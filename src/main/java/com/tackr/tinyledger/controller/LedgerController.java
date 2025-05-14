package com.tackr.tinyledger.controller;

import com.tackr.tinyledger.dto.request.TransactionRequest;
import com.tackr.tinyledger.dto.response.BalanceResponse;
import com.tackr.tinyledger.dto.response.TransactionResponse;
import com.tackr.tinyledger.service.LedgerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ledger")
@Tag(name = "Ledger", description = "Provides operations to register transactions and view balances")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Operation(
            summary = "Register a new transaction",
            description = "Records a deposit or a withdraw and returns the registered transaction data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transaction successfully registered"),
                    @ApiResponse(responseCode = "400", description = "Invalid request received"),
                    @ApiResponse(responseCode = "422", description = "Transaction failed due to insufficient funds")
            }
    )
    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> registerTransaction(@RequestBody @Valid TransactionRequest request) {
        TransactionResponse response = ledgerService.processAndReturnTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Retrieve the transactions history",
            description = "Returns a list of all transactions registered in the ledger"
    )
    @GetMapping("/transaction/history")
    public ResponseEntity<List<TransactionResponse>> retrieveHistory() {
        return ResponseEntity.ok(ledgerService.getTransactionHistory());
    }

    @Operation(
            summary = "Retrieve the account balance",
            description = "Returns the total balance of the ledger account"
    )
    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> retrieveBalance() {
        return ResponseEntity.ok(ledgerService.getBalance());
    }
}
