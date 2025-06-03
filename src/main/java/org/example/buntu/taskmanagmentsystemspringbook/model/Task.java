package org.example.buntu.taskmanagmentsystemspringbook.model;

// Task.java

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Task Entity Model
 * Represents a task in the task management system
 */
public class Task {
    @Setter
    private Long id;

    @Setter
    @NotBlank(message = "Title is required")
    private String title;

    @Setter
    private String description;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    @Setter
    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;
    @Setter
    private LocalDateTime dueDate;

    // Constructors
    public Task() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = TaskStatus.TODO;
        this.priority = TaskPriority.MEDIUM;
    }

    public Task(String title, String description, TaskStatus status, TaskPriority priority) {
        this();
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;

    }

    // Enums
    public enum TaskStatus {
        TODO, IN_PROGRESS, COMPLETED, CANCELLED
    }

    public enum TaskPriority {
        LOW, MEDIUM, HIGH, URGENT
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }
}