import React, { useState, useEffect } from 'react';
import { eventAPI, RSVPDashboardItem } from '../../api/events';

const RSVPDashboard: React.FC = () => {
  const [dashboardData, setDashboardData] = useState<RSVPDashboardItem[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      console.log('Loading RSVP dashboard...');
      const data = await eventAPI.getRSVPDashboard();
      console.log('RSVP Dashboard data received:', data);
      console.log('Data length:', data.length);
      setDashboardData(data);
    } catch (error) {
      console.error('Failed to load RSVP dashboard:', error);
      console.error('Error details:', error.response?.data);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div>Loading RSVP dashboard...</div>;

  return (
    <div style={{ padding: '20px' }}>
      <h2>RSVP Dashboard</h2>
      {dashboardData.length === 0 ? (
        <p>No events found.</p>
      ) : (
        <div style={{ display: 'grid', gap: '15px' }}>
          {dashboardData.map((item) => (
            <div key={item.eventId} style={{ 
              border: '1px solid #ddd', 
              padding: '15px', 
              borderRadius: '5px',
              backgroundColor: '#f9f9f9'
            }}>
              <h3>{item.eventName}</h3>
              <p><strong>Date:</strong> {new Date(item.eventDate).toLocaleString()}</p>
              
              <div style={{ 
                display: 'grid', 
                gridTemplateColumns: 'repeat(3, 1fr)', 
                gap: '10px', 
                marginTop: '15px' 
              }}>
                <div style={{ 
                  textAlign: 'center', 
                  padding: '10px', 
                  backgroundColor: '#28a745', 
                  color: 'white', 
                  borderRadius: '4px' 
                }}>
                  <div style={{ fontSize: '24px', fontWeight: 'bold' }}>{item.yesCount}</div>
                  <div style={{ fontSize: '12px' }}>Attending</div>
                </div>
                
                <div style={{ 
                  textAlign: 'center', 
                  padding: '10px', 
                  backgroundColor: '#dc3545', 
                  color: 'white', 
                  borderRadius: '4px' 
                }}>
                  <div style={{ fontSize: '24px', fontWeight: 'bold' }}>{item.noCount}</div>
                  <div style={{ fontSize: '12px' }}>Not Attending</div>
                </div>
                
                <div style={{ 
                  textAlign: 'center', 
                  padding: '10px', 
                  backgroundColor: '#6c757d', 
                  color: 'white', 
                  borderRadius: '4px' 
                }}>
                  <div style={{ fontSize: '24px', fontWeight: 'bold' }}>{item.totalCount}</div>
                  <div style={{ fontSize: '12px' }}>Total RSVPs</div>
                </div>
              </div>
              
              {item.totalCount > 0 && (
                <div style={{ marginTop: '10px', fontSize: '14px', color: '#666' }}>
                  Attendance Rate: {Math.round((item.yesCount / item.totalCount) * 100)}%
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default RSVPDashboard;