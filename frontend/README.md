# Event Planning Frontend

React TypeScript application for the Event Planning system.

## Prerequisites

- Node.js 18+
- npm or yarn
- Backend API running on localhost:8080

## Setup

1. **Install dependencies**:
   ```bash
   cd frontend
   npm install
   ```

2. **Start the development server**:
   ```bash
   npm start
   ```

3. **Application will be available at**: `http://localhost:3000`

## Features

- **User Authentication**: Register and login
- **Event Management**: Create and view events
- **RSVP System**: Submit and manage RSVPs
- **Guest Lists**: View event attendees
- **Task Management**: Create and track tasks (basic implementation)
- **Budget Tracking**: Add expenses and view summaries (basic implementation)

## Usage

1. **Register/Login**: Create an account or login with existing credentials
2. **Create Events**: Use the "Create Event" button to add new events
3. **View Events**: See all your events in the "My Events" section
4. **RSVP**: Use the RSVP demo section to test guest responses
5. **Share Events**: Each event gets a shareable link for guest access

## API Integration

The frontend communicates with the backend API at `http://localhost:8080/api`. All API calls are handled through the `/src/api/` modules:

- `auth.ts` - Authentication endpoints
- `events.ts` - Event management
- `guests.ts` - RSVP and guest management
- `tasks.ts` - Task management
- `budget.ts` - Budget and expense tracking