package com.tackr.tinyledger.api.repository;

import com.tackr.tinyledger.api.domain.Transaction;
import com.tackr.tinyledger.api.domain.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryLedgerRepositoryTest {

    private InMemoryLedgerRepository repository;

    @BeforeEach
    void setup() {
        repository = new InMemoryLedgerRepository();
    }

    @Test
    void shouldSaveTransactionAndUpdateBalance() {
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, new BigDecimal("100.58"));
        repository.save(transaction);

        assertEquals(new BigDecimal("100.58"), repository.getBalance());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldSubtractFromBalanceOnWithdraw() {
        repository.save(new Transaction(TransactionType.DEPOSIT, new BigDecimal("97.45")));
        repository.save(new Transaction(TransactionType.WITHDRAW, new BigDecimal("47.45")));

        assertEquals(new BigDecimal("50.00"), repository.getBalance());
    }

    @Test
    void shouldReturnImmutableTransactionList() {
        repository.save(new Transaction(TransactionType.DEPOSIT, new BigDecimal("65.00")));

        List<Transaction> transactions = repository.findAll();
        assertThrows(UnsupportedOperationException.class, () -> transactions.add(null));
    }

    @Test
    void shouldClearRepository() {
        repository.save(new Transaction(TransactionType.DEPOSIT, new BigDecimal("500.00")));
        repository.clear();

        assertEquals(BigDecimal.ZERO, repository.getBalance());
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void shouldRejectNullTransaction() {
        assertThrows(NullPointerException.class, () -> repository.save(null));
    }
}