package com.tackr.tinyledger.api.controller;

import com.tackr.tinyledger.api.dto.request.TransactionRequest;
import com.tackr.tinyledger.api.dto.response.BalanceResponse;
import com.tackr.tinyledger.api.dto.response.TransactionResponse;
import com.tackr.tinyledger.api.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> registerTransaction(@RequestBody @Valid TransactionRequest request) {
        TransactionResponse response = ledgerService.processAndReturnTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> retrieveHistory() {
        return ResponseEntity.ok(ledgerService.getTransactionHistory());
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> retrieveBalance() {
        return ResponseEntity.ok(ledgerService.getBalance());
    }
}
