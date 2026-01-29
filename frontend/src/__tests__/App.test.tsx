import { render, screen } from '@testing-library/react';
import { vi } from 'vitest';
import App from '../App';

// Mock all API calls
vi.mock('../api/auth', () => ({
  authAPI: {
    login: vi.fn(),
    register: vi.fn()
  }
}));

vi.mock('../api/events', () => ({
  eventAPI: {
    getEvents: vi.fn().mockResolvedValue([])
  }
}));

// Mock localStorage
const localStorageMock = {
  getItem: vi.fn(),
  setItem: vi.fn(),
  removeItem: vi.fn(),
  clear: vi.fn(),
};
Object.defineProperty(window, 'localStorage', {
  value: localStorageMock
});

describe('App Integration', () => {
  beforeEach(() => {
    vi.clearAllMocks();
    localStorageMock.getItem.mockReturnValue(null);
  });

  test('renders login form when not authenticated', () => {
    render(<App />);
    
    expect(screen.getAllByText(/event planning/i)[0]).toBeInTheDocument();
    expect(screen.getByText(/please login or register/i)).toBeInTheDocument();
  });

  test('renders dashboard when authenticated', () => {
    // Mock authenticated state with user object
    localStorageMock.getItem.mockImplementation((key) => {
      if (key === 'token') return 'fake-token';
      if (key === 'user') return JSON.stringify({
        name: 'Test User',
        email: 'test@example.com',
        role: 'ORGANIZER'
      });
      return null;
    });

    render(<App />);
    
    expect(screen.getByText(/browse events/i)).toBeInTheDocument();
    expect(screen.getByText(/create event/i)).toBeInTheDocument();
    expect(screen.getByText(/\(ORGANIZER\)/i)).toBeInTheDocument();
  });

  test('navigation works correctly', () => {
    localStorageMock.getItem.mockImplementation((key) => {
      if (key === 'token') return 'fake-token';
      if (key === 'user') return JSON.stringify({
        name: 'Test User',
        email: 'test@example.com',
        role: 'COORDINATOR'
      });
      return null;
    });

    render(<App />);
    
    // Check that navigation links are present for coordinator
    expect(screen.getByText(/browse events/i)).toBeInTheDocument();
    expect(screen.getByText(/rsvp dashboard/i)).toBeInTheDocument();
    expect(screen.getByText(/logout/i)).toBeInTheDocument();
    expect(screen.getByText(/\(COORDINATOR\)/i)).toBeInTheDocument();
  });

  test('attendee role shows correct navigation', () => {
    localStorageMock.getItem.mockImplementation((key) => {
      if (key === 'token') return 'fake-token';
      if (key === 'user') return JSON.stringify({
        name: 'Test User',
        email: 'test@example.com',
        role: 'ATTENDEE'
      });
      return null;
    });

    render(<App />);
    
    // Attendee should only see browse events (RSVP section removed)
    expect(screen.getByText(/browse events/i)).toBeInTheDocument();
    expect(screen.getByText(/\(ATTENDEE\)/i)).toBeInTheDocument();
    expect(screen.queryByText(/create event/i)).not.toBeInTheDocument();
    expect(screen.queryByText(/my tasks/i)).not.toBeInTheDocument();
  });
});