# Component Dependencies

## Backend Component Dependency Matrix

| Component | Depends On | Used By |
|-----------|------------|---------|
| AuthenticationService | UserRepository | All Services, Controller |
| UserService | UserRepository, AuthenticationService | EventService, TaskService, Controller |
| EventService | EventRepository, UserService | GuestService, TaskService, BudgetService, Controller |
| GuestService | GuestRepository, EventService | Controller |
| TaskService | TaskRepository, EventService, UserService | Controller |
| BudgetService | BudgetRepository, EventService | Controller |
| UserRepository | MongoDB | UserService, AuthenticationService |
| EventRepository | MongoDB | EventService |
| GuestRepository | MongoDB | GuestService |
| TaskRepository | MongoDB | TaskService |
| BudgetRepository | MongoDB | BudgetService |

## Communication Patterns

### 1. Direct Method Calls (Chosen Approach)
Components communicate through direct method invocations within the same JVM:

```
Controller → Service → Repository → MongoDB
```

**Example Flow - Create Event**:
```
EventPlanningController.createEvent()
  → EventService.createEvent()
    → UserService.getUserById() [validate organizer]
    → EventRepository.save()
      → MongoDB
```

### 2. Service-to-Service Communication
Services call each other directly for cross-domain operations:

**EventService → UserService**:
- Validate event organizers
- Check user permissions

**GuestService → EventService**:
- Validate event exists before RSVP
- Get event details for guest operations

**TaskService → EventService + UserService**:
- Validate event exists for task creation
- Validate assignee users exist

**BudgetService → EventService**:
- Validate event exists for budget creation
- Link budgets to events

### 3. Authentication Flow
Centralized authentication used by all components:

```
Request → AuthenticationFilter → AuthenticationService.validateToken()
  → Extract User → Pass to Controller → Pass to Services
```

## Data Flow Patterns

### 1. Request Processing Flow
```
Frontend Request
  ↓
REST Controller (Single Controller)
  ↓
Domain Service (EventService, TaskService, etc.)
  ↓
Repository (EventRepository, TaskRepository, etc.)
  ↓
MongoDB
```

### 2. Authentication Flow
```
Login Request
  ↓
AuthenticationService.authenticateUser()
  ↓
UserRepository.findByEmail()
  ↓
Generate JWT Token
  ↓
Return to Frontend
```

### 3. Cross-Domain Operations Flow
```
Create Task Request
  ↓
TaskService.createTask()
  ↓
EventService.getEventById() [validate event]
  ↓
UserService.getUserById() [validate assignee]
  ↓
TaskRepository.save()
```

## Frontend Component Dependencies

### Component Hierarchy
```
App
├── AuthenticationProvider (Context)
├── Router
│   ├── Dashboard
│   │   ├── Timeline
│   │   ├── MetricCards
│   │   └── QuickActions
│   ├── EventManagement
│   │   ├── EventList
│   │   ├── EventForm
│   │   └── EventDetails
│   ├── GuestManagement
│   │   ├── GuestList
│   │   └── RSVPForm
│   ├── TaskManagement
│   │   ├── TaskList
│   │   ├── TaskForm
│   │   └── TaskBoard
│   └── BudgetManagement
│       ├── BudgetOverview
│       ├── ExpenseForm
│       └── BudgetReports
```

### State Management Dependencies
Using React built-in state (useState, useContext):

```typescript
// Authentication Context (Global)
AuthContext → All Protected Components

// Feature-specific State (Local)
EventManagement → EventList, EventForm, EventDetails
GuestManagement → GuestList, RSVPForm
TaskManagement → TaskList, TaskForm, TaskBoard
BudgetManagement → BudgetOverview, ExpenseForm, BudgetReports
```

## Dependency Validation

### Circular Dependency Check
✅ **No circular dependencies detected**:
- Services have clear hierarchy: Controller → Service → Repository
- Cross-service calls are unidirectional
- Authentication service is used by others but doesn't depend on them

### Dependency Consistency
✅ **All dependencies are consistent**:
- Each service depends only on its repository and other services it needs
- Repository layer only depends on MongoDB
- Controller only depends on services
- No direct repository access from controller

### Interface Contracts
All component interactions use well-defined interfaces:
- Service interfaces define business operations
- Repository interfaces define data access operations
- API contracts define REST endpoints
- Frontend API clients match backend endpoints

## Technology Stack Dependencies

### Backend Dependencies
```
Spring Boot Framework
├── Spring Web (REST Controller)
├── Spring Security (Authentication)
├── Spring Data MongoDB (Repositories)
├── JWT Library (Token Management)
└── MongoDB Driver
```

### Frontend Dependencies
```
React Application
├── React Router (Navigation)
├── TypeScript (Type Safety)
├── Axios (HTTP Client)
├── React Context (State Management)
└── CSS Modules/Styled Components (Styling)
```

### Development Dependencies
```
Backend: Maven/Gradle, JUnit, Mockito
Frontend: npm/yarn, Jest, React Testing Library
Database: MongoDB (local instance)
```