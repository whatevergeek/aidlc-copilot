import apiClient from './client';

export interface RSVP {
  id: string;
  eventId: string;
  guestEmail: string;
  guestName: string;
  response: 'YES' | 'NO';
}

export interface RSVPRequest {
  guestEmail: string;
  guestName: string;
  response: 'YES' | 'NO';
}

export interface RSVPSummary {
  yesCount: number;
  noCount: number;
  totalCount: number;
}

export const guestAPI = {
  submitRSVP: async (eventId: string, rsvpData: RSVPRequest): Promise<RSVP> => {
    const response = await apiClient.post(`/events/${eventId}/rsvp`, rsvpData);
    return response.data;
  },

  getGuestList: async (eventId: string): Promise<RSVP[]> => {
    const response = await apiClient.get(`/events/${eventId}/guests`);
    return response.data;
  },

  getRSVPSummary: async (eventId: string): Promise<RSVPSummary> => {
    const response = await apiClient.get(`/events/${eventId}/rsvp-summary`);
    return response.data;
  },
};