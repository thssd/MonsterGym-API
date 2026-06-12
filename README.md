# 🏋️ MonsterGym API

A RESTful API for gym management built with Java and Spring Boot. The system handles student and trainer registration with full CRUD operations, stateless JWT-based authentication, and role-based access control.

---

## Features

- JWT authentication with 2-hour token expiration
- Role-based access control (`ADMIN` and `USER`)
- Full CRUD for students (alunos) and trainers (treinadores)
- Soft delete — records are deactivated, not removed from the database
- Pagination and sorting on listing endpoints
- Automated database migrations with Flyway
- Centralized error handling with `@RestControllerAdvice`
- Input validation with Bean Validation

---

## Tech Stack

| Technology          | Version  |
|---------------------|----------|
| Java                | 17       |
| Spring Boot         | 4.0.6    |
| Spring Security     | —        |
| Spring Data JPA     | —        |
| PostgreSQL          | —        |
| Flyway              | —        |
| Auth0 java-jwt      | 4.5.2    |
| Lombok              | —        |
| Maven               | —        |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL running locally

### 1. Clone the repository

```bash
git clone https://github.com/thssd/monstergym-api.git
cd monstergym-api
```

### 2. Create the database

```sql
CREATE DATABASE academia;
```

### 3. Configure environment variables

The application reads credentials from environment variables. Set the following before running:

| Variable       | Description                                      |
|----------------|--------------------------------------------------|
| `DB_USERNAME`  | PostgreSQL username                              |
| `DB_PASSWORD`  | PostgreSQL password                              |
| `JWT_SECRET`   | Secret key for signing JWT tokens (min. 32 chars) |

Example (Linux/macOS):
```bash
export DB_USERNAME=postgres
export DB_PASSWORD=yourpassword
export JWT_SECRET=your_very_long_and_secure_secret_key
```

> ⚠️ If `JWT_SECRET` is not set, the app falls back to a default insecure value. Always set it in production.

### 4. Run the application

```bash
./mvnw spring-boot:run
```

Flyway will automatically run the migrations and create the required tables on startup.

The API will be available at `http://localhost:8080`.

---

## API Endpoints

### Authentication — `/auth`

| Method | Endpoint          | Auth required | Description          |
|--------|-------------------|---------------|----------------------|
| POST   | `/auth/registrar` | No            | Register a new user  |
| POST   | `/auth/login`     | No            | Authenticate and get a JWT token |

#### Register

```http
POST /auth/registrar
Content-Type: application/json

{
  "username": "john",
  "password": "secret123",
  "role": "USER"
}
```

Roles: `USER` or `ADMIN`

#### Login

```http
POST /auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "secret123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Use this token in the `Authorization` header for all protected endpoints:
```
Authorization: Bearer <token>
```

---

### Students — `/alunos`

All endpoints require authentication.

| Method | Endpoint      | Role required | Description            |
|--------|---------------|---------------|------------------------|
| POST   | `/alunos`     | USER          | Register a student     |
| GET    | `/alunos`     | USER          | List active students   |
| PUT    | `/alunos`     | USER          | Update student data    |
| DELETE | `/alunos/{id}`| USER          | Deactivate a student   |

#### Register a student

```http
POST /alunos
Authorization: Bearer <token>
Content-Type: application/json

{
  "nome": "Carlos Silva",
  "email": "carlos@email.com",
  "telefone": "11999998888",
  "cpf": "123.456.789-00"
}
```

#### List students (paginated)

```http
GET /alunos?page=0&size=10
Authorization: Bearer <token>
```

Results are sorted by CPF and only include active students.

#### Update a student

```http
PUT /alunos
Authorization: Bearer <token>
Content-Type: application/json

{
  "id": 1,
  "nome": "Carlos Souza",
  "telefone": "11988887777"
}
```

Only include the fields you want to change. `id` is required.

#### Deactivate a student

```http
DELETE /alunos/1
Authorization: Bearer <token>
```

Returns `204 No Content`. The student is not deleted from the database — their `ativo` field is set to `false`.

---

### Trainers — `/treinadores`

| Method | Endpoint           | Role required | Description            |
|--------|--------------------|---------------|------------------------|
| POST   | `/treinadores`     | **ADMIN**     | Register a trainer     |
| GET    | `/treinadores`     | USER          | List active trainers   |
| PUT    | `/treinadores`     | USER          | Update trainer data    |
| DELETE | `/treinadores/{id}`| USER          | Deactivate a trainer   |

#### Register a trainer (ADMIN only)

```http
POST /treinadores
Authorization: Bearer <admin-token>
Content-Type: application/json

{
  "nome": "Ana Lima",
  "cref": "123456-G/SP",
  "telefone": "11977776666",
  "especialidade": "HIPERTROFIA"
}
```

Available specialties: `HIPERTROFIA`, `EMAGRECIMENTO`, `FUNCIONAL`, `REABILITACAO`

---

## Project Structure

```
src/main/java/com/monstergym/api/
│
├── controller/         # REST controllers (request/response handling)
│   ├── AlunosController.java
│   ├── TreinadorController.java
│   └── AutenticacaoController.java
│
├── domain/             # Entities and DTOs
│   ├── alunos/
│   ├── treinadores/
│   └── user/
│
├── repository/         # Spring Data JPA repositories
│
├── service/            # Business logic
│   ├── AuthorizationService.java
│   └── TokenService.java
│
└── infra/
    ├── security/       # JWT filter and security configuration
    └── exceptions/     # Global error handler
```

---

## Authentication Flow

```
Client                          API
  │                              │
  │── POST /auth/login ─────────>│
  │                              │ validates credentials
  │<── { "token": "eyJ..." } ───│
  │                              │
  │── GET /alunos ──────────────>│
  │   Authorization: Bearer eyJ  │ validates JWT
  │                              │ checks roles
  │<── 200 OK [{ aluno... }] ───│
```

The token expires in **2 hours**. After that, the client must log in again.

---

## Database Schema

Migrations are managed by Flyway and run automatically on startup.

**usuarios**
| Column   | Type   | Notes              |
|----------|--------|--------------------|
| id       | BIGINT | Primary key        |
| username | TEXT   | Unique             |
| password | TEXT   | BCrypt hashed      |
| role     | TEXT   | `ADMIN` or `USER`  |

**aluno** (created by JPA/Hibernate)
| Column   | Type    | Notes              |
|----------|---------|--------------------|
| id       | BIGINT  | Primary key        |
| nome     | TEXT    |                    |
| email    | TEXT    |                    |
| telefone | TEXT    |                    |
| cpf      | TEXT    |                    |
| ativo    | BOOLEAN | Soft delete flag   |

**treinadores** (created by JPA/Hibernate)
| Column        | Type    | Notes                                              |
|---------------|---------|----------------------------------------------------|
| id            | BIGINT  | Primary key                                        |
| nome          | TEXT    |                                                    |
| cref          | TEXT    | Professional registration number                   |
| telefone      | TEXT    |                                                    |
| especialidade | TEXT    | `HIPERTROFIA`, `EMAGRECIMENTO`, `FUNCIONAL`, `REABILITACAO` |
| ativo         | BOOLEAN | Soft delete flag                                   |

---

## Author

**Thiago Shimizu Sodré dos Santos**
[LinkedIn](https://www.linkedin.com/in/thiagoshimizusodre) · [GitHub](https://github.com/thssd)
