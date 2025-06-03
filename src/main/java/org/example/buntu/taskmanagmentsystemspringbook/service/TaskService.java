package org.example.buntu.taskmanagmentsystemspringbook.service;

import org.example.buntu.taskmanagmentsystemspringbook.model.Task;
import java.util.List;

/**
 * Task Service Interface
 * Defines business logic operations for task management
 */
public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    boolean deleteTask(Long id);
    List<Task> getTasksByStatus(Task.TaskStatus status);
    List<Task> getTasksByPriority(Task.TaskPriority priority);
    long getTaskCount();
}