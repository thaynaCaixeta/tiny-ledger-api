# Data Transfer Object (DTO) Layer

The DTO layer defines the input and output contracts for every API endpoint.

## Request DTOs
Located in the ```request/``` directory, these classes describe the shape of incoming requests. Each field is validated using Java Bean Validation (the standard ```javax.validation API```). If a constraint cannot be expressed with built-in annotations, you can create a custom validator.

## Response DTOs
Located in the ```response/``` directory, these classes describe the shape of outgoing responses. They are implemented as Java records to enforce immutability: once created, their state cannot change. Whenever you need to return modified data, instantiate a new record rather than mutating an existing one.