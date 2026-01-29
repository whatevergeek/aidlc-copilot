import React, { useState, useEffect } from 'react';
import { eventAPI, Event } from '../../api/events';
import { guestAPI } from '../../api/guests';
import { useAuth } from '../../contexts/AuthContext';

interface EventWithRSVP extends Event {
  userRSVP?: {
    hasRSVP: boolean;
    response?: string;
  };
}

const EventList: React.FC = () => {
  const [events, setEvents] = useState<EventWithRSVP[]>([]);
  const [loading, setLoading] = useState(true);
  const [rsvpLoading, setRsvpLoading] = useState<string | null>(null);
  const { user } = useAuth();

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    try {
      const data = await eventAPI.getEvents();
      const eventsWithRSVP = await Promise.all(
        data.map(async (event) => {
          try {
            const rsvpStatus = await eventAPI.getMyRSVP(event.id);
            return { ...event, userRSVP: rsvpStatus };
          } catch {
            return { ...event, userRSVP: { hasRSVP: false } };
          }
        })
      );
      setEvents(eventsWithRSVP);
    } catch (error) {
      console.error('Failed to load events:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleRSVP = async (eventId: string, response: 'YES' | 'NO') => {
    if (!user) return;
    
    setRsvpLoading(eventId);
    try {
      await guestAPI.submitRSVP(eventId, {
        guestName: user.name,
        guestEmail: user.email,
        response
      });
      // Reload events to update RSVP status
      await loadEvents();
    } catch (error) {
      alert('Failed to submit RSVP');
    } finally {
      setRsvpLoading(null);
    }
  };

  const getRSVPButtonStyle = (eventId: string, buttonType: 'YES' | 'NO') => {
    const event = events.find(e => e.id === eventId);
    const isSelected = event?.userRSVP?.hasRSVP && event.userRSVP.response === buttonType;
    const baseStyle = {
      padding: '8px 16px',
      border: 'none',
      borderRadius: '4px',
      cursor: rsvpLoading === eventId ? 'not-allowed' : 'pointer',
      opacity: rsvpLoading === eventId ? 0.6 : 1
    };

    if (buttonType === 'YES') {
      return {
        ...baseStyle,
        backgroundColor: isSelected ? '#155724' : '#28a745',
        color: 'white'
      };
    } else {
      return {
        ...baseStyle,
        backgroundColor: isSelected ? '#721c24' : '#dc3545',
        color: 'white'
      };
    }
  };

  if (loading) return <div>Loading events...</div>;

  return (
    <div style={{ padding: '20px' }}>
      <h2>Browse Events</h2>
      {events.length === 0 ? (
        <p>No events found.</p>
      ) : (
        <div style={{ display: 'grid', gap: '15px' }}>
          {events.map((event) => (
            <div key={event.id} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '5px' }}>
              <h3>{event.name}</h3>
              <p>{event.description}</p>
              <p><strong>Date:</strong> {new Date(event.dateTime).toLocaleString()}</p>
              <p><strong>Location:</strong> {event.location}</p>
              
              {event.userRSVP?.hasRSVP && (
                <p style={{ color: '#666', fontSize: '14px', marginBottom: '10px' }}>
                  Your RSVP: <strong>{event.userRSVP.response === 'YES' ? "I'll attend" : "I can't attend"}</strong>
                </p>
              )}
              
              <div style={{ marginTop: '15px', display: 'flex', gap: '10px' }}>
                <button
                  onClick={() => handleRSVP(event.id, 'YES')}
                  disabled={rsvpLoading === event.id}
                  style={getRSVPButtonStyle(event.id, 'YES')}
                >
                  {rsvpLoading === event.id ? 'Updating...' : "Yes, I'll attend"}
                </button>
                <button
                  onClick={() => handleRSVP(event.id, 'NO')}
                  disabled={rsvpLoading === event.id}
                  style={getRSVPButtonStyle(event.id, 'NO')}
                >
                  {rsvpLoading === event.id ? 'Updating...' : "No, I can't attend"}
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default EventList;