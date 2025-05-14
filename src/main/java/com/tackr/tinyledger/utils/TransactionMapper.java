package com.tackr.tinyledger.utils;

import com.tackr.tinyledger.domain.Transaction;
import com.tackr.tinyledger.dto.response.BalanceResponse;
import com.tackr.tinyledger.dto.response.TransactionResponse;

public class TransactionMapper {

    public static TransactionResponse toTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getType(),
                transaction.getAmount(),
                DateUtils.toCustomFormat(transaction.getTimestamp()),
                transaction.getStatus()
        );
    }

    public static BalanceResponse toBalanceResponse(Transaction transaction) {
        return new BalanceResponse(transaction.getAmount());
    }
}
