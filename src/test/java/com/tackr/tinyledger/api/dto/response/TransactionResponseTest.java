package com.tackr.tinyledger.api.dto.response;

import com.tackr.tinyledger.api.domain.TransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionResponseTest {

    @Test
    void shouldHoldTransactionDataCorrectly() {
        TransactionType type = TransactionType.WITHDRAW;
        BigDecimal amount = new BigDecimal("200.13");
        LocalDateTime timestamp = LocalDateTime.now();

        TransactionResponse response = new TransactionResponse(type, amount, timestamp);

        assertEquals(type, response.type());
        assertEquals(amount, response.amount());
        assertEquals(timestamp, response.timestamp());
    }
}