package org.example.buntu.taskmanagmentsystemspringbook.controller;

import org.example.buntu.taskmanagmentsystemspringbook.model.Task;
import org.example.buntu.taskmanagmentsystemspringbook.service.TaskService;
import org.example.buntu.taskmanagmentsystemspringbook.service.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Task Management
 * Handles HTTP requests and responses
 * @RestController combines @Controller and @ResponseBody
 */
@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*") // Allow CORS for frontend integration
public class TaskController {

    private final TaskService taskService;

    /**
     * Constructor-based Dependency Injection
     */
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * GET /api/tasks - Get all tasks
     */
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        Map<String, Object> response = new HashMap<>();
        response.put("tasks", tasks);
        response.put("count", tasks.size());
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/tasks/{id} - Get task by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * POST /api/tasks - Create new task
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createTask(@Valid @RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        Map<String, Object> response = new HashMap<>();
        response.put("task", createdTask);
        response.put("message", "Task created successfully");
        response.put("status", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * PUT /api/tasks/{id} - Update existing task
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        Map<String, Object> response = new HashMap<>();
        response.put("task", updatedTask);
        response.put("message", "Task updated successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/tasks/{id} - Delete task
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task deleted successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/tasks/status/{status} - Get tasks by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Task.TaskStatus status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/priority/{priority} - Get tasks by priority
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Task.TaskPriority priority) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/stats - Get task statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getTaskStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTasks", taskService.getTaskCount());
        stats.put("todoTasks", taskService.getTasksByStatus(Task.TaskStatus.TODO).size());
        stats.put("inProgressTasks", taskService.getTasksByStatus(Task.TaskStatus.IN_PROGRESS).size());
        stats.put("completedTasks", taskService.getTasksByStatus(Task.TaskStatus.COMPLETED).size());

        return ResponseEntity.ok(stats);
    }

    /**
     * Global Exception Handler for this controller
     */
    @ExceptionHandler(TaskServiceImpl.TaskNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTaskNotFound(TaskServiceImpl.TaskNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("status", "error");
        error.put("code", 404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Internal server error");
        error.put("message", ex.getMessage());
        error.put("status", "error");
        error.put("code", 500);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
