import React, { useState } from 'react';
import { authAPI } from '../../api/auth';

const RegisterForm: React.FC = () => {
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('ATTENDEE');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await authAPI.register({ email, name, password, role });
      setMessage('Registration successful! You can now login.');
      setError('');
    } catch (err: any) {
      setError(err.response?.data?.error || 'Registration failed');
      setMessage('');
    }
  };

  return (
    <div style={{ maxWidth: '400px', margin: '0 auto', padding: '20px' }}>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '15px' }}>
          <label>Name:</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', marginTop: '5px' }}
          />
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', marginTop: '5px' }}
          />
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', marginTop: '5px' }}
          />
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label>Role:</label>
          <select
            value={role}
            onChange={(e) => setRole(e.target.value)}
            style={{ width: '100%', padding: '8px', marginTop: '5px' }}
          >
            <option value="ATTENDEE">Event Attendee</option>
            <option value="COORDINATOR">Event Coordinator</option>
            <option value="ORGANIZER">Event Organizer</option>
          </select>
          <small style={{ color: '#666', fontSize: '12px', display: 'block', marginTop: '5px' }}>
            {role === 'ATTENDEE' && 'Browse and RSVP to events'}
            {role === 'COORDINATOR' && 'Help organize events and complete tasks'}
            {role === 'ORGANIZER' && 'Create events and manage teams'}
          </small>
        </div>
        {error && <div style={{ color: 'red', marginBottom: '15px' }}>{error}</div>}
        {message && <div style={{ color: 'green', marginBottom: '15px' }}>{message}</div>}
        <button type="submit" style={{ width: '100%', padding: '10px', backgroundColor: '#28a745', color: 'white', border: 'none' }}>
          Register
        </button>
      </form>
    </div>
  );
};

export default RegisterForm;