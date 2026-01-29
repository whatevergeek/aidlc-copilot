import apiClient from './client';

export interface Event {
  id: string;
  name: string;
  description: string;
  dateTime: string;
  location: string;
  organizerId: string;
  shareableLink: string;
}

export interface CreateEventRequest {
  name: string;
  description: string;
  dateTime: string;
  location: string;
}

export interface RSVPDashboardItem {
  eventId: string;
  eventName: string;
  eventDate: string;
  yesCount: number;
  noCount: number;
  totalCount: number;
}

export const eventAPI = {
  createEvent: async (eventData: CreateEventRequest): Promise<Event> => {
    const response = await apiClient.post('/events', eventData);
    return response.data;
  },

  getEvents: async (): Promise<Event[]> => {
    const response = await apiClient.get('/events');
    return response.data;
  },

  getEventById: async (eventId: string): Promise<Event> => {
    const response = await apiClient.get(`/events/${eventId}`);
    return response.data;
  },

  getEventByLink: async (shareableLink: string): Promise<Event> => {
    const response = await apiClient.get(`/events/share/${shareableLink}`);
    return response.data;
  },

  getMyRSVP: async (eventId: string): Promise<{ hasRSVP: boolean; response?: string }> => {
    const response = await apiClient.get(`/events/${eventId}/my-rsvp`);
    return response.data;
  },

  getRSVPDashboard: async (): Promise<RSVPDashboardItem[]> => {
    const response = await apiClient.get('/rsvp-dashboard');
    return response.data;
  },
};