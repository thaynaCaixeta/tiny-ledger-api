package com.tackr.tinyledger.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void shouldInitializeTransactionCorrectly() {
        BigDecimal amount = new BigDecimal("158.32");
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount, TransactionStatus.COMPLETED);

        assertNotNull(transaction.getId());
        assertNotNull(transaction.getTimestamp());
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
        assertEquals(amount, transaction.getAmount());
    }
}