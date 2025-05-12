package com.tackr.tinyledger.api.repository;

import com.tackr.tinyledger.api.domain.Transaction;
import com.tackr.tinyledger.api.domain.TransactionType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 @implNote
 * Simple in-memory repository for ledger transactions.
 * Not thread-safe beyond basic synchronized blocks.
 * TODO Implement a test at the service layer to provide more information about scenario and ensure it's working as intended
 */
@Repository
public class InMemoryLedgerRepository implements LedgerRepository {

    private final List<Transaction> transactions = new ArrayList<>();
    private BigDecimal balance = BigDecimal.ZERO;

    @Override
    public synchronized void save(Transaction transaction, BigDecimal updatedBalance) {
        transactions.add(transaction);
        this.balance = updatedBalance;
    }

    @Override
    public synchronized BigDecimal getBalance() {
        return balance;
    }

    @Override
    public synchronized List<Transaction> findAll() {
        // Note: The copyOf method returns an unmodified list
        return List.copyOf(transactions);
    }

    /**
     @implNote
     * Clears all ledger data.
     * Intended for use in tests only.
     */
    void clear() {
        transactions.clear();
        balance = BigDecimal.ZERO;
    }
}
