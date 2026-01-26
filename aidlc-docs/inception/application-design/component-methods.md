# Component Methods

## Backend Component Methods

### UserManagement Component

#### UserService Methods
```java
// User registration and authentication
public User registerUser(UserRegistrationRequest request)
public AuthenticationResponse authenticateUser(LoginRequest request)
public User getUserById(String userId)
public User updateUserProfile(String userId, UserUpdateRequest request)
public boolean changePassword(String userId, ChangePasswordRequest request)
public void logoutUser(String userId)
```

#### UserRepository Methods
```java
// Data access methods
public User save(User user)
public Optional<User> findById(String userId)
public Optional<User> findByEmail(String email)
public boolean existsByEmail(String email)
public void deleteById(String userId)
```

#### AuthenticationService Methods
```java
// Security operations
public String generateToken(User user)
public boolean validateToken(String token)
public String hashPassword(String password)
public boolean verifyPassword(String password, String hash)
public User getCurrentUserFromToken(String token)
```

### EventManagement Component

#### EventService Methods
```java
// Event management operations
public Event createEvent(CreateEventRequest request, String organizerId)
public Event updateEvent(String eventId, UpdateEventRequest request)
public Event getEventById(String eventId)
public List<Event> getEventsByOrganizer(String organizerId)
public List<Event> getPublicEvents()
public String generateShareableLink(String eventId)
public void deleteEvent(String eventId)
```

#### EventRepository Methods
```java
// Event data access
public Event save(Event event)
public Optional<Event> findById(String eventId)
public List<Event> findByOrganizerId(String organizerId)
public List<Event> findAllPublicEvents()
public void deleteById(String eventId)
```

### GuestManagement Component

#### GuestService Methods
```java
// Guest and RSVP operations
public RSVP submitRSVP(RSVPRequest request)
public RSVP updateRSVP(String rsvpId, UpdateRSVPRequest request)
public List<Guest> getGuestList(String eventId)
public RSVPSummary getRSVPSummary(String eventId)
public Optional<RSVP> getRSVPByEventAndGuest(String eventId, String guestEmail)
```

#### GuestRepository Methods
```java
// Guest data access
public RSVP save(RSVP rsvp)
public Optional<RSVP> findById(String rsvpId)
public List<RSVP> findByEventId(String eventId)
public Optional<RSVP> findByEventIdAndGuestEmail(String eventId, String guestEmail)
public long countByEventIdAndResponse(String eventId, RSVPResponse response)
```

### TaskManagement Component

#### TaskService Methods
```java
// Task management operations
public Task createTask(CreateTaskRequest request, String eventId)
public Task updateTask(String taskId, UpdateTaskRequest request)
public Task assignTask(String taskId, String assigneeId)
public Task updateTaskStatus(String taskId, TaskStatus status)
public List<Task> getTasksByEvent(String eventId)
public List<Task> getTasksByAssignee(String assigneeId)
```

#### TaskRepository Methods
```java
// Task data access
public Task save(Task task)
public Optional<Task> findById(String taskId)
public List<Task> findByEventId(String eventId)
public List<Task> findByAssigneeId(String assigneeId)
public List<Task> findByEventIdAndStatus(String eventId, TaskStatus status)
```

### BudgetManagement Component

#### BudgetService Methods
```java
// Budget management operations
public Budget createBudget(CreateBudgetRequest request, String eventId)
public Budget updateBudget(String budgetId, UpdateBudgetRequest request)
public Expense addExpense(AddExpenseRequest request, String budgetId)
public Expense updateExpense(String expenseId, UpdateExpenseRequest request)
public BudgetSummary getBudgetSummary(String budgetId)
public List<Expense> getExpensesByCategory(String budgetId, String category)
```

#### BudgetRepository Methods
```java
// Budget data access
public Budget save(Budget budget)
public Optional<Budget> findById(String budgetId)
public Optional<Budget> findByEventId(String eventId)
public Expense saveExpense(Expense expense)
public List<Expense> findExpensesByBudgetId(String budgetId)
```

