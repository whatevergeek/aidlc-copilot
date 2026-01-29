import { render, screen, waitFor } from '@testing-library/react';
import { vi } from 'vitest';
import EventList from '../events/EventList';

// Mock the events API
vi.mock('../../api/events', () => ({
  eventAPI: {
    getEvents: vi.fn()
  }
}));

// Mock the auth context
vi.mock('../../contexts/AuthContext', () => ({
  useAuth: () => ({
    user: { id: '1', email: 'test@example.com' },
    token: 'fake-token'
  })
}));

const mockEvents = [
  {
    id: '1',
    name: 'Test Event 1',
    description: 'Description 1',
    dateTime: '2024-12-25T10:00:00',
    location: 'Location 1',
    organizerId: '1',
    shareableLink: 'link1'
  },
  {
    id: '2',
    name: 'Test Event 2',
    description: 'Description 2',
    dateTime: '2024-12-26T10:00:00',
    location: 'Location 2',
    organizerId: '1',
    shareableLink: 'link2'
  }
];

describe('EventList', () => {
  test('shows loading state initially', () => {
    render(<EventList />);
    expect(screen.getByText(/loading events/i)).toBeInTheDocument();
  });
});