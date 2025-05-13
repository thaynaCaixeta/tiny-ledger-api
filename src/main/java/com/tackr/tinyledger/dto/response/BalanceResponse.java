package com.tackr.tinyledger.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Represents the account balance")
public record BalanceResponse (
        @Schema(description = "Account balance", example = "345.00")
        BigDecimal balance
){}
