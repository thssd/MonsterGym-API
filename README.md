# 🏋️ MonsterGym API

A RESTful API for gym management built with Java and Spring Boot. The system handles student and trainer registration with full CRUD operations, class scheduling with business rule validation, stateless JWT-based authentication, and role-based access control.

---

## Features

- JWT authentication with 2-hour token expiration
- Role-based access control (`ADMIN` and `USER`)
- Full CRUD for students (alunos) and trainers (treinadores)
- Class scheduling with automatic trainer assignment by specialty
- Business rule validation pipeline using the Strategy pattern
- Class cancellation with reason tracking and 24-hour advance notice rule
- Soft delete — records are deactivated, not removed from the database
- Pagination and sorting on listing endpoints
- Automated database migrations with Flyway
- Centralized error handling with `@RestControllerAdvice`
- Input validation with Bean Validation
- Interactive API documentation with Swagger UI (SpringDoc / OpenAPI 3)
- Automated tests using `@DataJpaTest` with an H2 in-memory database

---

## Tech Stack

| Technology          | Version  |
|---------------------|----------|
| Java                | 17       |
| Spring Boot         | 4.0.6    |
| Spring Security     | —        |
| Spring Data JPA     | —        |
| PostgreSQL          | —        |
| H2 (test)           | —        |
| Flyway              | —        |
| Auth0 java-jwt      | 4.5.2    |
| SpringDoc OpenAPI   | 3.0.3    |
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

| Variable       | Description                                       |
|----------------|---------------------------------------------------|
| `DB_USERNAME`  | PostgreSQL username                               |
| `DB_PASSWORD`  | PostgreSQL password                               |
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

## API Documentation (Swagger UI)

Interactive documentation is available at:

```
http://localhost:8080/swagger-ui.html
```

All protected endpoints require a Bearer JWT token. Use the **Authorize** button in the Swagger UI to set your token after logging in.

---

## API Endpoints

### Authentication — `/auth`

| Method | Endpoint          | Auth required | Description                      |
|--------|-------------------|---------------|----------------------------------|
| POST   | `/auth/registrar` | No            | Register a new user              |
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

| Method | Endpoint       | Role required | Description          |
|--------|----------------|---------------|----------------------|
| POST   | `/alunos`      | USER          | Register a student   |
| GET    | `/alunos`      | USER          | List active students |
| PUT    | `/alunos`      | USER          | Update student data  |
| DELETE | `/alunos/{id}` | USER          | Deactivate a student |

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

| Method | Endpoint              | Role required | Description            |
|--------|-----------------------|---------------|------------------------|
| POST   | `/treinadores`        | **ADMIN**     | Register a trainer     |
| GET    | `/treinadores`        | USER          | List active trainers   |
| PUT    | `/treinadores`        | USER          | Update trainer data    |
| DELETE | `/treinadores/{id}`   | USER          | Deactivate a trainer   |

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

### Classes — `/aulas`

All endpoints require authentication.

| Method | Endpoint | Role required | Description       |
|--------|----------|---------------|-------------------|
| POST   | `/aulas` | USER          | Schedule a class  |
| DELETE | `/aulas` | USER          | Cancel a class    |

#### Schedule a class

```http
POST /aulas
Authorization: Bearer <token>
Content-Type: application/json

{
  "idAluno": 1,
  "idTreinador": 2,
  "data": "2025-12-20T10:00:00",
  "especialidade": "HIPERTROFIA"
}
```

`idTreinador` is optional. If omitted, the API will automatically assign a random available trainer with the given `especialidade`.

Response:
```json
{
  "id": 10,
  "idTreinador": 2,
  "idAluno": 1,
  "data": "2025-12-20T10:00:00"
}
```

#### Cancel a class

```http
DELETE /aulas
Authorization: Bearer <token>
Content-Type: application/json

{
  "idConsulta": 10,
  "motivoCancelamento": "IMPREVISTO",
  "descricao": null
}
```

Available cancellation reasons: `PACIENTE_DESISTIU`, `MEDICO_CANCELOU`, `IMPREVISTO`, `FALTA_DE_TEMPO`, `OUTRO`

> When `motivoCancelamento` is `OUTRO`, the `descricao` field is required.

Returns `204 No Content`.

---

## Business Rules

### Scheduling validations

All rules below are enforced before a class is saved. Any violation returns `400 Bad Request` with a descriptive message.

