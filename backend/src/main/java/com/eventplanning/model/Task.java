package com.eventplanning.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String eventId;
    private String title;
    private String description;
    private String assigneeId;
    private LocalDate dueDate;
    private TaskStatus status;

    public enum TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED
    }

    public Task() {}

    public Task(String eventId, String title, String description, String assigneeId, LocalDate dueDate) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.assigneeId = assigneeId;
        this.dueDate = dueDate;
        this.status = TaskStatus.PENDING;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAssigneeId() { return assigneeId; }
    public void setAssigneeId(String assigneeId) { this.assigneeId = assigneeId; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}