# 📋 Task Management REST API

A comprehensive Task Management REST API built with **Spring Boot**, demonstrating modern Java web development practices including Inversion of Control (IoC), Dependency Injection (DI), and containerization with Docker.

## 🏢 Project Context

**Cloud Nova Inc.—** Backend Systems Modernization  
This project serves as a foundation for transitioning backend systems to Spring Boot and containerized microservices architecture.

---

## 🎯 Project Objectives

By completing this project, you will:

- ✅ Understand Spring Framework architecture and core concepts
- ✅ Implement Dependency Injection using Spring IoC container
- ✅ Build and run Spring Boot applications from scratch
- ✅ Create RESTful APIs using Spring MVC
- ✅ Test APIs using tools like Postman or curl
- ✅ Package and run applications inside Docker containers

---

## 🛠️ Technology Stack

| Technology          | Version | Purpose               |
|---------------------|---------|-----------------------|
| **Java**            | 21      | Programming Language  |
| **Spring Boot**     | 3.2.5   | Application Framework |
| **Spring MVC**      | 6.x     | Web Framework         |
| **Maven**           | 3.9+    | Build Tool            |
| **Docker**          | Latest  | Containerization      |
| **Bean Validation** | 3.x     | Input Validation      |

---

## 📁 Project Structure

```
task-management-api/
├── src/
│   └── main/
│       ├── java/com/buntu/taskmanagementsystemspringboot/
│       │   ├── TaskManagementSystemSpringbootApplication.java     # Main Application
│       │   ├── controller/
│       │   │   └── TaskController.java            # REST Controllers
│       │   ├── service/
│       │   │   ├── TaskService.java               # Service Interface
│       │   │   └── TaskServiceImpl.java           # Business Logic
│       │   ├── repository/
│       │   │   ├── TaskRepository.java            # Data Access Interface
│       │   │   └── InMemoryTaskRepository.java    # In-Memory Implementation
│       │   └── model/
│       │       └── Task.java                      # Domain Model
│       └── resources/
│           └── application.properties              # Configuration
├── pom.xml                                         # Maven Dependencies
├── Dockerfile                                      # Container Configuration
└── README.md                                       # This file
```

---

## 🚀 Quick Start

### Prerequisites
- **Java 21+** installed
- **Maven 3.9+** installed
- **Docker** installed (optional)

### Method 1: Run Locally
```bash
# Clone the repository
git clone https://github.com/WilliamBuntu/TSM-Docker.git
cd task-management-api

# Build and run
./mvnw spring-boot:run

# Or build JAR and run
./mvnw clean package
java -jar target/task-management-api-1.0.0.jar
```

### Method 2: Run with Docker
```bash
# Build Docker image
docker build -t task-management-api .

# Run container
docker run -p 8080:8080 task-management-api

# Run in background
docker run -d -p 8080:8080 --name task-api task-management-api
```

### ✅ Verify Installation
Once started, visit: `http://localhost:8080/api/tasks`

---

## 📡 API Endpoints

| Method   | Endpoint                         | Description          | Request Body |
|----------|----------------------------------|----------------------|--------------|
| `GET`    | `/api/tasks`                     | Get all tasks        | -            |
| `GET`    | `/api/tasks/{id}`                | Get task by ID       | -            |
| `POST`   | `/api/tasks`                     | Create new task      | Task JSON    |
| `PUT`    | `/api/tasks/{id}`                | Update existing task | Task JSON    |
| `DELETE` | `/api/tasks/{id}`                | Delete task          | -            |
| `GET`    | `/api/tasks/status/{status}`     | Filter by status     | -            |
| `GET`    | `/api/tasks/priority/{priority}` | Filter by priority   | -            |
| `GET`    | `/api/tasks/stats`               | Get task statistics  | -            |

### 📝 Task Model
```json
{
  "id": 1,
  "title": "Task Title",
  "description": "Task Description",
  "status": "TODO",
  "priority": "HIGH",
  "createdAt": "2025-06-04T10:30:00",
  "updatedAt": "2025-06-04T10:30:00",
  "dueDate": "2025-06-10T17:00:00"
}
```

### 📊 Status Values
- `TODO` - Task isn't started
- `IN_PROGRESS` - Task in progress
- `COMPLETED` - Task completed
- `CANCELLED` - Task canceled

### 🎯 Priority Values
- `LOW` - Low priority
- `MEDIUM` - Medium priority
- `HIGH` - High priority
- `URGENT` - Urgent priority

---

## 🧪 API Testing Examples

### Using cURL

#### Get All Tasks
```bash
curl -X GET http://localhost:8080/api/tasks
```

#### Create a New Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Spring Boot",
    "description": "Complete Spring Boot tutorial",
    "status": "TODO",
    "priority": "HIGH"
  }'
