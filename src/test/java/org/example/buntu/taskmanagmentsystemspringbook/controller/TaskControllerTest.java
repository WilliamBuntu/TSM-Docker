package org.example.buntu.taskmanagmentsystemspringbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.buntu.taskmanagmentsystemspringbook.model.Task;
import org.example.buntu.taskmanagmentsystemspringbook.service.TaskService;
import org.example.buntu.taskmanagmentsystemspringbook.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(TaskControllerTest.TestConfig.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task testTask;
    private List<Task> taskList;

    @Configuration
    static class TestConfig {
        @Bean
        public TaskService taskService() {
            return Mockito.mock(TaskService.class);
        }
    }

    @BeforeEach
    void setUp() {
        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setStatus(Task.TaskStatus.TODO);
        testTask.setPriority(Task.TaskPriority.MEDIUM);
        testTask.setDueDate(LocalDate.now().plusDays(7).atStartOfDay());

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Another Task");
        task2.setStatus(Task.TaskStatus.IN_PROGRESS);
        task2.setPriority(Task.TaskPriority.HIGH);

        taskList = Arrays.asList(testTask, task2);
    }

    @Test
    void getAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(taskList);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasks", hasSize(2)))
                .andExpect(jsonPath("$.count", is(2)))
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.tasks[0].id", is(1)))
                .andExpect(jsonPath("$.tasks[0].title", is("Test Task")))
                .andExpect(jsonPath("$.tasks[1].id", is(2)))
                .andExpect(jsonPath("$.tasks[1].title", is("Another Task")));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getTaskById() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(testTask);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.description", is("Test Description")))
                .andExpect(jsonPath("$.status", is("TODO")));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void getTaskById_NotFound() throws Exception {
        when(taskService.getTaskById(99L))
                .thenThrow(new TaskServiceImpl.TaskNotFoundException("Task not found with id: 99"));

        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.error", containsString("Task not found")))
                .andExpect(jsonPath("$.code", is(404)));
    }

    @Test
    void createTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(testTask);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.task.id", is(1)))
                .andExpect(jsonPath("$.task.title", is("Test Task")))
                .andExpect(jsonPath("$.message", is("Task created successfully")))
                .andExpect(jsonPath("$.status", is("success")));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void updateTask() throws Exception {
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(testTask);

        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task.id", is(1)))
                .andExpect(jsonPath("$.message", is("Task updated successfully")))
                .andExpect(jsonPath("$.status", is("success")));

        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void deleteTask() throws Exception {
        // Fix: don't use doNothing() for non-void methods
        // If deleteTask returns a boolean or other value, mock that return
        when(taskService.deleteTask(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Task deleted successfully")))
                .andExpect(jsonPath("$.status", is("success")));

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void getTasksByStatus() throws Exception {
        List<Task> todoTasks = List.of(testTask);
        when(taskService.getTasksByStatus(Task.TaskStatus.TODO)).thenReturn(todoTasks);

        mockMvc.perform(get("/api/tasks/status/TODO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("TODO")));

        verify(taskService, times(1)).getTasksByStatus(Task.TaskStatus.TODO);
    }

    @Test
    void getTasksByPriority() throws Exception {
        List<Task> mediumPriorityTasks = List.of(testTask);
        when(taskService.getTasksByPriority(Task.TaskPriority.MEDIUM)).thenReturn(mediumPriorityTasks);

        mockMvc.perform(get("/api/tasks/priority/MEDIUM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].priority", is("MEDIUM")));

        verify(taskService, times(1)).getTasksByPriority(Task.TaskPriority.MEDIUM);
    }

    @Test
    void getTaskStats() throws Exception {
        when(taskService.getTaskCount()).thenReturn(5L);
        when(taskService.getTasksByStatus(Task.TaskStatus.TODO)).thenReturn(Arrays.asList(testTask));
        when(taskService.getTasksByStatus(Task.TaskStatus.IN_PROGRESS)).thenReturn(Arrays.asList(taskList.get(1)));
        when(taskService.getTasksByStatus(Task.TaskStatus.COMPLETED)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/tasks/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalTasks", is(5)))
                .andExpect(jsonPath("$.todoTasks", is(1)))
                .andExpect(jsonPath("$.inProgressTasks", is(1)))
                .andExpect(jsonPath("$.completedTasks", is(0)));

        verify(taskService, times(1)).getTaskCount();
        verify(taskService, times(1)).getTasksByStatus(Task.TaskStatus.TODO);
        verify(taskService, times(1)).getTasksByStatus(Task.TaskStatus.IN_PROGRESS);
        verify(taskService, times(1)).getTasksByStatus(Task.TaskStatus.COMPLETED);
    }

    @Test
    void handleTaskNotFound() throws Exception {
        when(taskService.getTaskById(anyLong()))
                .thenThrow(new TaskServiceImpl.TaskNotFoundException("Task not found with id: 999"));

        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", containsString("Task not found")))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.code", is(404)));
    }

    @Test
    void handleGenericException() throws Exception {
        when(taskService.getTaskById(anyLong()))
                .thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("Internal server error")))
                .andExpect(jsonPath("$.message", containsString("Something went wrong")))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.code", is(500)));
    }
}