## Frontend Component Methods

### EventManagement Feature Methods
```typescript
// Event management operations
export const createEvent = async (eventData: CreateEventRequest): Promise<Event>
export const updateEvent = async (eventId: string, eventData: UpdateEventRequest): Promise<Event>
export const getEvents = async (): Promise<Event[]>
export const getEventById = async (eventId: string): Promise<Event>
export const deleteEvent = async (eventId: string): Promise<void>
export const generateShareLink = async (eventId: string): Promise<string>
```

### GuestManagement Feature Methods
```typescript
// Guest and RSVP operations
export const submitRSVP = async (rsvpData: RSVPRequest): Promise<RSVP>
export const updateRSVP = async (rsvpId: string, rsvpData: UpdateRSVPRequest): Promise<RSVP>
export const getGuestList = async (eventId: string): Promise<Guest[]>
export const getRSVPSummary = async (eventId: string): Promise<RSVPSummary>
```

### TaskManagement Feature Methods
```typescript
// Task management operations
export const createTask = async (taskData: CreateTaskRequest): Promise<Task>
export const updateTask = async (taskId: string, taskData: UpdateTaskRequest): Promise<Task>
export const assignTask = async (taskId: string, assigneeId: string): Promise<Task>
export const updateTaskStatus = async (taskId: string, status: TaskStatus): Promise<Task>
export const getTasksByEvent = async (eventId: string): Promise<Task[]>
```

### BudgetManagement Feature Methods
```typescript
// Budget management operations
export const createBudget = async (budgetData: CreateBudgetRequest): Promise<Budget>
export const addExpense = async (expenseData: AddExpenseRequest): Promise<Expense>
export const updateExpense = async (expenseId: string, expenseData: UpdateExpenseRequest): Promise<Expense>
export const getBudgetSummary = async (budgetId: string): Promise<BudgetSummary>
```

### Authentication Feature Methods
```typescript
// Authentication operations
export const login = async (credentials: LoginRequest): Promise<AuthenticationResponse>
export const register = async (userData: UserRegistrationRequest): Promise<User>
export const logout = async (): Promise<void>
export const getCurrentUser = async (): Promise<User>
export const updateProfile = async (userData: UserUpdateRequest): Promise<User>
```

## REST API Controller Methods

### Single Controller (EventPlanningController)
```java
// Authentication endpoints
@PostMapping("/auth/login")
public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request)

@PostMapping("/auth/register")
public ResponseEntity<User> register(@RequestBody UserRegistrationRequest request)

// Event endpoints
@PostMapping("/events")
public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest request)

@GetMapping("/events/{eventId}")
public ResponseEntity<Event> getEvent(@PathVariable String eventId)

@GetMapping("/events")
public ResponseEntity<List<Event>> getEvents()

// RSVP endpoints
@PostMapping("/events/{eventId}/rsvp")
public ResponseEntity<RSVP> submitRSVP(@PathVariable String eventId, @RequestBody RSVPRequest request)

@GetMapping("/events/{eventId}/guests")
public ResponseEntity<List<Guest>> getGuestList(@PathVariable String eventId)

// Task endpoints
@PostMapping("/events/{eventId}/tasks")
public ResponseEntity<Task> createTask(@PathVariable String eventId, @RequestBody CreateTaskRequest request)

@GetMapping("/events/{eventId}/tasks")
public ResponseEntity<List<Task>> getEventTasks(@PathVariable String eventId)

// Budget endpoints
@PostMapping("/events/{eventId}/budget")
public ResponseEntity<Budget> createBudget(@PathVariable String eventId, @RequestBody CreateBudgetRequest request)

@PostMapping("/budgets/{budgetId}/expenses")
public ResponseEntity<Expense> addExpense(@PathVariable String budgetId, @RequestBody AddExpenseRequest request)
```

**Note**: Detailed business rules and validation logic will be defined in the Functional Design phase (CONSTRUCTION phase).