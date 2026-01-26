# Unit of Work Definition

## System Decomposition: Two Units

### Unit 1: Backend Service
**Type**: Java Spring Boot Application
**Purpose**: REST API and business logic for event planning system
**Technology**: Java 21, Spring Boot, MongoDB
**Location**: `backend/` directory

**Responsibilities**:
- All REST API endpoints
- User authentication and management
- Event management operations
- Guest management and RSVP processing
- Task management and assignment
- Budget tracking and expense management
- MongoDB data access

**Components Included**:
- UserManagement (UserService, UserRepository)
- EventManagement (EventService, EventRepository)
- GuestManagement (GuestService, GuestRepository)
- TaskManagement (TaskService, TaskRepository)
- BudgetManagement (BudgetService, BudgetRepository)
- Authentication (AuthenticationService, Security Configuration)
- Single REST Controller (EventPlanningController)

### Unit 2: Frontend Application
**Type**: React/TypeScript Single Page Application
**Purpose**: User interface for event planning system
**Technology**: React, TypeScript, Axios
**Location**: `frontend/` directory

**Responsibilities**:
- User authentication UI
- Event management interface
- Guest management and RSVP forms
- Task management interface
- Budget tracking interface
- Dashboard with timeline view
- API integration with backend

**Components Included**:
- Authentication Feature (Login, Register, Profile)
- EventManagement Feature (EventList, EventForm, EventDetails)
- GuestManagement Feature (GuestList, RSVPForm)
- TaskManagement Feature (TaskList, TaskForm, TaskBoard)
- BudgetManagement Feature (BudgetOverview, ExpenseForm)
- Dashboard Feature (Timeline, MetricCards, QuickActions)

## Unit Dependencies

### Backend Unit Dependencies
- **External**: MongoDB database
- **Internal**: No dependencies (self-contained)

### Frontend Unit Dependencies
- **External**: Backend Unit REST API
- **Internal**: No dependencies (self-contained)

### Communication Pattern
```
Frontend Unit → HTTP/REST → Backend Unit → MongoDB
```

## Development Structure
```
project-root/
├── backend/                 # Unit 1: Java Spring Boot
│   ├── src/main/java/
│   ├── src/main/resources/
│   ├── pom.xml
│   └── README.md
├── frontend/                # Unit 2: React TypeScript
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── README.md
└── aidlc-docs/             # Documentation
```

## Story Distribution

All 9 user stories implemented across both units:
- **Backend Unit**: Implements business logic and data persistence for all stories
- **Frontend Unit**: Implements user interface and interactions for all stories

| User Story | Backend Implementation | Frontend Implementation |
|------------|----------------------|------------------------|
| US-1: User Registration/Login | UserService, AuthenticationService | LoginForm, RegisterForm |
| US-2: Create Events | EventService | EventForm, EventList |
| US-3: Share Events | EventService (generate links) | EventDetails (share functionality) |
| US-4: RSVP to Events | GuestService | RSVPForm |
| US-5: Manage Guest Lists | GuestService | GuestList |
| US-6: Create/Assign Tasks | TaskService | TaskForm, TaskAssignment |
| US-7: Manage Tasks | TaskService | TaskList, TaskBoard |
| US-8: Track Budget | BudgetService | BudgetOverview, ExpenseForm |
| US-9: Dashboard Timeline | All Services (data) | Timeline, Dashboard |