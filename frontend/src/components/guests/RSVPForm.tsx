import React, { useState } from 'react';
import { guestAPI } from '../../api/guests';

interface RSVPFormProps {
  eventId: string;
}

const RSVPForm: React.FC<RSVPFormProps> = ({ eventId }) => {
  const [guestName, setGuestName] = useState('');
  const [guestEmail, setGuestEmail] = useState('');
  const [response, setResponse] = useState<'YES' | 'NO'>('YES');
  const [message, setMessage] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await guestAPI.submitRSVP(eventId, { guestName, guestEmail, response });
      setMessage('RSVP submitted successfully!');
      setGuestName('');
      setGuestEmail('');
    } catch (error) {
      setMessage('Failed to submit RSVP');
    }
  };

  return (
    <div style={{ maxWidth: '400px', margin: '20px auto', padding: '20px', border: '1px solid #ddd' }}>
      <h3>RSVP to Event</h3>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '15px' }}>
          <label>Your Name:</label>
          <input
            type="text"
            value={guestName}
            onChange={(e) => setGuestName(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', marginTop: '5px' }}
          />
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label>Your Email:</label>
          <input
            type="email"
            value={guestEmail}
            onChange={(e) => setGuestEmail(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', marginTop: '5px' }}
          />
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label>Response:</label>
          <select
            value={response}
            onChange={(e) => setResponse(e.target.value as 'YES' | 'NO')}
            style={{ width: '100%', padding: '8px', marginTop: '5px' }}
          >
            <option value="YES">Yes, I'll attend</option>
            <option value="NO">No, I can't attend</option>
          </select>
        </div>
        {message && <div style={{ color: 'green', marginBottom: '15px' }}>{message}</div>}
        <button type="submit" style={{ width: '100%', padding: '10px', backgroundColor: '#28a745', color: 'white', border: 'none' }}>
          Submit RSVP
        </button>
      </form>
    </div>
  );
};

export default RSVPForm;