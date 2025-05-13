package com.tackr.tinyledger.service;

import com.tackr.tinyledger.domain.Transaction;
import com.tackr.tinyledger.domain.TransactionType;
import com.tackr.tinyledger.dto.request.TransactionRequest;
import com.tackr.tinyledger.dto.response.BalanceResponse;
import com.tackr.tinyledger.dto.response.TransactionResponse;
import com.tackr.tinyledger.repository.LedgerRepository;
import com.tackr.tinyledger.utils.TransactionMapper;
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

        if (!hasEnoughBalance(request, currentBalance)) {
            throw new IllegalArgumentException("Transaction rejected: insufficient funds");
        }

        Transaction newTransaction = new Transaction(requestType, requestAmount);
        BigDecimal updatedBalance = calculateNewBalance(request, currentBalance);

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

    private boolean hasEnoughBalance(TransactionRequest request, BigDecimal currentBalance) {
        return request.getType() == TransactionType.DEPOSIT || currentBalance.subtract(request.getAmount()).compareTo(BigDecimal.ZERO) >= 0;
    }

    private BigDecimal calculateNewBalance(TransactionRequest request, BigDecimal currentBalance) {
        return switch (request.getType()) {
            case DEPOSIT -> currentBalance.add(request.getAmount());
            case WITHDRAW -> currentBalance.subtract(request.getAmount());
        };
    }

}
