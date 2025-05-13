package com.tackr.tinyledger.dto.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.math.BigDecimal;

class BalanceResponseTest {

    @Test
    void shouldHoldTransactionBalanceDataCorrectly() {
        BigDecimal amount = BigDecimal.TEN;
        BalanceResponse response = new BalanceResponse(amount);
        assertEquals(amount, response.balance());
    }
}