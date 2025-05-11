package com.tackr.tinyledger.api.repository;

import com.tackr.tinyledger.api.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * @implNote The LedgerRepository interface intends to abstract how transactions are stored
 * maintaining the application decoupled and making it easier to migrate from an in-memory
 * database to an enterprise database in the future.
 */
public interface LedgerRepository {

    void save(Transaction transaction);

    BigDecimal getBalance();

    List<Transaction> findAll();
}
