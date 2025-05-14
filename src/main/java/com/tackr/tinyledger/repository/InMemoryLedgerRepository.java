package com.tackr.tinyledger.repository;

import com.tackr.tinyledger.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @implNote
 * Simple in-memory repository for ledger transactions.
 * This implementation is intentionally not thread-safe,
 * as concurrency concerns are out of scope for this assessment.
 */
@Repository
public class InMemoryLedgerRepository implements LedgerRepository {

    private final List<Transaction> transactions = new ArrayList<>();
    private BigDecimal balance = BigDecimal.ZERO;

    @Override
    public void save(Transaction transaction, BigDecimal updatedBalance) {
        if (transaction == null) {
            throw new IllegalArgumentException("transaction must not be null");
        }
        if (updatedBalance == null) {
            throw new IllegalArgumentException("updatedBalance must not be null");
        }
        transactions.add(transaction);
        this.balance = updatedBalance;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public List<Transaction> findAll() {
        // Note: The use of the copyOf method here is justified by its characteristic of returning an unmodified list
        return List.copyOf(transactions);
    }

    /**
     * @implNote
     * Clears all ledger data.
     * Intended for use in tests only.
     */
    void clear() {
        transactions.clear();
        balance = BigDecimal.ZERO;
    }
}
