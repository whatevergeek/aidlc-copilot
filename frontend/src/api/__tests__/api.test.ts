import { vi } from 'vitest';
import { authAPI } from '../auth';
import { eventAPI } from '../events';

// Mock axios
vi.mock('../client', () => ({
  default: {
    post: vi.fn(),
    get: vi.fn(),
    put: vi.fn(),
    delete: vi.fn()
  }
}));

describe('Auth API', () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  test('login makes correct API call', async () => {
    const { default: apiClient } = await import('../client');
    const mockResponse = {
      data: {
        user: { id: '1', username: 'testuser', email: 'test@example.com' },
        token: 'fake-token'
      }
    };
    (apiClient.post as any).mockResolvedValue(mockResponse);

    const result = await authAPI.login({ email: 'testuser', password: 'password123' });

    expect(apiClient.post).toHaveBeenCalledWith('/auth/login', {
      email: 'testuser',
      password: 'password123'
    });
    expect(result).toEqual(mockResponse.data);
  });

  test('register makes correct API call', async () => {
    const { default: apiClient } = await import('../client');
    const mockResponse = {
      data: {
        user: { id: '1', username: 'testuser', email: 'test@example.com' }
      }
    };
    (apiClient.post as any).mockResolvedValue(mockResponse);

    const result = await authAPI.register({ email: 'test@example.com', name: 'testuser', password: 'password123' });

    expect(apiClient.post).toHaveBeenCalledWith('/auth/register', {
      email: 'test@example.com',
      name: 'testuser',
      password: 'password123'
    });
    expect(result).toEqual(mockResponse.data);
  });
});

describe('Events API', () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  test('getEvents makes correct API call', async () => {
    const { default: apiClient } = await import('../client');
    const mockEvents = [
      { id: '1', name: 'Event 1', description: 'Desc 1', dateTime: '2024-12-25T10:00:00', location: 'Location 1', organizerId: '1', shareableLink: 'link1' },
      { id: '2', name: 'Event 2', description: 'Desc 2', dateTime: '2024-12-26T10:00:00', location: 'Location 2', organizerId: '1', shareableLink: 'link2' }
    ];
    (apiClient.get as any).mockResolvedValue({ data: mockEvents });

    const result = await eventAPI.getEvents();

    expect(apiClient.get).toHaveBeenCalledWith('/events');
    expect(result).toEqual(mockEvents);
  });

  test('createEvent makes correct API call', async () => {
    const { default: apiClient } = await import('../client');
    const newEvent = {
      name: 'New Event',
      description: 'New Description',
      dateTime: '2024-12-25T10:00:00',
      location: 'New Location'
    };
    const mockResponse = { data: { id: '1', ...newEvent, organizerId: '1', shareableLink: 'link1' } };
    (apiClient.post as any).mockResolvedValue(mockResponse);

    const result = await eventAPI.createEvent(newEvent);

    expect(apiClient.post).toHaveBeenCalledWith('/events', newEvent);
    expect(result).toEqual(mockResponse.data);
  });

  test('getEventById makes correct API call', async () => {
    const { default: apiClient } = await import('../client');
    const mockEvent = { id: '1', name: 'Event 1', description: 'Desc 1', dateTime: '2024-12-25T10:00:00', location: 'Location 1', organizerId: '1', shareableLink: 'link1' };
    (apiClient.get as any).mockResolvedValue({ data: mockEvent });

    const result = await eventAPI.getEventById('1');

    expect(apiClient.get).toHaveBeenCalledWith('/events/1');
    expect(result).toEqual(mockEvent);
  });

  test('getEventByLink makes correct API call', async () => {
    const { default: apiClient } = await import('../client');
    const mockEvent = { id: '1', name: 'Event 1', description: 'Desc 1', dateTime: '2024-12-25T10:00:00', location: 'Location 1', organizerId: '1', shareableLink: 'link1' };
    (apiClient.get as any).mockResolvedValue({ data: mockEvent });

    const result = await eventAPI.getEventByLink('link1');

    expect(apiClient.get).toHaveBeenCalledWith('/events/share/link1');
    expect(result).toEqual(mockEvent);
  });
});