package com.tackr.tinyledger.api.service;

import com.tackr.tinyledger.api.domain.Transaction;
import com.tackr.tinyledger.api.domain.TransactionType;
import com.tackr.tinyledger.api.dto.request.TransactionRequest;
import com.tackr.tinyledger.api.dto.response.BalanceResponse;
import com.tackr.tinyledger.api.dto.response.TransactionResponse;
import com.tackr.tinyledger.api.repository.LedgerRepository;
import com.tackr.tinyledger.api.utils.TransactionMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LedgerService {

    private final LedgerRepository repository;

    public LedgerService(LedgerRepository repository) {
        this.repository = repository;
    }

    public TransactionResponse processAndReturnTransaction(TransactionRequest request) {
        BigDecimal requestAmount = request.getAmount();
        TransactionType requestType = request.getType();

        BigDecimal currentBalance = repository.getBalance();

        if (!hasEnoughBalance(requestType, currentBalance, requestAmount)) {
            throw new IllegalArgumentException("Transaction rejected: insufficient funds");
        }

        Transaction newTransaction = new Transaction(requestType, requestAmount);

        BigDecimal updatedBalance = calculateNewBalance(requestType, requestAmount, currentBalance);
        repository.save(newTransaction, updatedBalance);

        return TransactionMapper.toTransactionResponse(newTransaction);
    }

    public BalanceResponse getBalance() {
        return new BalanceResponse(repository.getBalance());
    }

    public List<TransactionResponse> getTransactionHistory() {
        return repository.findAll()
                .stream()
                .map(TransactionMapper::toTransactionResponse)
                .toList();
    }

    private boolean hasEnoughBalance(TransactionType type, BigDecimal currentBalance, BigDecimal requestAmount) {
        return type != TransactionType.WITHDRAW || currentBalance.subtract(requestAmount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private BigDecimal calculateNewBalance(TransactionType requestType, BigDecimal requestAmount, BigDecimal currentBalance) {
        return switch (requestType) {
            case DEPOSIT -> currentBalance.add(requestAmount);
            case WITHDRAW -> currentBalance.subtract(requestAmount);
        };
    }

}
