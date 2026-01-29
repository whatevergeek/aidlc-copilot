import { render, screen, fireEvent } from '@testing-library/react';
import { vi } from 'vitest';
import RegisterForm from '../components/auth/RegisterForm';

// Mock the auth API
vi.mock('../api/auth', () => ({
  authAPI: {
    register: vi.fn().mockResolvedValue({ message: 'Success' })
  }
}));

describe('RegisterForm', () => {
  test('renders role selection dropdown', () => {
    render(<RegisterForm />);
    
    const roleSelect = screen.getByRole('combobox');
    expect(roleSelect).toBeInTheDocument();
    
    // Check all role options are present
    expect(screen.getByText('Event Attendee')).toBeInTheDocument();
    expect(screen.getByText('Event Coordinator')).toBeInTheDocument();
    expect(screen.getByText('Event Organizer')).toBeInTheDocument();
  });

  test('shows role descriptions when role is selected', () => {
    render(<RegisterForm />);
    
    const roleSelect = screen.getByRole('combobox');
    
    // Test attendee description (default)
    expect(screen.getByText('Browse and RSVP to events')).toBeInTheDocument();
    
    // Test coordinator description
    fireEvent.change(roleSelect, { target: { value: 'COORDINATOR' } });
    expect(screen.getByText('Help organize events and complete tasks')).toBeInTheDocument();
    
    // Test organizer description
    fireEvent.change(roleSelect, { target: { value: 'ORGANIZER' } });
    expect(screen.getByText('Create events and manage teams')).toBeInTheDocument();
  });

  test('defaults to ATTENDEE role', () => {
    render(<RegisterForm />);
    
    const roleSelect = screen.getByRole('combobox') as HTMLSelectElement;
    expect(roleSelect.value).toBe('ATTENDEE');
  });
});