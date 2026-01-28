package com.eventplanning.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "budgets")
public class Budget {
    @Id
    private String id;
    private String eventId;
    private List<Expense> expenses;

    public Budget() {
        this.expenses = new ArrayList<>();
    }

    public Budget(String eventId) {
        this.eventId = eventId;
        this.expenses = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public List<Expense> getExpenses() { return expenses; }
    public void setExpenses(List<Expense> expenses) { this.expenses = expenses; }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    public BigDecimal getTotalAmount() {
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static class Expense {
        private String description;
        private String category;
        private BigDecimal amount;

        public Expense() {}

        public Expense(String description, String category, BigDecimal amount) {
            this.description = description;
            this.category = category;
            this.amount = amount;
        }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }
}