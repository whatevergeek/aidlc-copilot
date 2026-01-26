# Application Services

## Backend Services (Domain Services per Business Area)

### 1. UserService
**Purpose**: Orchestrate user-related business operations
**Responsibilities**:
- User registration workflow
- User authentication and session management
- User profile updates
- Password management

**Key Operations**:
- registerUser(userDetails): User
- authenticateUser(credentials): AuthToken
- updateUserProfile(userId, profileData): User
- changePassword(userId, oldPassword, newPassword): boolean

**Dependencies**:
- UserRepository (data access)
- AuthenticationService (security operations)

### 2. EventService
**Purpose**: Orchestrate event management business operations
**Responsibilities**:
- Event lifecycle management
- Event sharing and access control
- Event search and filtering
- Event validation

**Key Operations**:
- createEvent(eventDetails, organizerId): Event
- updateEvent(eventId, eventDetails): Event
- generateShareableLink(eventId): String
- getEventsByOrganizer(organizerId): List<Event>
- getPublicEvents(): List<Event>

**Dependencies**:
- EventRepository (data access)
- UserService (organizer validation)

### 3. GuestService
**Purpose**: Orchestrate guest management and RSVP operations
**Responsibilities**:
- RSVP processing
- Guest list management
- Invitation tracking
- Attendance reporting

**Key Operations**:
- submitRSVP(eventId, guestDetails, response): RSVP
- updateRSVP(rsvpId, newResponse): RSVP
- getGuestList(eventId): List<Guest>
- getEventRSVPSummary(eventId): RSVPSummary

**Dependencies**:
- GuestRepository (data access)
- EventService (event validation)

### 4. TaskService
**Purpose**: Orchestrate task management and assignment operations
**Responsibilities**:
- Task creation and assignment
- Task status tracking
- Task completion workflow
- Task reporting

**Key Operations**:
- createTask(taskDetails, eventId): Task
- assignTask(taskId, assigneeId): Task
- updateTaskStatus(taskId, status): Task
- getTasksByEvent(eventId): List<Task>
- getTasksByAssignee(userId): List<Task>

**Dependencies**:
- TaskRepository (data access)
- EventService (event validation)
- UserService (assignee validation)

### 5. BudgetService
**Purpose**: Orchestrate budget management and expense tracking
**Responsibilities**:
- Budget creation and management
- Expense processing
- Budget calculations
- Financial reporting

**Key Operations**:
- createBudget(budgetDetails, eventId): Budget
- addExpense(expenseDetails, budgetId): Expense
- updateExpense(expenseId, expenseDetails): Expense
- getBudgetSummary(budgetId): BudgetSummary
- getExpensesByCategory(budgetId): Map<Category, List<Expense>>

**Dependencies**:
- BudgetRepository (data access)
- EventService (event validation)

### 6. AuthenticationService (Centralized)
**Purpose**: Centralized authentication and security operations
**Responsibilities**:
- JWT token management
- Password hashing and verification
- Session validation
- Security enforcement

**Key Operations**:
- generateToken(user): String
- validateToken(token): boolean
- hashPassword(password): String
- verifyPassword(password, hash): boolean
- getCurrentUser(token): User

**Dependencies**:
- UserRepository (user data access)
- JWT library (token operations)

## Service Communication Patterns

### Direct Method Calls
Services communicate through direct method invocations:
- EventService → UserService (validate organizers)
- GuestService → EventService (validate events)
- TaskService → EventService, UserService (validate events and users)
- BudgetService → EventService (validate events)
- All services → AuthenticationService (security operations)

### Service Orchestration
- **Single Controller**: All REST endpoints handled by one controller
- **Service Layer**: Controller delegates to appropriate domain services
- **Repository Layer**: Services use repositories for data access
- **Authentication**: Centralized authentication service used by all components

## Frontend Service Integration

### API Client Services
- **EventAPI**: Handles event-related HTTP requests
- **GuestAPI**: Manages guest and RSVP operations
- **TaskAPI**: Handles task management requests
- **BudgetAPI**: Manages budget and expense operations
- **AuthAPI**: Handles authentication requests

### State Management Services
- **EventStore**: React context for event state
- **UserStore**: React context for user authentication state
- **TaskStore**: React context for task management state
- **BudgetStore**: React context for budget state

### Communication Flow
```
Frontend Component → API Client → REST Controller → Domain Service → Repository → MongoDB
```

## Service Dependencies Matrix

| Service | Depends On |
|---------|------------|
| UserService | UserRepository, AuthenticationService |
| EventService | EventRepository, UserService |
| GuestService | GuestRepository, EventService |
| TaskService | TaskRepository, EventService, UserService |
| BudgetService | BudgetRepository, EventService |
| AuthenticationService | UserRepository |