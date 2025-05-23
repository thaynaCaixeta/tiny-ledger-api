package com.tackr.tinyledger.dto.response;

import com.tackr.tinyledger.domain.TransactionStatus;
import com.tackr.tinyledger.domain.TransactionType;
import com.tackr.tinyledger.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionResponseTest {

    @Test
    void shouldHoldTransactionDataCorrectly() {
        TransactionType type = TransactionType.WITHDRAW;
        BigDecimal amount = new BigDecimal("200.13");
        String timestamp = DateUtils.toCustomFormat(LocalDateTime.now());
        TransactionStatus status = TransactionStatus.COMPLETED;

        TransactionResponse response = new TransactionResponse(type, amount, timestamp, status);

        assertEquals(type, response.type());
        assertEquals(amount, response.amount());
        assertEquals(timestamp, response.timestamp());
        assertEquals(status, response.status());
    }
}