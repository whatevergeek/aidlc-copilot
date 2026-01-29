import React, { useState } from 'react';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import LoginForm from './components/auth/LoginForm';
import RegisterForm from './components/auth/RegisterForm';
import EventList from './components/events/EventList';
import EventForm from './components/events/EventForm';
import RSVPDashboard from './components/rsvp/RSVPDashboard';

const AppContent: React.FC = () => {
  const { isAuthenticated, userRole, logout } = useAuth();
  const [currentView, setCurrentView] = useState<'events' | 'create' | 'login' | 'register' | 'tasks' | 'rsvp-dashboard'>('events');

  if (!isAuthenticated) {
    return (
      <div style={{ minHeight: '100vh', backgroundColor: '#f5f5f5' }}>
        <nav style={{ backgroundColor: '#007bff', padding: '1rem', color: 'white' }}>
          <h1 style={{ margin: 0, display: 'inline' }}>Event Planning App</h1>
          <div style={{ float: 'right' }}>
            <button 
              onClick={() => setCurrentView('login')}
              style={{ marginRight: '10px', padding: '5px 15px', backgroundColor: 'transparent', color: 'white', border: '1px solid white' }}
            >
              Login
            </button>
            <button 
              onClick={() => setCurrentView('register')}
              style={{ padding: '5px 15px', backgroundColor: 'white', color: '#007bff', border: 'none' }}
            >
              Register
            </button>
          </div>
        </nav>
        
        <div style={{ padding: '20px' }}>
          {currentView === 'login' && <LoginForm />}
          {currentView === 'register' && <RegisterForm />}
          {currentView === 'events' && (
            <div style={{ textAlign: 'center', marginTop: '50px' }}>
              <h2>Welcome to Event Planning App</h2>
              <p>Please login or register to manage your events.</p>
            </div>
          )}
        </div>
      </div>
    );
  }

  return (
    <div style={{ minHeight: '100vh', backgroundColor: '#f5f5f5' }}>
      <nav style={{ backgroundColor: '#007bff', padding: '1rem', color: 'white' }}>
        <h1 style={{ margin: 0, display: 'inline' }}>Event Planning App</h1>
        <span style={{ marginLeft: '20px', fontSize: '14px' }}>({userRole})</span>
        <div style={{ float: 'right' }}>
          <button 
            onClick={() => setCurrentView('events')}
            style={{ marginRight: '10px', padding: '5px 15px', backgroundColor: 'transparent', color: 'white', border: '1px solid white' }}
          >
            Browse Events
          </button>
          {(userRole === 'COORDINATOR' || userRole === 'ORGANIZER') && (
            <>
              <button 
                onClick={() => setCurrentView('tasks')}
                style={{ marginRight: '10px', padding: '5px 15px', backgroundColor: 'transparent', color: 'white', border: '1px solid white' }}
              >
                My Tasks
              </button>
              <button 
                onClick={() => setCurrentView('rsvp-dashboard')}
                style={{ marginRight: '10px', padding: '5px 15px', backgroundColor: 'transparent', color: 'white', border: '1px solid white' }}
              >
                RSVP Dashboard
              </button>
            </>
          )}
          {userRole === 'ORGANIZER' && (
            <button 
              onClick={() => setCurrentView('create')}
              style={{ marginRight: '10px', padding: '5px 15px', backgroundColor: 'transparent', color: 'white', border: '1px solid white' }}
            >
              Create Event
            </button>
          )}
          <button 
            onClick={logout}
            style={{ padding: '5px 15px', backgroundColor: '#dc3545', color: 'white', border: 'none' }}
          >
            Logout
          </button>
        </div>
      </nav>

      <div style={{ padding: '20px' }}>
        {currentView === 'events' && <EventList />}
        {currentView === 'create' && userRole === 'ORGANIZER' && <EventForm onEventCreated={() => setCurrentView('events')} />}
        {currentView === 'tasks' && (userRole === 'COORDINATOR' || userRole === 'ORGANIZER') && <div>Task Management Coming Soon</div>}
        {currentView === 'rsvp-dashboard' && (userRole === 'COORDINATOR' || userRole === 'ORGANIZER') && <RSVPDashboard />}
      </div>
    </div>
  );
};

const App: React.FC = () => {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
};

export default App;