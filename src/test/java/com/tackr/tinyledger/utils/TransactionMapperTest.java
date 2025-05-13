package com.tackr.tinyledger.utils;

import com.tackr.tinyledger.domain.Transaction;
import com.tackr.tinyledger.domain.TransactionType;
import com.tackr.tinyledger.dto.response.BalanceResponse;
import com.tackr.tinyledger.dto.response.TransactionResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {

    @Test
    void shouldMapFromTransactionDomainToTransactionResponseCorrectly() {
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, BigDecimal.TEN);
        TransactionResponse response = TransactionMapper.toTransactionResponse(transaction);

        assertEquals(transaction.getType(), response.type());
        assertEquals(transaction.getAmount(), response.amount());
        assertEquals(transaction.getTimestamp(), response.timestamp());
    }

    @Test
    void shouldMapFromTransactionDomainToBalanceResponseCorrectly() {
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, BigDecimal.TWO);
        BalanceResponse response = TransactionMapper.toBalanceResponse(transaction);

        assertEquals(transaction.getAmount(), response.balance());
    }

}