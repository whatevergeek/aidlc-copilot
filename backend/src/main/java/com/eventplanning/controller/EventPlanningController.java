package com.eventplanning.controller;

import com.eventplanning.model.*;
import com.eventplanning.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class EventPlanningController {

    @Autowired private UserService userService;
    @Autowired private EventService eventService;
    @Autowired private GuestService guestService;
    @Autowired private TaskService taskService;
    @Autowired private BudgetService budgetService;
    @Autowired private AuthenticationService authenticationService;

    // Authentication Endpoints
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            User user = userService.registerUser(
                request.get("email"),
                request.get("name"),
                request.get("password"),
                request.get("role")
            );
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "userId", user.getId(), "role", user.getRole()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String token = userService.authenticateUser(request.get("email"), request.get("password"));
            String userId = authenticationService.getUserIdFromToken(token);
            Optional<User> user = userService.getUserById(userId);
            return ResponseEntity.ok(Map.of(
                "token", token, 
                "role", user.get().getRole(),
                "name", user.get().getName(),
                "email", user.get().getEmail()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Event Endpoints
    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody Map<String, Object> request, @RequestHeader("Authorization") String token) {
        try {
            String userId = getUserIdFromToken(token);
            // Check if user is ORGANIZER
            Optional<User> user = userService.getUserById(userId);
            if (user.isEmpty() || user.get().getRole() != User.UserRole.ORGANIZER) {
                return ResponseEntity.status(403).body(Map.of("error", "Only organizers can create events"));
            }
            
            Event event = eventService.createEvent(
                (String) request.get("name"),
                (String) request.get("description"),
                LocalDateTime.parse((String) request.get("dateTime")),
                (String) request.get("location"),
                userId
            );
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null) {
            String userId = getUserIdFromToken(token);
            Optional<User> user = userService.getUserById(userId);
            if (user.isPresent() && user.get().getRole() == User.UserRole.ORGANIZER) {
                return ResponseEntity.ok(eventService.getEventsByOrganizer(userId));
            }
        }
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable String eventId) {
        Optional<Event> event = eventService.getEventById(eventId);
        return event.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/events/share/{shareableLink}")
    public ResponseEntity<?> getEventByLink(@PathVariable String shareableLink) {
        Optional<Event> event = eventService.getEventByShareableLink(shareableLink);
        return event.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // RSVP Endpoints
    @PostMapping("/events/{eventId}/rsvp")
    public ResponseEntity<?> submitRSVP(@PathVariable String eventId, @RequestBody Map<String, String> request) {
        try {
            RSVP rsvp = guestService.submitRSVP(
                eventId,
                request.get("guestEmail"),
                request.get("guestName"),
                RSVP.RSVPResponse.valueOf(request.get("response"))
            );
            return ResponseEntity.ok(rsvp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/events/{eventId}/guests")
    public ResponseEntity<List<RSVP>> getGuestList(@PathVariable String eventId) {
        return ResponseEntity.ok(guestService.getGuestList(eventId));
    }

    @GetMapping("/events/{eventId}/rsvp-summary")
    public ResponseEntity<GuestService.RSVPSummary> getRSVPSummary(@PathVariable String eventId) {
        return ResponseEntity.ok(guestService.getRSVPSummary(eventId));
    }

    @GetMapping("/events/{eventId}/my-rsvp")
    public ResponseEntity<?> getMyRSVP(@PathVariable String eventId, @RequestHeader("Authorization") String token) {
        try {
            String userId = getUserIdFromToken(token);
            Optional<User> user = userService.getUserById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.status(403).body(Map.of("error", "User not found"));
            }
            
            Optional<RSVP> rsvp = guestService.getRSVPByEventAndEmail(eventId, user.get().getEmail());
            if (rsvp.isPresent()) {
                return ResponseEntity.ok(Map.of(
                    "hasRSVP", true,
                    "response", rsvp.get().getResponse().toString()
                ));
            } else {
                return ResponseEntity.ok(Map.of("hasRSVP", false));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/rsvp-dashboard")
    public ResponseEntity<?> getRSVPDashboard(@RequestHeader("Authorization") String token) {
        try {
            System.out.println("RSVP Dashboard endpoint called");
            String userId = getUserIdFromToken(token);
            System.out.println("User ID: " + userId);
            
            Optional<User> user = userService.getUserById(userId);
            System.out.println("User found: " + user.isPresent());
            
            if (user.isEmpty() || (user.get().getRole() != User.UserRole.COORDINATOR && user.get().getRole() != User.UserRole.ORGANIZER)) {
                System.out.println("Access denied - user role: " + (user.isPresent() ? user.get().getRole() : "none"));
                return ResponseEntity.status(403).body(Map.of("error", "Only coordinators and organizers can access RSVP dashboard"));
            }
            
            System.out.println("Getting all events...");
            List<Event> events = eventService.getAllEvents();
            System.out.println("Events found: " + events.size());
            
            List<Map<String, Object>> dashboardData = events.stream().map(event -> {
                System.out.println("Processing event: " + event.getName());
                GuestService.RSVPSummary summary = guestService.getRSVPSummary(event.getId());
                System.out.println("RSVP Summary - Yes: " + summary.getYesCount() + ", No: " + summary.getNoCount());
                Map<String, Object> eventData = new java.util.HashMap<>();
                eventData.put("eventId", event.getId());
                eventData.put("eventName", event.getName());
                eventData.put("eventDate", event.getDateTime().toString());
                eventData.put("yesCount", summary.getYesCount());
                eventData.put("noCount", summary.getNoCount());
                eventData.put("totalCount", summary.getTotalCount());
                return eventData;
            }).collect(java.util.stream.Collectors.toList());
            
            System.out.println("Dashboard data size: " + dashboardData.size());
            return ResponseEntity.ok(dashboardData);
        } catch (Exception e) {
            System.err.println("Error in RSVP Dashboard: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Task Endpoints
    @PostMapping("/events/{eventId}/tasks")
    public ResponseEntity<?> createTask(@PathVariable String eventId, @RequestBody Map<String, Object> request, @RequestHeader("Authorization") String token) {
        try {
            String userId = getUserIdFromToken(token);
            // Check if user is COORDINATOR or ORGANIZER
            Optional<User> user = userService.getUserById(userId);
            
            if (user.isEmpty() || (user.get().getRole() != User.UserRole.COORDINATOR && user.get().getRole() != User.UserRole.ORGANIZER)) {
                return ResponseEntity.status(403).body(Map.of("error", "Only coordinators and organizers can create tasks"));
            }
            
            // Verify event exists
            Optional<Event> event = eventService.getEventById(eventId);
            if (event.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "Event not found"));
            }
            
            Task task = taskService.createTask(
                eventId,
                (String) request.get("title"),
                (String) request.get("description"),
                (String) request.get("assigneeId"),
                LocalDate.parse((String) request.get("dueDate"))
            );
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/events/{eventId}/tasks")
    public ResponseEntity<List<Task>> getEventTasks(@PathVariable String eventId) {
        return ResponseEntity.ok(taskService.getTasksByEvent(eventId));
    }

    @GetMapping("/tasks/assigned")
    public ResponseEntity<List<Task>> getAssignedTasks(@RequestHeader("Authorization") String token) {
        String userId = getUserIdFromToken(token);
        return ResponseEntity.ok(taskService.getTasksByAssignee(userId));
    }

    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable String taskId, @RequestBody Map<String, String> request, @RequestHeader("Authorization") String token) {
        try {
            String userId = getUserIdFromToken(token);
            Optional<Task> taskOpt = taskService.getTaskById(taskId);
            Optional<User> user = userService.getUserById(userId);
            
            if (taskOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            if (user.isEmpty()) {
                return ResponseEntity.status(403).body(Map.of("error", "User not found"));
            }
            
            Task task = taskOpt.get();
            User.UserRole userRole = user.get().getRole();
            
            // Allow assignee, coordinators, and organizers to update task status
            boolean canUpdate = task.getAssigneeId().equals(userId) || 
                               userRole == User.UserRole.COORDINATOR || 
                               userRole == User.UserRole.ORGANIZER;
            
            if (!canUpdate) {
                return ResponseEntity.status(403).body(Map.of("error", "You can only update tasks assigned to you or if you're a coordinator/organizer"));
            }
            
            Task updatedTask = taskService.updateTaskStatus(taskId, Task.TaskStatus.valueOf(request.get("status")));
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Budget Endpoints
    @PostMapping("/events/{eventId}/budget")
    public ResponseEntity<Budget> createBudget(@PathVariable String eventId) {
        return ResponseEntity.ok(budgetService.createBudget(eventId));
    }

    @GetMapping("/events/{eventId}/budget")
    public ResponseEntity<?> getBudget(@PathVariable String eventId) {
        Optional<Budget> budget = budgetService.getBudgetByEventId(eventId);
        return budget.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/events/{eventId}/expenses")
    public ResponseEntity<?> addExpense(@PathVariable String eventId, @RequestBody Map<String, Object> request) {
        try {
            Budget budget = budgetService.addExpense(
                eventId,
                (String) request.get("description"),
                (String) request.get("category"),
                new BigDecimal(request.get("amount").toString())
            );
            return ResponseEntity.ok(budget);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/events/{eventId}/budget-summary")
    public ResponseEntity<BudgetService.BudgetSummary> getBudgetSummary(@PathVariable String eventId) {
        return ResponseEntity.ok(budgetService.getBudgetSummary(eventId));
    }

    // User Endpoints
    @GetMapping("/users/coordinators")
    public ResponseEntity<List<User>> getCoordinators(@RequestHeader("Authorization") String token) {
        String userId = getUserIdFromToken(token);
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty() || (user.get().getRole() != User.UserRole.COORDINATOR && user.get().getRole() != User.UserRole.ORGANIZER)) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(userService.getUsersByRole(User.UserRole.COORDINATOR));
    }

    @GetMapping("/users/organizers")
    public ResponseEntity<List<User>> getOrganizers(@RequestHeader("Authorization") String token) {
        String userId = getUserIdFromToken(token);
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty() || (user.get().getRole() != User.UserRole.COORDINATOR && user.get().getRole() != User.UserRole.ORGANIZER)) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(userService.getUsersByRole(User.UserRole.ORGANIZER));
    }

    // Helper method
    private String getUserIdFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return authenticationService.getUserIdFromToken(token);
    }
}