# Users API

A simple Spring Boot REST API for user management using JDBC with H2 in-memory database.

---

## Tech Stack

- Java 21
- Spring Boot 3.3.4
- Spring JDBC (JdbcTemplate)
- H2 Database (in-memory)
- Bean Validation (Jakarta)
- Lombok
- Maven

---

## How to Run

```bash
mvn spring-boot:run
```

The application starts on **http://localhost:8080**

H2 Console: **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:productdb`
- Username: `sa`
- Password: *(empty)*

---

## Package Structure

```
src/main/java/com/example/productspring/
├── ProductSpringApplication.java
├── controller/
│   └── UserController.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── UserNotFoundException.java
├── model/
│   └── User.java
├── repository/
│   └── UserRepository.java
└── service/
    └── UserService.java

src/main/resources/
├── application.yml
├── schema.sql
└── data.sql
```

---

## API List

| Method | Path | Description | Status |
|--------|------|-------------|--------|
| GET | /users | Get all users | 200 |
| GET | /users/{userId} | Get user by ID | 200 / 404 |
| POST | /users | Create a user | 201 |
| PUT | /users/{userId} | Update a user | 200 / 404 |
| DELETE | /users/{userId} | Delete a user | 204 / 404 |

---

## Example Requests & Responses

### GET /users
```bash
curl -s http://localhost:8080/users | jq
```
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "username": "johndoe",
    "email": "john@example.com",
    "phone": "555-1234",
    "website": "https://johndoe.com"
  }
]
```

### GET /users/{userId}
```bash
curl -s http://localhost:8080/users/1 | jq
```
```json
{
  "id": 1,
  "name": "John Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "phone": "555-1234",
  "website": "https://johndoe.com"
}
```

### POST /users
```bash
curl -s -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Martin",
    "username": "bobmartin",
    "email": "bob@example.com",
    "phone": "555-0000",
    "website": "https://bob.com"
  }' | jq
```
```json
{
  "id": 4,
  "name": "Bob Martin",
  "username": "bobmartin",
  "email": "bob@example.com",
  "phone": "555-0000",
  "website": "https://bob.com"
}
```

### PUT /users/{userId}
```bash
curl -s -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "username": "johndoe",
    "email": "john.updated@example.com"
  }' | jq
```

### DELETE /users/{userId}
```bash
curl -s -X DELETE http://localhost:8080/users/1 -w "\nHTTP %{http_code}\n"
```

---

## Error Responses

### 404 Not Found
```json
{
  "status": 404,
  "message": "User not found with id: 99"
}
```

### 400 Validation Error
```bash
curl -s -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{}' | jq
```
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": [
    "name must not be blank",
    "username must not be blank",
    "email must not be blank"
  ]
}
```

### 500 Internal Server Error
```json
{
  "status": 500,
  "message": "Internal Server Error"
}
```

---

## Sample Data

Seeded automatically on startup via `data.sql`:

| ID | Name | Username | Email |
|----|------|----------|-------|
| 1 | John Doe | johndoe | john@example.com |
| 2 | Jane Smith | janesmith | jane@example.com |
| 3 | Alice Johnson | alicejohnson | alice@example.com |

---

## Validation Rules

| Field | Rule |
|-------|------|
| name | Required, not blank |
| username | Required, not blank |
| email | Required, not blank |
| phone | Optional |
| website | Optional |
