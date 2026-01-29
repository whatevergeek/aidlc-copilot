# Integration Test Instructions - Vite Migration

## Purpose
Test the complete integration between Vite frontend and Spring Boot backend to ensure the migration maintains all functionality.

## Test Scenarios

### Scenario 1: Frontend → Backend API Integration
- **Description**: Verify all API calls work through Vite proxy
- **Setup**: Both frontend (Vite) and backend (Spring Boot) running
- **Test Steps**: 
  1. Start backend: `cd backend && mvn spring-boot:run`
  2. Start frontend: `cd frontend && yarn dev`
  3. Test all API endpoints through browser
- **Expected Results**: All API calls succeed, no CORS errors
- **Cleanup**: Stop both services

### Scenario 2: Authentication Flow Integration
- **Description**: Complete user authentication workflow
- **Setup**: Clean database state, both services running
- **Test Steps**:
  1. Register new user through frontend
  2. Login with created user
  3. Access protected routes
  4. Verify JWT token handling
- **Expected Results**: Authentication works identically to CRA version
- **Cleanup**: Clear test user data

### Scenario 3: Event Management Integration
- **Description**: Full event lifecycle through UI
- **Setup**: Authenticated user session
- **Test Steps**:
  1. Create new event
  2. Edit event details
  3. Manage guest list
  4. Track RSVPs
  5. Manage tasks and budget
- **Expected Results**: All event features work without issues
- **Cleanup**: Delete test events

## Setup Integration Test Environment

### 1. Start Required Services
```bash
# Terminal 1: Start MongoDB
mongod --dbpath C:\data\db

# Terminal 2: Start Backend
cd backend
mvn spring-boot:run

# Terminal 3: Start Frontend (Vite)
cd frontend
yarn dev
```

### 2. Configure Service Endpoints
```bash
# Verify services are running
curl http://localhost:8080/api/health  # Backend health
curl http://localhost:3000             # Frontend loads
```

### 3. Verify Proxy Configuration
```bash
# Test API proxy through frontend
curl http://localhost:3000/api/health  # Should proxy to backend
```

## Run Integration Tests

### 1. Manual Integration Testing

#### Authentication Integration
- [ ] User registration works
- [ ] User login works  
- [ ] JWT tokens are handled correctly
- [ ] Protected routes work
- [ ] Logout works

#### Event Management Integration
- [ ] Create events through UI
- [ ] View events list
- [ ] Edit event details
- [ ] Delete events
- [ ] Share event links

#### RSVP System Integration
- [ ] Submit RSVP responses
- [ ] View guest lists
- [ ] Track RSVP summaries
- [ ] Guest management works

#### Task Management Integration
- [ ] Create tasks
- [ ] Assign tasks
- [ ] Update task status
- [ ] View task lists

#### Budget Tracking Integration
- [ ] Add budget items
- [ ] Track expenses
- [ ] View budget summaries
- [ ] Budget calculations work

### 2. API Integration Validation
```bash
# Test all major API endpoints
curl -X POST http://localhost:3000/api/auth/register -H "Content-Type: application/json" -d '{"username":"test","email":"test@example.com","password":"password123"}'

curl -X POST http://localhost:3000/api/auth/login -H "Content-Type: application/json" -d '{"username":"test","password":"password123"}'

# Test with JWT token
curl -X GET http://localhost:3000/api/events -H "Authorization: Bearer <token>"
```

### 3. Performance Integration Testing
- [ ] **Page Load Time**: Initial load <3 seconds
- [ ] **API Response Time**: <500ms for typical requests
- [ ] **Hot Reload**: Changes appear <1 second
- [ ] **Build Time**: Production build <30 seconds

### 4. Cross-Browser Integration
- [ ] **Chrome**: All features work
- [ ] **Firefox**: All features work
- [ ] **Safari**: All features work (if available)
- [ ] **Edge**: All features work

## Verify Service Interactions

### Frontend → Backend Communication
- **API Proxy**: All `/api/*` requests route to backend
- **CORS Handling**: No CORS errors in browser console
- **Error Handling**: API errors display properly in UI
- **Authentication**: JWT tokens sent with requests

### Database Integration
- **Data Persistence**: All CRUD operations work
- **Data Consistency**: UI reflects database state
- **Transaction Handling**: Complex operations complete successfully

### Real-time Features (if applicable)
- **WebSocket Connections**: If using WebSockets
- **Server-Sent Events**: If using SSE
- **Polling**: If using polling for updates

## Cleanup
```bash
# Stop all services
# Ctrl+C in each terminal

# Clean test data
mongo
use eventplanning
db.users.deleteMany({"username": /^test/})
db.events.deleteMany({"title": /^Test/})
```

## Success Criteria
- [ ] All API integrations work identically to CRA version
- [ ] No new errors or issues introduced
- [ ] Performance is same or better than CRA
- [ ] All user workflows complete successfully
- [ ] No data loss or corruption