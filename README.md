# Tiny Ledger API

## Features
* Record money movements (deposit / withdraw)
* View transaction history

## Project Structure
```
com.tackr.tinyledger.api/
├── src/main/java
│   └── com/tackr/tinyledger/
│       ├── controller/        # REST API endpoints
│       ├── service/           # Application business logic
│       ├── domain/            # Core domain entities (e.g., Transaction, TransactionType)
│       ├── dto/               # API data transfer objects (DTOs)
│       │   ├── request/       # Input DTOs with validation annotations
│       │   └── response/      # Output DTOs (immutable records)
│       ├── repository/        # In-memory persistence (with interface abstraction)
│       └── utils/             # Utility and mapping classes (e.g., TransactionMapper)
│
├── src/main/resources
│   └── docs/                  # Static OpenAPI spec (YAML and JSON formats)
```

## API Documentation
Interactive documentation available via Swagger UI:
``` http://localhost:8080/swagger-ui.html ```
