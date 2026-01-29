import { render, screen } from '@testing-library/react';
import { vi } from 'vitest';
import App from '../App';

// Mock all API calls
vi.mock('./api/auth', () => ({
  authAPI: {
    login: vi.fn(),
    register: vi.fn()
  }
}));

vi.mock('./api/events', () => ({
  eventsAPI: {
    getAll: vi.fn().mockResolvedValue([])
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
    // Mock authenticated state
    localStorageMock.getItem.mockImplementation((key) => {
      if (key === 'token') return 'fake-token';
      if (key === 'user') return JSON.stringify({ 
        id: '1', 
        username: 'testuser', 
        email: 'test@example.com' 
      });
      return null;
    });

    render(<App />);
    
    expect(screen.getByText(/my events/i)).toBeInTheDocument();
    expect(screen.getByText(/create event/i)).toBeInTheDocument();
  });

  test('navigation works correctly', () => {
    localStorageMock.getItem.mockImplementation((key) => {
      if (key === 'token') return 'fake-token';
      if (key === 'user') return JSON.stringify({ 
        id: '1', 
        username: 'testuser', 
        email: 'test@example.com' 
      });
      return null;
    });

    render(<App />);
    
    // Check that navigation links are present
    expect(screen.getByText(/my events/i)).toBeInTheDocument();
    expect(screen.getByText(/logout/i)).toBeInTheDocument();
  });
});