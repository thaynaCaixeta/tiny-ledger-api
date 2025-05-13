package com.tackr.tinyledger.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tackr.tinyledger.domain.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Transaction operations response")
public record TransactionResponse(
        @Schema(description = "Type of the registered transaction", example = "WITHDRAW")
        TransactionType type,

        @Schema(description = "Amount of the registered transaction", example = "85.00")
        BigDecimal amount,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")

        @Schema(description = "Date and time of when the transaction was recorded")
        LocalDateTime timestamp
) {}
