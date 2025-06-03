package org.example.buntu.taskmanagmentsystemspringbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagementSystemSpringBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementSystemSpringBookApplication.class, args);
        System.out.println("🚀 Task Management API is running on http://localhost:8080/api");
    }

}