```

#### Update a Task
```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Task Title",
    "description": "Updated description",
    "status": "IN_PROGRESS",
    "priority": "MEDIUM"
  }'
```

#### Delete a Task
```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

#### Get Tasks by Status
```bash
curl -X GET http://localhost:8080/api/tasks/status/TODO
```

#### Get Task Statistics
```bash
curl -X GET http://localhost:8080/api/tasks/stats
```

### Using Postman
1. Import the collection using the API endpoints above
2. Set base URL to `http://localhost:8080`
3. Add `Content-Type: application/json` header for POST/PUT requests

---

## 🏗️ Architecture & Design Patterns

### 📐 Layered Architecture
```
┌─────────────────────┐
│   Controller Layer  │  ← REST endpoints, HTTP handling
├─────────────────────┤
│   Service Layer     │  ← Business logic, validation
├─────────────────────┤
│   Repository Layer  │  ← Data access, persistence
├─────────────────────┤
│   Model Layer       │  ← Domain entities, DTOs
└─────────────────────┘
```

### 🔧 Spring Framework Concepts Demonstrated

#### 1. **Inversion of Control (IoC)**
- Spring container manages object lifecycle
- Automatic bean creation and configuration

#### 2. **Dependency Injection (DI)**
```java
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository; // Constructor injection
    }
}
```

#### 3. **Component Scanning**
- `@RestController` - Web layer components
- `@Service` - Business logic components
- `@Repository` - Data access components

#### 4. **Spring MVC Pattern**
- Clean separation of concerns
- Request mapping and response handling
- Exception handling with `@ExceptionHandler`

---

## 🐳 Docker Configuration

### Dockerfile Features
- **Multi-layer build** for optimization
- **JDK 21** base image
- **Maven dependency caching** for faster builds
- **Proper working directory** setup

### Docker Commands
```bash
# Build image
docker build -t task-management-api .

# Run container
docker run -p 8080:8080 task-management-api

# Run in background
docker run -d -p 8080:8080 --name task-api task-management-api

# View logs
docker logs task-api

# Stop container
docker stop task-api

# Remove container
docker rm task-api
```

---

## 🔧 Configuration

### Application Properties
```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Application Configuration
spring.application.name=task-management-api

# Logging Configuration
logging.level.com.taskmanagement=DEBUG
```

### Maven Dependencies
- **spring-boot-starter-web** - Web and REST functionality
- **spring-boot-starter-validation** - Bean validation
- **spring-boot-starter-test** - Testing framework

---

## 🧪 Testing

### Manual Testing
1. **Start the application**
2. **Use curl or Postman** with provided examples
3. **Verify responses** match expected format

### Sample Data
The application starts with 3 preloaded tasks:
- Setup Development Environment (COMPLETED, HIGH)
- Learn Spring Boot (IN_PROGRESS, MEDIUM)
- Build REST API (TODO, HIGH)

---

## 📚 Learning Outcomes

After completing this project, you'll understand:

### Spring Framework Concepts
- **IoC Container** - How Spring manages objects
- **Dependency Injection** - Constructor vs field injection
- **Component Scanning** - Automatic bean discovery
- **Configuration Management** - Properties and profiles

### REST API Development
- **HTTP Methods** - GET, POST, PUT, DELETE
- **Status Codes** - 200, 201, 404, 500
- **Request/Response** - JSON serialization
- **Exception Handling** - Global error handling

### Modern Java Development
- **Annotation-based Configuration**
- **Bean Validation** - Input validation
- **Layered Architecture** - Separation of concerns
- **SOLID Principles** - Clean code practices

---

## 🚀 Future Enhancements

This project can be extended with:

### Database Integration
- **JPA/Hibernate** for database persistence
- **H2/PostgresSQL** database support
- **Data validation** and constraints

### Security Features
- **Spring Security** authentication
- **JWT tokens** for API access
- **Role-based authorization**

### Advanced Features
- **Pagination** for large datasets
- **Search functionality** with filters
- **File upload** for task attachments
- **Email notifications** for due dates

### Microservices
- **Service discovery** with Eureka
- **API Gateway** with Spring Cloud Gateway
- **Message queues** with RabbitMQ
- **Monitoring** with Actuator

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📞 Support

For questions or issues:
- 📧 **Email**: williambuntu1@gmail.com
- 📖 **Documentation**: [Spring Boot Reference](https://spring.io/projects/spring-boot)
- 🐛 **Issues**: Create an issue in this repository

---

## 📄 License

This project is licensed under the buntu License.

---

## 🙏 Acknowledgments

- **Spring Framework Team** - For the excellent framework
- **Cloud Nova Inc.** - For the learning opportunity
- **Docker Community** - For containerization tools

---

**Happy Coding! 🎉**

*Built with ❤️ using Spring Boot*