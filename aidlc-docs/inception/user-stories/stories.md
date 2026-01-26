# User Stories

## Epic 1: User Authentication & Profile Management

### US-1: User Registration and Login
**As an** Event Organizer, Coordinator, or Attendee  
**I want** to register and login with email/password  
**So that** I can access the event planning system

**Acceptance Criteria**:
- Given I am a new user, when I provide valid email and password, then I can create an account
- Given I am a registered user, when I provide correct credentials, then I can login successfully
- Given I provide invalid credentials, when I attempt to login, then I receive an error message

---

## Epic 2: Event Management

### US-2: Create Community Events
**As an** Event Organizer  
**I want** to create new community events with basic details  
**So that** I can organize meetups, workshops, and social gatherings

**Acceptance Criteria**:
- Given I am logged in as an organizer, when I provide event name, date, time, location, and description, then the event is created
- Given I create an event, when I save it, then I can view it in my event list
- Given I create an event, when I generate a shareable link, then others can access the event details

### US-3: Share Events for Registration
**As an** Event Organizer  
**I want** to generate shareable event links  
**So that** potential attendees can discover and register for my events

**Acceptance Criteria**:
- Given I have created an event, when I generate a shareable link, then the link provides access to event details
- Given someone accesses the shareable link, when they view the event, then they can see all event information
- Given someone accesses the shareable link, when they want to RSVP, then they can submit their response

---

## Epic 3: Guest Management & RSVP System

### US-4: RSVP to Events
**As an** Event Attendee  
**I want** to RSVP to events with simple Yes/No responses  
**So that** organizers know who will attend their events

**Acceptance Criteria**:
- Given I access an event via shareable link, when I choose Yes or No for RSVP, then my response is recorded
- Given I have already RSVP'd, when I access the event again, then I can see my current RSVP status
- Given I want to change my RSVP, when I select a different option, then my response is updated

### US-5: Manage Guest Lists
**As an** Event Organizer  
**I want** to view and manage guest lists and RSVP responses  
**So that** I can plan event logistics and capacity

**Acceptance Criteria**:
- Given attendees have RSVP'd to my event, when I view the guest list, then I can see all responses
- Given I view the guest list, when I check RSVP status, then I can see Yes/No counts
- Given I need to track attendance, when I view guest details, then I can see individual RSVP responses

---

## Epic 4: Task Management

### US-6: Create and Assign Tasks
**As an** Event Organizer  
**I want** to create tasks and assign them to coordinators  
**So that** event preparation work is distributed and tracked

**Acceptance Criteria**:
- Given I am organizing an event, when I create a task with description and due date, then the task is saved
- Given I have created a task, when I assign it to a coordinator, then they can see it in their task list
- Given I assign tasks, when I view task status, then I can track completion progress

### US-7: Manage Assigned Tasks
**As an** Event Coordinator  
**I want** to view and update my assigned tasks  
**So that** I can contribute to event planning and track my progress

**Acceptance Criteria**:
- Given I am assigned tasks, when I login, then I can see my task list
- Given I have tasks to complete, when I update task status, then the organizer can see the progress
- Given I complete a task, when I mark it as done, then it shows as completed

---

## Epic 5: Budget Management

### US-8: Track Event Budget
**As an** Event Organizer  
**I want** to create and manage event budgets with expense categories  
**So that** I can control event costs and track spending

**Acceptance Criteria**:
- Given I am organizing an event, when I create a budget with categories, then I can track expenses
- Given I have a budget, when I add expenses to categories, then totals are calculated automatically
- Given I track expenses, when I view budget summary, then I can see spending by category

---

## Epic 6: Dashboard & Timeline

### US-9: View Event Timeline Dashboard
**As an** Event Organizer or Coordinator  
**I want** to see a timeline view of upcoming events and deadlines  
**So that** I can manage multiple events and stay on schedule

**Acceptance Criteria**:
- Given I have events and tasks, when I view the dashboard, then I see a timeline of upcoming items
- Given I view the timeline, when I check deadlines, then I can see task due dates and event dates
- Given I use the dashboard, when I need event overview, then I can see key metrics and status

---

## Traceability Matrix

| User Story | Requirements Mapping |
|------------|---------------------|
| US-1 | FR-1.1, FR-1.2, FR-1.3 (User Management & Authentication) |
| US-2 | FR-2.1, FR-2.2, FR-2.3, FR-2.4 (Event Management) |
| US-3 | FR-3.1 (Guest Management - Shareable Links) |
| US-4 | FR-3.2, FR-3.4 (RSVP System) |
| US-5 | FR-3.3, FR-3.4 (Guest List Management) |
| US-6 | FR-4.1, FR-4.2, FR-4.3, FR-4.4 (Task Management) |
| US-7 | FR-4.2, FR-4.5 (Task Status Tracking) |
| US-8 | FR-5.1, FR-5.2, FR-5.3, FR-5.4 (Budget Management) |
| US-9 | FR-6.1, FR-6.2, FR-6.3, FR-6.4 (Dashboard & Visualizations) |