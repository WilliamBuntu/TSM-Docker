package org.example.buntu.taskmanagmentsystemspringbook.repository;

// TaskRepository.java (Interface)
import org.example.buntu.taskmanagmentsystemspringbook.model.Task;
import java.util.List;
import java.util.Optional;

/**
 * Task Repository Interface
 * Defines contract for task data access operations
 */
public interface TaskRepository {
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Task save(Task task);
    boolean deleteById(Long id);
    List<Task> findByStatus(Task.TaskStatus status);
    List<Task> findByPriority(Task.TaskPriority priority);
}