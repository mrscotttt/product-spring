# Product Management API

A production-grade Spring Boot REST API for product management, implemented as a bank coding interview project.

---

## Tech Stack

- Java 21
- Spring Boot 3.3.4
- Spring Data JPA + H2 (in-memory)
- Bean Validation (Jakarta)
- Lombok
- Maven

---

## How to Run

```bash
./mvnw spring-boot:run
```

The application starts on **http://localhost:8080**.

H2 Console is available at **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:productdb`
- Username: `sa`
- Password: *(empty)*

---

## Package Structure

```
src/main/java/com/example/productspring/
├── ProductSpringApplication.java
├── config/
│   └── JpaAuditingConfig.java
├── controller/
│   └── ProductController.java
├── dto/
│   ├── ProductRequest.java
│   ├── ProductResponse.java
│   ├── UpdateStatusRequest.java
│   └── ProductStatisticsResponse.java
├── entity/
│   ├── Product.java
│   └── ProductStatus.java
├── exception/
│   ├── DuplicateResourceException.java
│   ├── EntityNotFoundException.java
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java
├── repository/
│   └── ProductRepository.java
├── service/
│   ├── ProductService.java
│   └── ProductServiceImpl.java
└── specification/
    └── ProductSpecification.java
```

---

## API List

| Method | Path | Description |
|--------|------|-------------|
| POST | /products | Create a product |
| GET | /products | List products (paginated) |
| GET | /products/{id} | Get product by ID |
| PUT | /products/{id} | Update product |
| DELETE | /products/{id} | Delete product |
| GET | /products/search | Search with filters |
| PATCH | /products/{id}/status | Update status only |
| GET | /products/statistics | Get statistics |

---

## Example Requests

### Create Product
```http
POST /products
Content-Type: application/json

{
  "sku": "SKU-NEW-001",
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse",
  "category": "Electronics",
  "price": 1290.00,
  "quantity": 50,
  "status": "ACTIVE"
}
```

### List Products (paginated)
```http
GET /products?page=0&size=5&sort=name,asc
```

### Search Products
```http
GET /products/search?keyword=laptop&category=Electronics&status=ACTIVE&minPrice=50000&maxPrice=100000
```

### Update Status
```http
PATCH /products/1/status
Content-Type: application/json

{
  "status": "INACTIVE"
}
```

---

## Example Responses

### ProductResponse
```json
{
  "id": 1,
  "sku": "SKU-ELEC-001",
  "name": "iPhone 15 Pro",
  "description": "Latest Apple smartphone with titanium frame",
  "category": "Electronics",
  "price": 49900.0000,
  "quantity": 100,
  "status": "ACTIVE",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### Statistics
```json
{
  "totalProducts": 15,
  "activeProducts": 10,
  "inactiveProducts": 5,
  "totalInventoryValue": 12345678.0000,
  "averagePrice": 15230.5000
}
```

### Error Response
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found with id: 999",
  "path": "/products/999"
}
```

---

## Business Rules

1. SKU must be unique across all products.
2. Product name cannot be duplicated within the same category.
3. Price must be greater than zero.
4. Quantity cannot be negative.
5. ACTIVE products cannot be deleted.
6. Products with quantity = 0 cannot have status ACTIVE (applies to create and update).
7. `createdAt` is set once and never updated (`updatable = false`).
8. Null/blank search filters are ignored.
9. Returns HTTP 404 when a product does not exist.
10. All validation messages are descriptive and field-specific.
11. SKU cannot be modified after creation (PUT validates SKU matches).
12. Category cannot be blank.
13. All input strings are trimmed before persistence.

---

## Assumptions

- Statistics cover all products regardless of status (inventory value = SUM of price × quantity for all products).
- The `search` endpoint is separate from the paginated `list` endpoint to keep concerns distinct.
- `PUT /products/{id}` requires the SKU in the body and validates it has not changed.
- Enum values (`ACTIVE`, `INACTIVE`) are case-sensitive in request bodies.
- `description` is optional and may be null.
