package com.tackr.tinyledger.api.service;

import com.tackr.tinyledger.api.domain.TransactionType;
import com.tackr.tinyledger.api.dto.request.TransactionRequest;
import com.tackr.tinyledger.api.dto.response.BalanceResponse;
import com.tackr.tinyledger.api.dto.response.TransactionResponse;
import com.tackr.tinyledger.api.repository.InMemoryLedgerRepository;
import com.tackr.tinyledger.api.repository.LedgerRepository;
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

        BalanceResponse response = service.processTransaction(request);

        assertEquals(new BigDecimal("154.98"), response.balance());
    }

    @Test
    void shouldProcessWithdrawAndUpdateBalance() {
        TransactionRequest deposit = new TransactionRequest();
        deposit.setType(TransactionType.DEPOSIT);
        deposit.setAmount(new BigDecimal("187.00"));

        BalanceResponse depositResponse = service.processTransaction(deposit);
        assertEquals(deposit.getAmount(), depositResponse.balance());

        TransactionRequest withdraw = new TransactionRequest();
        withdraw.setType(TransactionType.WITHDRAW);
        withdraw.setAmount(new BigDecimal("37.00"));

        BalanceResponse withdrawResponse = service.processTransaction(withdraw);
        assertEquals(new BigDecimal("150.00"), withdrawResponse.balance());
    }

    @Test
    void shouldThrowAnExceptionWhenWithdrawBiggerThanAvailableBalance() {
        TransactionRequest request = new TransactionRequest();
        request.setType(TransactionType.WITHDRAW);
        request.setAmount(BigDecimal.TEN);

        IllegalArgumentException expectedException =
                assertThrows(IllegalArgumentException.class, () ->
                    service.processTransaction(request)
                );
        assertEquals("Transaction rejected: insufficient funds", expectedException.getMessage());
    }

    @Test
    void shouldReturnHistory() {
        TransactionRequest firstDeposit = new TransactionRequest();
        firstDeposit.setType(TransactionType.DEPOSIT);
        firstDeposit.setAmount(new BigDecimal("350.00"));

        TransactionRequest secondDeposit = new TransactionRequest();
        secondDeposit.setType(TransactionType.DEPOSIT);
        secondDeposit.setAmount(new BigDecimal("350.00"));

        TransactionRequest firstWithdraw = new TransactionRequest();
        firstWithdraw.setType(TransactionType.WITHDRAW);
        firstWithdraw.setAmount(new BigDecimal("200.00"));

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

        service.processTransaction(request);
        assertEquals(new BigDecimal("350.00"), service.getBalance().balance());
    }
}