import apiClient from './client';

export interface Budget {
  id: string;
  eventId: string;
  expenses: Expense[];
}

export interface Expense {
  description: string;
  category: string;
  amount: number;
}

export interface AddExpenseRequest {
  description: string;
  category: string;
  amount: number;
}

export interface BudgetSummary {
  totalAmount: number;
  expenseCount: number;
}

export const budgetAPI = {
  createBudget: async (eventId: string): Promise<Budget> => {
    const response = await apiClient.post(`/events/${eventId}/budget`);
    return response.data;
  },

  getBudget: async (eventId: string): Promise<Budget> => {
    const response = await apiClient.get(`/events/${eventId}/budget`);
    return response.data;
  },

  addExpense: async (eventId: string, expenseData: AddExpenseRequest): Promise<Budget> => {
    const response = await apiClient.post(`/events/${eventId}/expenses`, expenseData);
    return response.data;
  },

  getBudgetSummary: async (eventId: string): Promise<BudgetSummary> => {
    const response = await apiClient.get(`/events/${eventId}/budget-summary`);
    return response.data;
  },
};