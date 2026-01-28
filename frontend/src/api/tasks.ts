import apiClient from './client';

export interface Task {
  id: string;
  eventId: string;
  title: string;
  description: string;
  assigneeId: string;
  dueDate: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED';
}

export interface CreateTaskRequest {
  title: string;
  description: string;
  assigneeId: string;
  dueDate: string;
}

export const taskAPI = {
  createTask: async (eventId: string, taskData: CreateTaskRequest): Promise<Task> => {
    const response = await apiClient.post(`/events/${eventId}/tasks`, taskData);
    return response.data;
  },

  getEventTasks: async (eventId: string): Promise<Task[]> => {
    const response = await apiClient.get(`/events/${eventId}/tasks`);
    return response.data;
  },

  getAssignedTasks: async (): Promise<Task[]> => {
    const response = await apiClient.get('/tasks/assigned');
    return response.data;
  },

  updateTaskStatus: async (taskId: string, status: Task['status']): Promise<Task> => {
    const response = await apiClient.put(`/tasks/${taskId}/status`, { status });
    return response.data;
  },
};