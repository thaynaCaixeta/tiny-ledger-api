package com.tackr.tinyledger.service;

import com.tackr.tinyledger.domain.TransactionType;
import com.tackr.tinyledger.dto.request.TransactionRequest;
import com.tackr.tinyledger.dto.response.TransactionResponse;
import com.tackr.tinyledger.exception.InsufficientFundsException;
import com.tackr.tinyledger.repository.InMemoryLedgerRepository;
import com.tackr.tinyledger.repository.LedgerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LedgerServiceTest {

    private LedgerService service;

    @BeforeEach
    void setup() {
        LedgerRepository repository = new InMemoryLedgerRepository();
        this.service = new LedgerService(repository);
    }

    @Test
    void shouldProcessDepositAndUpdateBalance() {
        TransactionRequest request = new TransactionRequest();
        request.setType(TransactionType.DEPOSIT);
        request.setAmount(new BigDecimal("154.98"));

        TransactionResponse response = service.processAndReturnTransaction(request);

        assertEquals(new BigDecimal("154.98"), response.amount());
    }

    @Test
    void shouldProcessWithdrawAndUpdateBalance() {
        TransactionRequest deposit = new TransactionRequest();
        deposit.setType(TransactionType.DEPOSIT);
        deposit.setAmount(new BigDecimal("187.00"));

        TransactionResponse depositResponse = service.processAndReturnTransaction(deposit);
        assertEquals(deposit.getAmount(), depositResponse.amount());

        TransactionRequest withdraw = new TransactionRequest();
        withdraw.setType(TransactionType.WITHDRAW);
        withdraw.setAmount(new BigDecimal("37.00"));
        service.processAndReturnTransaction(withdraw);

        BigDecimal finalBalance = service.getBalance().balance();

        service.processAndReturnTransaction(withdraw);
        assertEquals(new BigDecimal("150.00"), finalBalance);
    }

    @Test
    void shouldReturnHistory() {
        TransactionRequest firstDeposit = new TransactionRequest();
        firstDeposit.setType(TransactionType.DEPOSIT);
        firstDeposit.setAmount(new BigDecimal("350.00"));
        service.processAndReturnTransaction(firstDeposit);

        TransactionRequest secondDeposit = new TransactionRequest();
        secondDeposit.setType(TransactionType.DEPOSIT);
        secondDeposit.setAmount(new BigDecimal("350.00"));
        service.processAndReturnTransaction(secondDeposit);

        TransactionRequest withdraw = new TransactionRequest();
        withdraw.setType(TransactionType.WITHDRAW);
        withdraw.setAmount(new BigDecimal("200.00"));
        service.processAndReturnTransaction(withdraw);

        List<TransactionResponse> transactionsHistory = service.getTransactionHistory();

        assertEquals(3, transactionsHistory.size());
        assertEquals(TransactionType.DEPOSIT, transactionsHistory.getFirst().type());
        assertEquals(TransactionType.DEPOSIT, transactionsHistory.get(1).type());
        assertEquals(TransactionType.WITHDRAW, transactionsHistory.get(2).type());
    }

    @Test
    void shouldReturnCurrentBalance() {
        TransactionRequest request = new TransactionRequest();
        request.setType(TransactionType.DEPOSIT);
        request.setAmount(new BigDecimal("350.00"));

        service.processAndReturnTransaction(request);
        assertEquals(new BigDecimal("350.00"), service.getBalance().balance());
    }

    @Test
    void shouldThrowInsufficientFundsWhenWithdrawingMoreThanBalance() {
        TransactionRequest withdraw = new TransactionRequest();
        withdraw.setType(TransactionType.WITHDRAW);
        withdraw.setAmount(new BigDecimal("145.75"));

        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> service.processAndReturnTransaction(withdraw)
        );

        assertEquals("Transaction rejected: insufficient funds", exception.getMessage());
    }
}