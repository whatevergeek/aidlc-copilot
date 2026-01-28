package com.eventplanning.service;

import com.eventplanning.model.Task;
import com.eventplanning.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(String eventId, String title, String description, String assigneeId, LocalDate dueDate) {
        Task task = new Task(eventId, title, description, assigneeId, dueDate);
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(String taskId) {
        return taskRepository.findById(taskId);
    }

    public List<Task> getTasksByEvent(String eventId) {
        return taskRepository.findByEventId(eventId);
    }

    public List<Task> getTasksByAssignee(String assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    public Task updateTask(String taskId, String title, String description, LocalDate dueDate) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        
        Task task = taskOpt.get();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        return taskRepository.save(task);
    }

    public Task assignTask(String taskId, String assigneeId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        
        Task task = taskOpt.get();
        task.setAssigneeId(assigneeId);
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(String taskId, Task.TaskStatus status) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        
        Task task = taskOpt.get();
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public void deleteTask(String taskId) {
        taskRepository.deleteById(taskId);
    }
}