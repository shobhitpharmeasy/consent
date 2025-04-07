# 🛡️ Consent Management System

A secure and scalable **Spring Boot** application for managing user and service-level consent within an organization. Built with modern practices like JWT-based authentication, DTO-layer abstraction, MapStruct mapping, and layered architecture.

---

## 🚀 Features

- 🔐 **JWT Authentication** – Secure login and session handling via JSON Web Tokens.
- 👥 **Employee Management** – CRUD operations for employees with hashed password support.
- 🛠️ **Service Access Control** – Manage access to various services with ownership logic.
- 🧩 **DTO-Entity Mapping** – Clean separation using MapStruct and helper mappers.
- 📜 **OpenAPI Docs** – Auto-generated Swagger UI for REST APIs.
- 🗃️ **PostgreSQL + JPA** – Robust data access layer with Spring Data JPA.
- ⚙️ **Security Configurable** – Centralized Spring Security configuration.
- 🧪 **Testing Support** – Pre-wired for unit and integration tests using Spring Test and RestDocs.

---

## 🏗️ Project Structure

```bash
.
├── src/main/java/com/pharmeasy/consent
│   ├── controller          # REST Controllers (Login, Employee, Service)
│   ├── dto                 # Request/Response DTOs
│   ├── entity              # JPA Entities (Employee, Service)
│   ├── mapper              # MapStruct interfaces and helpers
│   ├── repository          # JPA Repositories
│   ├── service             # Service interfaces & implementations
│   ├── config              # Spring Security & App configs
│   └── utils               # Utility classes (JWT, hashing, constants)
└── src/test               # Unit & Integration tests
```

---

## 📦 Tech Stack

| Layer      | Technology                     |
|------------|--------------------------------|
| Backend    | Java 21, Spring Boot 3.5       |
| Build Tool | Maven                          |
| Database   | PostgreSQL                     |
| ORM        | Spring Data JPA                |
| Security   | Spring Security + JWT          |
| Docs       | Springdoc OpenAPI (Swagger UI) |
| Mapping    | MapStruct + Lombok             |
| Testing    | JUnit, Spring Test, RestDocs   |

---

## ⚙️ Getting Started

### 🧰 Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL (or update `application.yml` for other DBs)

### ▶️ Running the App

```bash
# Build the project
./mvnw clean install

# Run the Spring Boot app
./mvnw spring-boot:run
```

### 🔑 API Documentation

Once the app is running, access the Swagger UI at:  
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🔐 Authentication

This app uses **JWT-based login**. To authenticate:

1. **Login** via `POST /login` with JSON:
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```
2. **Receive JWT** in the response.
3. **Use JWT** in the `Authorization` header for protected endpoints:
   ```
   Authorization: Bearer <token>
   ```

---

## 📄 Sample `.env` or `application.yml` Snippet

```yaml
spring:
    datasource:
        url:      jdbc:postgresql://localhost:5432/consent_db
        username: postgres
        password: yourpassword
    jpa:
        hibernate:
            ddl-auto: update
        show-sql: true
jwt:
    secret:       your_jwt_secret_key
    expirationMs: 3600000
```

---

## ✅ Tests

```bash
./mvnw test
```

Includes:

- Unit tests for service layers
- Security tests with Spring Security test utilities
- REST API contract tests with Spring RestDocs

---

## 🧑‍💻 Developer Info

**Author:** [Shobhit Aggarwal](https://github.com/shobhitpharmeasy)  
**License:** MIT  
**Repository:** [GitHub - pharmeasy/consent](https://github.com/shobhitpharmeasy/consent)

---

## 🧭 Roadmap Ideas (Optional)

- [ ] Multi-role access control (Admin/User/ServiceOwner)
- [ ] Consent history tracking with audit logs
- [ ] Integration with external service discovery
- [ ] Admin dashboard (React frontend)
