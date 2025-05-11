package com.tackr.tinyledger.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tackr.tinyledger.api.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        TransactionType type,
        BigDecimal amount,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp
) {}
