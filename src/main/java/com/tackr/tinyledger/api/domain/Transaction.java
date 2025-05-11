package com.tackr.tinyledger.api.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Transaction {
    /*
         Why choosing this design?
         By having all the fields defined as final and only being modified in the constructor we can mimic immutability
     */
    private final UUID id; // Prevents potential collisions or manual ID insertion
    private final TransactionType type;
    private final BigDecimal amount; // Recommended for monetary values due to its higher accuracy than float and double
    private final LocalDateTime timestamp;

    public Transaction(TransactionType type, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }
}
