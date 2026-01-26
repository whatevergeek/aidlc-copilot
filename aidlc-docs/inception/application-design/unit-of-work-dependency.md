# Unit of Work Dependencies

## Dependency Matrix

| Unit | Depends On | Used By |
|------|------------|---------|
| Backend Unit | MongoDB | Frontend Unit |
| Frontend Unit | Backend Unit (REST API) | End Users |

## Integration Points

### Frontend → Backend Integration
- **Protocol**: HTTP/REST
- **Authentication**: JWT tokens
- **Data Format**: JSON
- **Base URL**: `http://localhost:8080/api`

### Backend → MongoDB Integration
- **Protocol**: MongoDB Driver
- **Database**: `eventplanning`
- **Connection**: `mongodb://localhost:27017/eventplanning`

## Communication Patterns

### API Endpoints (Frontend → Backend)
```
POST /api/auth/login          - User authentication
POST /api/auth/register       - User registration
GET  /api/events             - Get events list
POST /api/events             - Create event
GET  /api/events/{id}/guests - Get guest list
POST /api/events/{id}/rsvp   - Submit RSVP
GET  /api/tasks              - Get tasks
POST /api/tasks              - Create task
GET  /api/budgets/{id}       - Get budget
POST /api/expenses           - Add expense
```

### Data Flow
```
User Interaction (Frontend)
    ↓
HTTP Request to Backend API
    ↓
Backend Service Processing
    ↓
MongoDB Data Operations
    ↓
JSON Response to Frontend
    ↓
UI Update
```

## Development Dependencies

### Backend Unit Build Dependencies
- Java 21 (target compatibility)
- Maven or Gradle
- Spring Boot 3.x
- Spring Data MongoDB
- Spring Security
- JWT Library

### Frontend Unit Build Dependencies
- Node.js 24+
- npm or yarn
- React 18+
- TypeScript 5+
- Axios (HTTP client)
- React Router

## Runtime Dependencies

### Backend Unit Runtime
- Java 25 (local development)
- MongoDB (running locally)
- Port 8080 (default Spring Boot)

### Frontend Unit Runtime
- Node.js (development server)
- Port 3000 (default React dev server)
- Backend API available at localhost:8080

## No Circular Dependencies
✅ Clean dependency hierarchy:
- Frontend depends on Backend
- Backend depends on MongoDB
- No circular dependencies