# Tiny Ledger API

Tiny Ledger is a Spring Boot-based REST API for managing simple account transactions (deposits and withdrawals) with in-memory persistence.

It includes:
- Clean architecture with layered responsibilities
- Spring Bean Validation and centralized error handling
- Swagger UI via Springdoc OpenAPI for API exploration
- Easy-to-adapt in-memory repository
- Java 21 compatibility with modern Spring Boot features

## Business Features
The API supports the following core business capabilities:

### Register a Transaction
- Accepts deposits and withdrawals
- Validates:
  - Transaction type is present and valid (DEPOSIT or WITHDRAW)
  - Amount is a positive decimal
- Rejects withdrawals when the resulting balance would be negative
- 
### Get Account Balance
- Returns the current final balance after all transactions

### View Transaction History
- Returns a full list of all transactions
- Transactions are timestamped and stored in-memory

### Input Validation and Error Responses
- Rejects malformed JSON
- Catches validation errors (e.g., missing fields or negative amounts)
- Returns clear error messages with status and request path

## Project Structure
```
src/main/java/com/tackr/tinyledger/
├── controller/        # REST endpoints
├── service/           # Business logic
├── repository/        # In-memory data persistence
├── domain/            # Core business entities
├── dto/               # Request/response objects
│   ├── request/
│   └── response/
├── utils/             # Utility classes
└── exception/         # Custom exceptions and handlers
```

## Requirements
- Java 21
- Maven 3.9+
- Spring Boot 3.2.5

## Build and Run

#### Clone the repository
```
git clone https://github.com/your-username/tiny-ledger-api.git
cd tiny-ledger-api
```

#### Build the project
``` mvn clean install ```

#### Run the API
``` mvn spring-boot:run ```

## API Documentation (Swagger UI)

After starting the application, access the interactive API documentation at:

``` http://localhost:8080/swagger-ui.html ```

Note: This is powered by Springdoc OpenAPI 2.3.0, which provides automatic generation of OpenAPI specs from annotated controllers.

## Validation and Error Handling

- Validation is applied using javax.validation via @Valid on request DTOs.

- Global error handling is implemented using @RestControllerAdvice.

- Common error scenarios are handled:
  - MethodArgumentNotValidException: invalid or missing fields
  - HttpMessageConversionException: malformed or incorrect JSON
  - InsufficientFundsException: custom domain validation
- Errors return structured responses:
``` 
{
  "timestamp": "2025-05-14T10:35:00",
  "status": 400,
  "message": "Malformed JSON request or invalid data types",
  "path": "/api/v1/ledger/transaction"
}
```

## Running Tests
``` mvn test ```

Tests include:
- Unit tests for all layers
- Validation and exception handling coverage
- Mockito usage for service/controller isolation

## Future Improvements
- Replace in-memory storage with JPA or a database
- Add user accounts and authentication
- Add concurrency control

## License
This project is provided for educational and assessment purposes. Customize or extend it as needed.