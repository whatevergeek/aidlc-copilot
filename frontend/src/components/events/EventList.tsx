import React, { useState, useEffect } from 'react';
import { eventAPI, Event } from '../../api/events';

const EventList: React.FC = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    try {
      const data = await eventAPI.getEvents();
      setEvents(data);
    } catch (error) {
      console.error('Failed to load events:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div>Loading events...</div>;

  return (
    <div style={{ padding: '20px' }}>
      <h2>My Events</h2>
      {events.length === 0 ? (
        <p>No events found. Create your first event!</p>
      ) : (
        <div style={{ display: 'grid', gap: '15px' }}>
          {events.map((event) => (
            <div key={event.id} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '5px' }}>
              <h3>{event.name}</h3>
              <p>{event.description}</p>
              <p><strong>Date:</strong> {new Date(event.dateTime).toLocaleString()}</p>
              <p><strong>Location:</strong> {event.location}</p>
              <p><strong>Share Link:</strong> {event.shareableLink}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default EventList;