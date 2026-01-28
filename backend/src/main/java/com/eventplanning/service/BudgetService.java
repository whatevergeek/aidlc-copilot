package com.eventplanning.service;

import com.eventplanning.model.Budget;
import com.eventplanning.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BudgetService {
    
    @Autowired
    private BudgetRepository budgetRepository;

    public Budget createBudget(String eventId) {
        Optional<Budget> existingBudget = budgetRepository.findByEventId(eventId);
        if (existingBudget.isPresent()) {
            return existingBudget.get();
        }
        
        Budget budget = new Budget(eventId);
        return budgetRepository.save(budget);
    }

    public Optional<Budget> getBudgetByEventId(String eventId) {
        return budgetRepository.findByEventId(eventId);
    }

    public Budget addExpense(String eventId, String description, String category, BigDecimal amount) {
        Budget budget = budgetRepository.findByEventId(eventId)
                .orElseGet(() -> createBudget(eventId));
        
        Budget.Expense expense = new Budget.Expense(description, category, amount);
        budget.addExpense(expense);
        return budgetRepository.save(budget);
    }

    public BudgetSummary getBudgetSummary(String eventId) {
        Optional<Budget> budgetOpt = budgetRepository.findByEventId(eventId);
        if (budgetOpt.isEmpty()) {
            return new BudgetSummary(BigDecimal.ZERO, 0);
        }
        
        Budget budget = budgetOpt.get();
        return new BudgetSummary(budget.getTotalAmount(), budget.getExpenses().size());
    }

    public static class BudgetSummary {
        private BigDecimal totalAmount;
        private int expenseCount;

        public BudgetSummary(BigDecimal totalAmount, int expenseCount) {
            this.totalAmount = totalAmount;
            this.expenseCount = expenseCount;
        }

        public BigDecimal getTotalAmount() { return totalAmount; }
        public int getExpenseCount() { return expenseCount; }
    }
}