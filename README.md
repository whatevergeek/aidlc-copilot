# Event Planning Application

A complete event planning system built with Java Spring Boot backend and React TypeScript frontend.

## 📚 Documentation

**User Guides (by Persona):**
- **[Event Organizer Guide](event-organizer-guide.md)** - Create and manage community events
- **[Event Coordinator Guide](event-coordinator-guide.md)** - Support event execution and task management  
- **[Event Attendee Guide](event-attendee-guide.md)** - Discover and participate in events

**Setup & Administration:**
- **[Local Setup Guide](local-setup.md)** - VS Code development setup
- **[System Admin Guide](system-admin-guide.md)** - Database and infrastructure management
- **[Developer Guide](dev-guide.md)** - Development and maintenance
- **[DevOps Guide](devops-guide.md)** - Deployment and operations

## Features

✅ **Role-Based Access Control** - Three user types with different permissions  
✅ **User Authentication** - Register and login with JWT tokens  
✅ **Event Management** - Create, view, and share community events (Organizers)  
✅ **RSVP System** - Simple Yes/No responses with guest tracking (All users)  
✅ **Guest Management** - View attendee lists and RSVP summaries  
✅ **Task Management** - Create and assign tasks with status tracking (Coordinators+)  
✅ **Budget Tracking** - Add expenses and monitor event budgets  
✅ **Dashboard** - Role-based interface with appropriate features  

### User Roles

**🎉 Event Attendee**
- Browse all community events
- RSVP to events (Yes/No responses)
- View event details and information

**🤝 Event Coordinator** 
- All Attendee capabilities +
- Create tasks for any event
- Assign tasks to coordinators and organizers
- Update task status (own tasks + any as coordinator)
- Support event execution

**📋 Event Organizer**
- All Coordinator capabilities +
- Create and manage events
- Full event lifecycle management
- Team coordination and leadership  

## Quick Start

### Prerequisites
- Java 21+
- Node.js 18+
- MongoDB running on localhost:27017

### 1. Start Backend
```bash
cd backend
mvn spring-boot:run
```
Backend runs on: `http://localhost:8080`

### 2. Start Frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend runs on: `http://localhost:3000`

### 3. Use the Application
1. Open `http://localhost:3000`
2. Register a new account and select your role:
   - **Event Attendee**: Browse and RSVP to events
   - **Event Coordinator**: Create tasks and support events
   - **Event Organizer**: Create events and manage teams
3. Explore features based on your role
4. Create events (Organizers), manage tasks (Coordinators+), or RSVP (All users)

## Architecture

- **Backend**: Java 21 Spring Boot REST API
- **Frontend**: React 18 with TypeScript and Vite
- **Database**: MongoDB
- **Authentication**: JWT tokens
- **Communication**: REST API with JSON

## Project Structure

```
event-planning-app/
├── backend/                 # Java Spring Boot API
│   ├── src/main/java/
│   │   └── com/eventplanning/
│   │       ├── model/       # Data models
│   │       ├── repository/  # MongoDB repositories
│   │       ├── service/     # Business logic
│   │       ├── controller/  # REST endpoints
│   │       └── config/      # Security configuration
│   └── pom.xml
├── frontend/                # React TypeScript App
│   ├── src/
│   │   ├── api/            # API client functions
│   │   ├── components/     # React components
│   │   ├── contexts/       # React contexts
│   │   └── App.tsx
│   └── package.json
└── aidlc-docs/             # AI-DLC documentation
```

## User Stories Implemented

1. **US-1**: User Registration and Login ✅
2. **US-2**: Create Community Events ✅
3. **US-3**: Share Events via Links ✅
4. **US-4**: RSVP to Events ✅
5. **US-5**: Manage Guest Lists ✅
6. **US-6**: Create and Assign Tasks ✅
7. **US-7**: Manage Task Status ✅
8. **US-8**: Track Event Budget ✅
9. **US-9**: Dashboard Timeline View ✅

## Technology Stack

- **Backend**: Java 21, Spring Boot 3.2, Spring Security, Spring Data MongoDB
- **Frontend**: React 18, TypeScript 5, Vite 5, Vitest
- **Database**: MongoDB
- **Build Tools**: Maven (backend), npm (frontend)
- **Authentication**: JWT tokens with bcrypt password hashing

## Development

This project was built using the AI-DLC (AI-Driven Development Life Cycle) methodology, ensuring:
- Complete requirements analysis
- User story-driven development
- Component-based architecture
- Full API documentation
- Ready-to-run local setup

## Next Steps

The application is fully functional for local development. To enhance it further, consider:
- Adding more detailed task management features
- Implementing real-time notifications
- Adding file upload for event images
- Creating mobile-responsive design improvements
- Adding email notifications for RSVPs