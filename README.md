# Student Management System

A RESTful backend application built using **Java 17, Spring Boot 3.3.5, Spring Security, JWT, Google OAuth2, MySQL, and Redis** to manage students, departments, courses, and enrollment workflows.

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
- [API Examples](#api-examples)
- [Logging and Monitoring](#logging-and-monitoring)
- [Error Handling](#error-handling)
- [Future Enhancements](#future-enhancements)

---

## Overview

The Student Management System is a backend application designed to manage student records and academic information through RESTful APIs.

The application follows a layered architecture that separates controllers, services, repositories, entities, DTOs, security components, and configuration.

It demonstrates practical backend engineering concepts, including entity relationships, request validation, centralized exception handling, JWT authentication, Google OAuth2 login, role-based authorization, Redis caching, file uploads, transaction management, and execution-time logging.

## Features

### Student Management
- Create, retrieve, update, and delete student records.
- Retrieve students using pagination and sorting.
- Search students by first name or last name, ignoring case.
- Upload or replace student profile images.
- Validate incoming student data.
- Use request and response DTOs.

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

### Authentication and Security
- JWT access-token authentication using JJWT.
- Google OAuth2 login using Spring Security OAuth2 Client.
- Generate an application-issued JWT after successful Google login.
- Database-backed user authentication.
- BCrypt password hashing.
- Custom JWT authentication filter and token validation.
- Role-based authorization using `USER` and `ADMIN` roles.
- Restrict selected student-management operations to administrators.
- Secure protected APIs using Bearer tokens.
- Keep login and configured Swagger/OpenAPI endpoints publicly accessible.

### Performance and Maintainability
- Redis caching for frequently accessed course and department data.
- Configurable cache expiration.
- Global exception handling.
- Bean Validation for incoming requests.
- Centralized application logging.
- Spring AOP-based execution-time logging.
- JPA auditing.
- Swagger/OpenAPI documentation.
- Spring Boot Actuator endpoints for monitoring.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 3.3.5 | Application framework |
| Spring Web | REST API development |
| Spring Security | Authentication and authorization |
| JWT / JJWT | Token generation and validation |
| Spring Security OAuth2 Client | Google login |
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
       Business Logic
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
Google OAuth2 Login
Role-Based Authorization
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
- **Security Layer:** Authenticates users, validates JWTs, and applies authorization rules.
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

*Note: The structure above represents the main packages and classes. OAuth2-specific classes may be present in your current implementation even if they are not listed individually here.*

## Database Design

The application uses MySQL with Spring Data JPA and Hibernate.

### Main Entities

- **Student:** Stores student details, contact information, date of birth, profile image path, and address.
- **Department:** Represents an academic department and its associated students and courses.
- **Course:** Stores course details, department association, and enrollment information.
- **EnrollmentRequest:** Tracks course enrollment requests and their statuses.
- **Notification:** Stores notification records generated by the enrollment workflow.
- **User:** Stores authentication information and the user's role.

### Entity Relationships

- **Department–Student:** One department can be associated with multiple students.
- **Department–Course:** One department can contain multiple courses.
- **Student–Course:** Many-to-many relationship for student course enrollments.
- **Student–Address:** Address information is associated with the student.
- **Enrollment Request:** Connects a student with a course and tracks the request lifecycle.

JPA annotations define the entity relationships and persistence mappings.

## Authentication and Security

The application uses Spring Security, JWT, Google OAuth2, and role-based authorization to secure REST APIs.

### 1. Username and Password Login

The local login endpoint is:

```http
POST /api/auth/login
Content-Type: application/json
```

Example request:

```json
{
  "username": "YOUR_USERNAME",
  "password": "YOUR_PASSWORD"
}
```

Authentication flow:

1. The client submits the username and password.
2. Spring Security authenticates the credentials against the database.
3. BCrypt verifies the password.
4. The application generates a signed JWT access token.
5. The client uses the token to access protected APIs.
6. The JWT filter validates the token and loads the user's authorities.
7. Spring Security checks the user's roles before allowing access.

The response includes the access token, token type, and token expiration duration.

### 2. Google OAuth2 Login

Start Google login using:

```text
http://localhost:8080/oauth2/authorization/google
```

Google OAuth2 flow:

1. The user starts the Google login process.
2. Spring Security redirects the user to Google.
3. The user authenticates with Google.
4. Google redirects to the application's OAuth2 callback.
5. The application identifies the user using the verified email information.
6. The application finds the existing local user or creates a new user with the `USER` role.
7. The application generates its own JWT access token.
8. The client uses the JWT as a Bearer token for protected API requests.

The local redirect URI is:

```text
http://localhost:8080/login/oauth2/code/google
```

Configure this URI in Google Cloud Console for your OAuth client. For a deployed application, configure the corresponding deployed callback URI.

### 3. JWT Authorization Header

Send the JWT with protected API requests:

```http
Authorization: Bearer YOUR_ACCESS_TOKEN
```

The configured access-token lifetime is **15 minutes**.

### 4. Role-Based Authorization

The application supports the following roles:

| Role | Description |
|---|---|
| `USER` | Can access endpoints permitted to normal authenticated users |
| `ADMIN` | Can access administrator-restricted endpoints |

Selected student-management operations, including listing students and updating or deleting student records, are restricted to `ADMIN` according to the security configuration.

A `401 Unauthorized` response generally indicates missing or invalid authentication. A `403 Forbidden` response indicates that the request does not have the required permissions.

### 5. Security Configuration

Set the following environment variables in IntelliJ IDEA or your deployment environment:

```text
GOOGLE_CLIENT_ID
GOOGLE_CLIENT_SECRET
JWT_SECRET
DB_USERNAME
DB_PASSWORD
```

Security best practices:

- Never commit credentials or JWT signing secrets to GitHub.
- Use environment variables for sensitive configuration.
- Store passwords using BCrypt rather than plain text.
- Validate JWT signatures and expiration.
- Use HTTPS in deployed environments.
- Assign roles according to the application's authorization requirements.

## API Documentation

Swagger UI provides an interactive interface for exploring and testing the APIs.

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI specification:** `http://localhost:8080/v3/api-docs`

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/login` | Authenticate using username and password |
| GET | `/oauth2/authorization/google` | Start Google OAuth2 login |
| GET | `/login/oauth2/code/google` | OAuth2 callback handled by Spring Security |

### Students

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/students` | Create a student |
| GET | `/api/students` | List students with pagination and search |
| GET | `/api/students/{id}` | Retrieve a student |
| PUT | `/api/students/{id}` | Update a student |
| DELETE | `/api/students/{id}` | Delete a student |
| POST | `/api/students/{id}/image` | Upload or replace a student image |

### Departments

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/departments` | Create a department |
| GET | `/api/departments` | List departments |
| GET | `/api/departments/{id}` | Retrieve a department |
| PUT | `/api/departments/{id}` | Update a department |
| DELETE | `/api/departments/{id}` | Delete a department |

### Courses

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/courses` | Create a course |
| GET | `/api/courses` | List courses |
| GET | `/api/courses/{id}` | Retrieve a course |
| PUT | `/api/courses/{id}` | Update a course |
| DELETE | `/api/courses/{id}` | Delete a course |

### Enrollment Requests

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/enrollment-requests` | Submit an enrollment request |
| GET | `/api/enrollment-requests` | List enrollment requests |
| GET | `/api/enrollment-requests/{id}` | Retrieve an enrollment request |
| POST | `/api/enrollment-requests/{id}/approve` | Approve a request |
| POST | `/api/enrollment-requests/{id}/reject` | Reject a request |

All protected API endpoints require a valid JWT. Login, configured Google OAuth2 routes, and configured Swagger/OpenAPI paths are accessible without an existing JWT. Individual endpoints may have additional role restrictions.

Check the actual controller mappings and `SecurityConfig` when adding or changing endpoints.

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
git clone https://github.com/Deepanshu07092000/Student_Management_System.git
cd Student_Management_System
```

### 2. Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE sms_db;
```

### 3. Configure Application Properties

Open:

```text
src/main/resources/application.properties
```

Configure the MySQL connection, Redis connection, JWT secret, and Google OAuth2 credentials.

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

spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
spring.security.oauth2.client.registration.google.scope=openid,profile,email
```

This is an example configuration. Preserve any additional properties already used by your project for file uploads, logging, Swagger, and Actuator.

Set `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET` before starting the application. For Google login, also set `GOOGLE_CLIENT_ID` and `GOOGLE_CLIENT_SECRET`.

Generate a sufficiently long, cryptographically secure Base64-encoded JWT secret compatible with the HMAC signing algorithm used by your JWT implementation. Never publish it in the repository.

### 4. Start Redis

Ensure Redis is running on the configured host and port.

Default local configuration:

```text
Host: localhost
Port: 6379
```

### 5. Build the Application

Run this command from the project root:

```bash
mvn clean install
```

### 6. Run the Application

```bash
mvn spring-boot:run
```

Alternatively, run `StudentManagementSystemApplication.java` from IntelliJ IDEA.

The application starts at:

```text
http://localhost:8080
```

## API Examples

### Login and Obtain a JWT

Send a POST request to:

```text
http://localhost:8080/api/auth/login
```

Example body:

```json
{
  "username": "YOUR_USERNAME",
  "password": "YOUR_PASSWORD"
}
```

Copy the `accessToken` from the response.

### Access a Protected Endpoint

Send a GET request to:

```text
http://localhost:8080/api/students?page=0&size=10
```

Add the following header:

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

The default upload directory documented for the project is:

```text
uploads/student-images
```

Check the file-storage configuration if your local directory differs.

## Logging and Monitoring

### Application Logging

The application uses Spring Boot logging configuration to record application activity and errors.

The configured logging levels are:

- Root logger: `INFO`
- Application package (`com.batch.sms`): `DEBUG`

Spring AOP is used to log service-method execution time and exceptions.

### Spring Boot Actuator

The project exposes selected Actuator endpoints for monitoring, subject to the application's configuration:

```text
http://localhost:8080/actuator/health
http://localhost:8080/actuator/info
http://localhost:8080/actuator/metrics
http://localhost:8080/actuator/loggers
```

Before deploying to production, review endpoint exposure and access controls. Do not expose detailed health information or logger-management endpoints publicly without appropriate protection.

## Error Handling

The application uses a centralized `GlobalExceptionHandler` to handle application exceptions.

The exception layer includes handling for scenarios such as:

- Resource not found.
- Duplicate resources.
- Invalid request states.
- Full course enrollment capacity.
- File storage failures.
- Request validation errors.

The `ErrorResponse` class standardizes error information returned by the API. Appropriate HTTP status codes help clients understand whether a request succeeded or failed.

## Future Enhancements

Potential improvements include:

- Adding refresh-token support and token revocation.
- Expanding automated unit and integration tests.
- Introducing database migration management with Flyway or Liquibase.
- Adding Docker and Docker Compose for repeatable deployments.
- Implementing CI/CD pipelines.
- Adding production-grade metrics and centralized log aggregation.
- Improving file storage with cloud object storage.
- Adding API rate limiting and further security controls.

These enhancements are suggestions and may not be implemented in the current version.

## Learning Outcomes

This project provides practical experience with:

- REST API design and HTTP status codes.
- Layered architecture and separation of concerns.
- Spring Security, JWT authentication, Google OAuth2 login, and role-based authorization.
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