| Rule | Description |
|------|-------------|
| Active student | The student must have an active account |
| Active trainer | The trainer must have an active account (if specified) |
| Minimum advance notice | Classes must be scheduled at least **1 hour** in advance |
| One class per day per student | A student cannot have more than one class on the same day |
| No trainer double-booking | A trainer cannot have two classes at the same time |
| Operating hours | Classes can only be scheduled between **07:00** and **22:00** |

### Cancellation validations

| Rule | Description |
|------|-------------|
| Minimum advance notice | Classes can only be cancelled at least **24 hours** before the scheduled time |
| Reason required | A cancellation reason (`motivoCancelamento`) is always required |
| Description required for "other" | When the reason is `OUTRO`, a `descricao` must be provided |

---

## Project Structure

```
src/main/java/com/monstergym/api/
│
├── controller/              # REST controllers (request/response handling)
│   ├── AlunosController.java
│   ├── TreinadorController.java
│   ├── AulaController.java
│   └── AutenticacaoController.java
│
├── domain/                  # Entities and DTOs
│   ├── alunos/
│   ├── treinadores/
│   ├── aulas/
│   │   ├── validacoes/      # Scheduling business rule validators
│   │   └── cancelamentos/   # Cancellation business rule validators
│   └── user/
│
├── repository/              # Spring Data JPA repositories
│
├── service/                 # Business logic
│   ├── AulaService.java
│   ├── AuthorizationService.java
│   └── TokenService.java
│
└── infra/
    ├── security/            # JWT filter and security configuration
    ├── exceptions/          # Global error handler
    └── springdoc/           # OpenAPI / Swagger configuration
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
  │── POST /aulas ──────────────>│
  │   Authorization: Bearer eyJ  │ validates JWT
  │                              │ runs business rule validators
  │                              │ assigns trainer if needed
  │<── 200 OK { aula... } ──────│
```

The token expires in **2 hours**. After that, the client must log in again.

---

## Testing

The project includes automated repository tests using `@DataJpaTest` with an H2 in-memory database (activated via the `test` Spring profile).

```bash
./mvnw test
```

Current test coverage:

| Test | Description |
|------|-------------|
| `escolherTreinadorAleatorio_Caso1` | Returns `null` when no trainer is available at the requested date/time |
| `escolherTreinadorAleatorio_Caso2` | Returns the correct trainer when they are available at the requested date/time |

---

## Database Schema

Migrations are managed by Flyway and run automatically on startup.

**usuarios**
| Column   | Type   | Notes             |
|----------|--------|-------------------|
| id       | BIGINT | Primary key       |
| username | TEXT   | Unique            |
| password | TEXT   | BCrypt hashed     |
| role     | TEXT   | `ADMIN` or `USER` |

**aluno**
| Column   | Type    | Notes            |
|----------|---------|------------------|
| id       | BIGINT  | Primary key      |
| nome     | TEXT    |                  |
| email    | TEXT    |                  |
| telefone | TEXT    |                  |
| cpf      | TEXT    |                  |
| ativo    | BOOLEAN | Soft delete flag |

**treinadores**
| Column        | Type    | Notes                                               |
|---------------|---------|-----------------------------------------------------|
| id            | BIGINT  | Primary key                                         |
| nome          | TEXT    |                                                     |
| cref          | TEXT    | Professional registration number                    |
| telefone      | TEXT    |                                                     |
| especialidade | TEXT    | `HIPERTROFIA`, `EMAGRECIMENTO`, `FUNCIONAL`, `REABILITACAO` |
| ativo         | BOOLEAN | Soft delete flag                                    |

**consultas** (aulas)
| Column              | Type      | Notes                                                                  |
|---------------------|-----------|------------------------------------------------------------------------|
| id                  | BIGINT    | Primary key                                                            |
| aluno_id            | BIGINT    | FK → aluno                                                             |
| treinador_id        | BIGINT    | FK → treinadores                                                       |
| data                | TIMESTAMP |                                                                        |
| especialidade       | TEXT      | Specialty at the time of booking                                       |
| motivoCancelamento  | TEXT      | `PACIENTE_DESISTIU`, `MEDICO_CANCELOU`, `IMPREVISTO`, `FALTA_DE_TEMPO`, `OUTRO` |
| descricao           | TEXT      | Required when `motivoCancelamento = OUTRO`                             |

---

## Author

**Thiago Shimizu Sodré dos Santos**  
[LinkedIn](https://www.linkedin.com/in/thiagoshimizusodre) · [GitHub](https://github.com/thssd)
