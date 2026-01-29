import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { vi } from 'vitest';
import LoginForm from '../auth/LoginForm';

// Mock the auth context
const mockLogin = vi.fn();
vi.mock('../../contexts/AuthContext', () => ({
  useAuth: () => ({
    login: mockLogin,
    user: null,
    loading: false
  })
}));

// Mock the auth API
vi.mock('../../api/auth', () => ({
  authAPI: {
    login: vi.fn().mockResolvedValue({ 
      token: 'fake-token', 
      role: 'ORGANIZER',
      name: 'Test User',
      email: 'test@example.com'
    })
  }
}));

describe('LoginForm', () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  test('renders form elements', () => {
    render(<LoginForm />);

    expect(screen.getByRole('heading', { name: /login/i })).toBeInTheDocument();
    expect(screen.getByText(/email/i)).toBeInTheDocument();
    expect(screen.getByText(/password/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /login/i })).toBeInTheDocument();
  });

  test('calls login with token and role on successful submission', async () => {
    render(<LoginForm />);

    const inputs = screen.getAllByDisplayValue('');
    const emailInput = inputs[0]; // First input (email)
    const passwordInput = inputs[1]; // Second input (password)
    const loginButton = screen.getByRole('button', { name: /login/i });

    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password123' } });
    fireEvent.click(loginButton);

    await waitFor(() => {
      expect(mockLogin).toHaveBeenCalledWith('fake-token', {
        name: 'Test User',
        email: 'test@example.com',
        role: 'ORGANIZER'
      });
    });
  });
});