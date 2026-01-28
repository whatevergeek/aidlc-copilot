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
                request.get("password")
            );
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "userId", user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String token = userService.authenticateUser(request.get("email"), request.get("password"));
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Event Endpoints
    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody Map<String, Object> request, @RequestHeader("Authorization") String token) {
        try {
            String userId = getUserIdFromToken(token);
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
            return ResponseEntity.ok(eventService.getEventsByOrganizer(userId));
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

    // Task Endpoints
    @PostMapping("/events/{eventId}/tasks")
    public ResponseEntity<?> createTask(@PathVariable String eventId, @RequestBody Map<String, Object> request) {
        try {
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
    public ResponseEntity<?> updateTaskStatus(@PathVariable String taskId, @RequestBody Map<String, String> request) {
        try {
            Task task = taskService.updateTaskStatus(taskId, Task.TaskStatus.valueOf(request.get("status")));
            return ResponseEntity.ok(task);
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

    // Helper method
    private String getUserIdFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return authenticationService.getUserIdFromToken(token);
    }
}