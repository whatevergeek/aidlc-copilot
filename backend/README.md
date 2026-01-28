# Event Planning Backend

Spring Boot REST API for the Event Planning Application.

## Prerequisites

- Java 21+
- Maven 3.6+
- MongoDB running on localhost:27017

## Setup

1. **Start MongoDB**:
   ```bash
   # Make sure MongoDB is running on localhost:27017
   ```

2. **Run the application**:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

3. **API will be available at**: `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Events
- `GET /api/events` - Get user's events
- `POST /api/events` - Create new event
- `GET /api/events/{id}` - Get event by ID
- `GET /api/events/share/{link}` - Get event by shareable link

### RSVP
- `POST /api/events/{id}/rsvp` - Submit RSVP
- `GET /api/events/{id}/guests` - Get guest list
- `GET /api/events/{id}/rsvp-summary` - Get RSVP summary

### Tasks
- `POST /api/events/{id}/tasks` - Create task
- `GET /api/events/{id}/tasks` - Get event tasks
- `GET /api/tasks/assigned` - Get assigned tasks
- `PUT /api/tasks/{id}/status` - Update task status

### Budget
- `POST /api/events/{id}/budget` - Create budget
- `GET /api/events/{id}/budget` - Get budget
- `POST /api/events/{id}/expenses` - Add expense
- `GET /api/events/{id}/budget-summary` - Get budget summary

## Database

The application uses MongoDB with the following collections:
- `users` - User accounts
- `events` - Event information
- `rsvps` - RSVP responses
- `tasks` - Event tasks
- `budgets` - Event budgets and expenses