package com.tackr.tinyledger.repository;

import com.tackr.tinyledger.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * @implNote While not strictly necessary, I introduced LedgerRepository to demonstrate how abstraction can enable
 * future extensibility (e.g., swapping to a database without rewriting entirely the business logic).
 * In a real system, this interface would also facilitate scaling
 */
public interface LedgerRepository {

    void save(Transaction transaction, BigDecimal updatedBalance);

    BigDecimal getBalance();

    List<Transaction> findAll();
}
