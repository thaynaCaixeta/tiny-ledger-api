package com.tackr.tinyledger.api.dto.response;

import java.math.BigDecimal;

public record BalanceResponse (
        BigDecimal balance
){}
