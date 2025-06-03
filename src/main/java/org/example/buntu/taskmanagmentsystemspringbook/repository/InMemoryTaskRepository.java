package org.example.buntu.taskmanagmentsystemspringbook.repository;

import org.example.buntu.taskmanagmentsystemspringbook.model.Task;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-Memory Task Repository Implementation
 * Uses ConcurrentHashMap for thread-safe operations
 * @Repository annotation makes this a Spring-managed bean
 */
@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public InMemoryTaskRepository() {
        // Initialize with sample data
        initializeSampleData();
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
        }
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public boolean deleteById(Long id) {
        return tasks.remove(id) != null;
    }

    @Override
    public List<Task> findByStatus(Task.TaskStatus status) {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByPriority(Task.TaskPriority priority) {
        return tasks.values().stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toList());
    }

    private void initializeSampleData() {
        save(new Task("Setup Development Environment",
                "Install Java, Maven, and IDE",
                Task.TaskStatus.COMPLETED,
                Task.TaskPriority.HIGH));

        save(new Task("Learn Spring Boot",
                "Complete Spring Boot tutorial",
                Task.TaskStatus.IN_PROGRESS,
                Task.TaskPriority.MEDIUM));

        save(new Task("Build REST API",
                "Create task management REST endpoints",
                Task.TaskStatus.TODO,
                Task.TaskPriority.HIGH));
    }
}
