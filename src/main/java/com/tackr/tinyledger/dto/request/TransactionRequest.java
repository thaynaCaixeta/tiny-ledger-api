package com.tackr.tinyledger.dto.request;

import com.tackr.tinyledger.domain.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Request to register a new transaction (deposit or withdraw")
public class TransactionRequest {
    @NotNull(message = "Transaction type is required")
    @Schema(description = "Type of transaction", example = "DEPOSIT")
    private TransactionType type;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    @Schema(description = "Transaction amount (must be positive)", example = "150.00")
    private BigDecimal amount;
}