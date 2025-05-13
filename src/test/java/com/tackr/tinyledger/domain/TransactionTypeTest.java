package com.tackr.tinyledger.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTypeTest {
    /**
     *    @implNote  As this is an enum class, the test case below only intends to ensure the existence of the expected values
     */
    @Test
    void shouldContainExpectedEnumValues() {
        assertEquals(TransactionType.DEPOSIT, TransactionType.valueOf("DEPOSIT"));
        assertEquals(TransactionType.WITHDRAW, TransactionType.valueOf("WITHDRAW"));
    }
}