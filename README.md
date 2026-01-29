# Event Planning Application

A complete event planning system built with Java Spring Boot backend and React TypeScript frontend.

## 📚 Documentation

- **[Local Setup Guide](local-setup.md)** - VS Code development setup
- **[User Guide](user-guide.md)** - How to use the application
- **[Admin Guide](admin-guide.md)** - System administration
- **[Developer Guide](dev-guide.md)** - Development and maintenance
- **[DevOps Guide](devops-guide.md)** - Deployment and operations

## Features

✅ **User Authentication** - Register and login with JWT tokens  
✅ **Event Management** - Create, view, and share community events  
✅ **RSVP System** - Simple Yes/No responses with guest tracking  
✅ **Guest Management** - View attendee lists and RSVP summaries  
✅ **Task Management** - Create and assign tasks with status tracking  
✅ **Budget Tracking** - Add expenses and monitor event budgets  
✅ **Dashboard** - Timeline view of events and key metrics  

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
2. Register a new account
3. Create your first event
4. Share the event link with guests
5. Manage tasks and budget

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