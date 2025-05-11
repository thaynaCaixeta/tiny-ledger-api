package com.tackr.tinyledger.api.dto.request;

import com.tackr.tinyledger.api.domain.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    @NotNull(message = "Transaction type is required.")
    private TransactionType type;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
    private BigDecimal amount;
}
