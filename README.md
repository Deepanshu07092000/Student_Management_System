# Student Management System

A RESTful backend application built using **Java 17, Spring Boot 3.3.5, Spring Security, JWT, MySQL, and Redis** to manage students, departments, courses, and enrollment workflows.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [System Architecture](#system-architecture)
- [Project Structure](#project-structure)
- [Database Design](#database-design)
- [Authentication and Security](#authentication-and-security)
- [API Documentation](#api-documentation)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Examples](#api-examples)
- [Logging and Monitoring](#logging-and-monitoring)
- [Error Handling](#error-handling)
- [Future Enhancements](#future-enhancements)

---

## Overview

The Student Management System is a backend application designed to manage student records and academic information through RESTful APIs.

The application follows a layered architecture that separates controllers, services, repositories, entities, DTOs, security components, and configuration. It incorporates practical backend engineering concepts such as entity relationships, request validation, centralized exception handling, JWT-based authentication, Redis caching, file uploads, transaction management, and execution-time logging.

The project demonstrates how to build maintainable and extensible Spring Boot applications using commonly adopted backend development practices.

## Features

### Student Management
- Create, retrieve, update, and delete student records.
- Retrieve students using pagination and sorting.
- Search students by first name or last name, ignoring case.
- Upload or replace student profile images.
- Validate incoming student data.
- Transfer API data using request and response DTOs.

### Department Management
- Create, retrieve, update, and delete departments.
- Associate departments with students and courses.
- Retrieve department information through REST APIs.

### Course Management
- Create, retrieve, update, and delete courses.
- Associate courses with departments.
- Manage course information and enrollment capacity.

### Enrollment Workflow
- Submit enrollment requests for courses.
- Retrieve enrollment requests.
- Approve or reject enrollment requests.
- Update course enrollment information when requests are approved.
- Generate notifications as part of the enrollment workflow.
- Use transaction management to keep related database changes consistent.

### Security
- JWT-based authentication.
- Stateless authentication using Spring Security.
- BCrypt password hashing.
- Database-backed user authentication.
- Custom JWT authentication filter.
- Protected REST endpoints.
- Public login and Swagger documentation endpoints.

### Performance and Maintainability
- Redis caching for frequently accessed course and department data.
- Configurable cache expiration.
- Global exception handling.
- Bean Validation for incoming requests.
- Centralized application logging.
- Spring AOP-based execution-time logging.
- JPA auditing for tracking entity creation and modification.
- Swagger/OpenAPI documentation.
- Spring Boot Actuator endpoints for application monitoring.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 3.3.5 | Application framework |
| Spring Web | REST API development |
| Spring Security | Authentication and request security |
| JWT / JJWT 0.12.6 | Token generation and validation |
| Spring Data JPA | Database persistence |
| Hibernate | ORM and entity mapping |
| MySQL | Relational database |
| Redis | Caching |
| Spring Validation | Request validation |
| Spring AOP | Execution-time logging |
| Spring Boot Actuator | Health and application metrics |
| Swagger / OpenAPI | API documentation and testing |
| Maven | Dependency and build management |
| Lombok | Boilerplate code reduction |
| Spring Boot DevTools | Development productivity |

## System Architecture

The application follows a layered architecture.

```text
Client / Postman / Swagger UI
              |
              v
       REST Controllers
              |
              v
        Service Layer
     (Business Logic)
              |
              v
       Repository Layer
              |
              v
        Spring Data JPA
              |
              v
          MySQL DB

Supporting Components:
----------------------
Spring Security + JWT
Redis Cache
DTOs + Validation
Global Exception Handler
Spring AOP Logging
JPA Auditing
Spring Boot Actuator
File Storage Service
```

### Layer Responsibilities

- **Controller Layer:** Exposes REST endpoints and handles HTTP requests and responses.
- **Service Layer:** Implements business rules and coordinates application operations.
- **Repository Layer:** Provides database access using Spring Data JPA.
- **Entity Layer:** Represents persistent domain objects and their relationships.
- **DTO Layer:** Defines request and response structures independently of database entities.
- **Security Layer:** Authenticates users and validates JWTs.
- **Exception Layer:** Handles application exceptions and standardizes error responses.
- **Configuration Layer:** Configures security, caching, auditing, OpenAPI, and other application components.
- **Aspect Layer:** Logs service execution time using Spring AOP.

## Project Structure

```text
com.batch.sms
├── aspect
│   └── ExecutionTimeLoggingAspect.java
├── common
│   └── BaseEntity.java
├── config
│   ├── CacheConfig.java
│   ├── DataSeeder.java
│   ├── DefaultAuditorAware.java
│   ├── JpaAuditingConfig.java
│   ├── OpenApiConfig.java
│   ├── SecurityConfig.java
│   └── WebConfig.java
├── controller
│   ├── AuthController.java
│   ├── StudentController.java
│   ├── DepartmentController.java
│   ├── CourseController.java
│   └── EnrollmentRequestController.java
├── dto
│   ├── AuthResponse.java
│   ├── LoginRequest.java
│   ├── PageResponse.java
│   ├── StudentRequestDto.java
│   ├── StudentResponseDto.java
│   ├── DepartmentRequestDto.java
│   ├── DepartmentResponseDto.java
│   ├── CourseRequestDto.java
│   ├── CourseResponseDto.java
│   ├── EnrollmentRequestDto.java
│   └── EnrollmentResponseDto.java
├── entity
│   ├── Address.java
│   ├── Student.java
│   ├── Department.java
│   ├── Course.java
│   ├── EnrollmentRequest.java
│   ├── EnrollmentStatus.java
│   ├── Notification.java
│   └── User.java
├── exception
│   ├── ErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── DuplicateResourceException.java
│   ├── CourseFullException.java
│   ├── FileStorageException.java
│   └── InvalidRequestStateException.java
├── repository
│   ├── StudentRepository.java
│   ├── DepartmentRepository.java
│   ├── CourseRepository.java
│   ├── EnrollmentRequestRepository.java
│   ├── NotificationRepository.java
│   └── UserRepository.java
├── security
│   ├── CustomUserDetailsService.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
│   └── Role.java
├── service
│   ├── AuthService.java
│   ├── StudentService.java
│   ├── DepartmentService.java
│   ├── CourseService.java
│   ├── EnrollmentRequestService.java
│   ├── FileStorageService.java
│   └── impl
│       ├── StudentServiceImpl.java
│       ├── DepartmentServiceImpl.java
│       ├── CourseServiceImpl.java
│       ├── EnrollmentRequestServiceImpl.java
│       └── FileStorageServiceImpl.java
└── StudentManagementSystemApplication.java
```

## Database Design

The application uses MySQL with Spring Data JPA and Hibernate.

The primary domain entities include:

- **Student:** Stores student details, contact information, date of birth, profile image path, and address.
- **Department:** Represents an academic department and its associated students and courses.
- **Course:** Stores course details, department association, and enrollment information.
- **EnrollmentRequest:** Tracks course enrollment requests and their statuses.
- **Notification:** Stores notification records generated by the enrollment workflow.
- **User:** Stores authentication information and user role.

### Entity Relationships

- **Department–Student:** One department can be associated with multiple students.
- **Department–Course:** A department can contain multiple courses.
- **Student–Course:** Many-to-many relationship for student course enrollments.
- **Student–Address:** Address information is embedded within the student entity.
- **Enrollment Request:** Connects a student with a course and tracks the request lifecycle.

JPA annotations define entity relationships and persistence mappings.

## Authentication and Security

The application uses Spring Security with JSON Web Tokens (JWT) to secure REST APIs.

### Authentication Flow

1. The client sends a username and password to the login endpoint.
2. Spring Security authenticates the credentials against the database.
3. Password verification is performed using BCrypt.
4. On successful authentication, the application generates a signed JWT access token.
5. The client sends the token in the `Authorization` header for protected requests.
6. The JWT authentication filter validates the token and establishes the authenticated security context.
7. Requests without valid authentication are denied by Spring Security.

### Login Endpoint

```http
POST /api/auth/login
Content-Type: application/json
```

Example request:

```json
{
  "username": "admin",
  "password": "YOUR_PASSWORD"
}
```

The response contains an access token, token type, and token expiration duration.

### Authorization Header

```http
Authorization: Bearer YOUR_ACCESS_TOKEN
```

### Token Configuration

The configured access token lifetime is **15 minutes**. The application uses stateless authentication and disables HTTP Basic and form-based login.

**Security note:** The current security configuration requires authentication for protected endpoints but does not yet enforce separate ADMIN and USER restrictions on individual endpoints. The `Role` enum and user role are present in the project.

## API Documentation

Swagger UI provides an interactive interface for exploring and testing the APIs.

**Swagger UI:** `http://localhost:8080/swagger-ui.html`

**OpenAPI specification:** `http://localhost:8080/v3/api-docs`

### API Endpoint Summary

#### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/login` | Authenticate and obtain a JWT |

#### Students

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/students` | Create a student |
| GET | `/api/students` | List students with pagination and search |
| GET | `/api/students/{id}` | Retrieve a student |
| PUT | `/api/students/{id}` | Update a student |
| DELETE | `/api/students/{id}` | Delete a student |
| POST | `/api/students/{id}/image` | Upload or replace a student image |

#### Departments

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/departments` | Create a department |
| GET | `/api/departments` | List departments |
| GET | `/api/departments/{id}` | Retrieve a department |
| PUT | `/api/departments/{id}` | Update a department |
| DELETE | `/api/departments/{id}` | Delete a department |

#### Courses

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/courses` | Create a course |
| GET | `/api/courses` | List courses |
| GET | `/api/courses/{id}` | Retrieve a course |
| PUT | `/api/courses/{id}` | Update a course |
| DELETE | `/api/courses/{id}` | Delete a course |

#### Enrollment Requests

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/enrollment-requests` | Submit an enrollment request |
| GET | `/api/enrollment-requests` | List enrollment requests |
| GET | `/api/enrollment-requests/{id}` | Retrieve an enrollment request |
| POST | `/api/enrollment-requests/{id}/approve` | Approve a request |
| POST | `/api/enrollment-requests/{id}/reject` | Reject a request |

All endpoints other than login and the configured Swagger/OpenAPI paths require authentication.

## Getting Started

### Prerequisites

Install the following:

- Java Development Kit (JDK) 17
- Apache Maven 3.8 or later
- MySQL 8.x
- Redis Server
- Git
- IntelliJ IDEA or another Java IDE
- Postman (optional, for API testing)

### 1. Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd student-management-system
```

Replace the repository URL with your actual GitHub repository URL.

### 2. Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE sms_db;
```

### 3. Configure Application Properties

Open:

`src/main/resources/application.properties`

Configure the MySQL connection, Redis connection, and JWT secret for your local environment.

Example configuration:

```properties
spring.application.name=student-management-system
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/sms_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.type=redis
spring.cache.redis.time-to-live=600000

jwt.secret=${JWT_SECRET}
jwt.access-token-expiration=900000
```

Set `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET` as environment variables before starting the application.

Generate a sufficiently long, cryptographically secure Base64-encoded JWT secret appropriate for the configured HMAC signing algorithm. Keep it private and never commit it to GitHub.

The project uses additional properties for file uploads, logging, Swagger, and Actuator. Preserve those settings when updating your existing configuration.

### 4. Start Redis

Make sure your Redis server is running on the configured host and port.

Default configuration:

```text
Host: localhost
Port: 6379
```

Redis is used for caching, so verify connectivity before testing cache-enabled endpoints.

### 5. Build the Application

```bash
mvn clean install
```

### 6. Run the Application

```bash
mvn spring-boot:run
```

Alternatively, run `StudentManagementSystemApplication.java` from your IDE.

The application starts on:

`http://localhost:8080`

## API Examples

### Login and Obtain a Token

Send a `POST` request to:

```text
http://localhost:8080/api/auth/login
```

Provide a valid username and password in the JSON request body.

Copy the returned access token.

### Access a Protected Endpoint

Send a `GET` request to:

```text
http://localhost:8080/api/students?page=0&size=10
```

Add this header:

```http
Authorization: Bearer YOUR_ACCESS_TOKEN
```

### Pagination, Sorting, and Search

Example requests:

```http
GET /api/students?page=0&size=10
GET /api/students?page=0&size=10&sort=firstName,asc
GET /api/students?page=0&size=10&sort=lastName,desc
GET /api/students?page=0&size=10&search=rah
```

Pagination is zero-based. Sorting uses Java entity property names, such as `firstName`, rather than database column names such as `first_name`.

### Upload a Student Image

```http
POST /api/students/{id}/image
```

Use `multipart/form-data` with a field named `file`. The configured maximum file size is 5 MB.

Uploaded images are stored using the configured filesystem directory:

```text
uploads/student-images
```

## Logging and Monitoring

### Application Logging

The application uses Spring Boot logging configuration to record application activity and errors.

The configured logging levels are:

- Root logger: `INFO`
- Application package (`com.batch.sms`): `DEBUG`

Spring AOP is used to log service-method execution time and exceptions.

### Spring Boot Actuator

The project exposes selected Actuator endpoints for monitoring:

```text
http://localhost:8080/actuator/health
http://localhost:8080/actuator/info
http://localhost:8080/actuator/metrics
http://localhost:8080/actuator/loggers
```

Review the exposure and access configuration before deploying to production. In particular, do not expose detailed health information or logger-management endpoints publicly without appropriate access controls.

## Error Handling

The application uses a centralized `GlobalExceptionHandler` to handle application exceptions.

The exception layer includes handling for scenarios such as:

- Resource not found.
- Duplicate resources.
- Invalid request states.
- Full course enrollment capacity.
- File storage failures.
- Request validation errors.

The `ErrorResponse` class standardizes error information returned by the API.

Appropriate HTTP status codes help clients understand whether a request succeeded or failed.

## Future Enhancements

Potential next steps for improving the project include:

- Enforcing role-based authorization for ADMIN and USER.
- Adding refresh-token support and token revocation.
- Adding automated unit and integration tests for all modules.
- Introducing database migration management with Flyway or Liquibase.
- Adding Docker and Docker Compose for repeatable deployments.
- Implementing CI/CD pipelines.
- Adding production-grade metrics and centralized log aggregation.
- Improving file storage with cloud object storage.
- Adding API rate limiting and more granular security controls.

These are potential improvements and are not all implemented in the current version.

## Learning Outcomes

This project provides practical experience with:

- REST API design and HTTP status codes.
- Layered architecture and separation of concerns.
- Spring Security and JWT authentication.
- JPA entity relationships and transaction management.
- Pagination, sorting, and searching.
- Redis caching.
- File uploads and filesystem storage.
- DTOs, validation, and global exception handling.
- Spring AOP, auditing, and application monitoring.

## Author

**Deepanshu Kaushik**
---

*This project is intended for learning, demonstration, and portfolio purposes. Review configuration, security, database migrations, testing, and operational controls before using it in a production environment.*
