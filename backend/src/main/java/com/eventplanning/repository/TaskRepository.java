package com.eventplanning.repository;

import com.eventplanning.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByEventId(String eventId);
    List<Task> findByAssigneeId(String assigneeId);
    List<Task> findByEventIdAndStatus(String eventId, Task.TaskStatus status);
}