package com.tackr.tinyledger.api.utils;

import com.tackr.tinyledger.api.domain.Transaction;
import com.tackr.tinyledger.api.dto.response.BalanceResponse;
import com.tackr.tinyledger.api.dto.response.TransactionResponse;

public class TransactionMapper {

    public static TransactionResponse toTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTimestamp()
        );
    }

    public static BalanceResponse toBalanceResponse(Transaction transaction) {
        return new BalanceResponse(transaction.getAmount());
    }
}
