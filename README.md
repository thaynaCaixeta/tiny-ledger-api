# Tiny Ledger API

## Features
* Record money movements (deposit / withdraw)
* View transaction history

## Project Structure
```
com.tackr.tinyledger.api/
├── controller/    # REST endpoints
├── service/       # Business logic
├── domain/        # Business entities
├── dto/           # Request/response classes
│   ├── request/   # DTOs with attribute validation
│   └── response/  # Immutable response DTOs
├── utils/         # Utility classes
├── repository/    # In-memory persistence
└── config/        # Application setup
```
