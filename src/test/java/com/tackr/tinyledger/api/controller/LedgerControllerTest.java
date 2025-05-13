package com.tackr.tinyledger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tackr.tinyledger.api.domain.TransactionType;
import com.tackr.tinyledger.api.dto.request.TransactionRequest;
import com.tackr.tinyledger.api.dto.response.BalanceResponse;
import com.tackr.tinyledger.api.dto.response.TransactionResponse;
import com.tackr.tinyledger.api.service.LedgerService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LedgerController.class)
class LedgerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LedgerService ledgerService;

    @Test
    void shouldReturn201WhenRegisteringTransaction() throws Exception {
        TransactionResponse expectedResponse =
                new TransactionResponse(TransactionType.DEPOSIT, new BigDecimal("150.00"), LocalDateTime.now());
        BDDMockito.given(ledgerService.processAndReturnTransaction(any())).willReturn(expectedResponse);

        TransactionRequest request = new TransactionRequest();
        request.setType(TransactionType.DEPOSIT);
        request.setAmount(new BigDecimal("150.00"));

        mockMvc.perform(post("/api/v1/ledger/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value("150.0"));
    }

    @Test
    void shouldReturn400WhenInvalidTransactionIsSent() throws Exception {
        TransactionRequest invalidRequest = new TransactionRequest();

        mockMvc.perform(post("/api/v1/ledger/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnTransactionHistory() throws Exception {
        List<TransactionResponse> history = List.of(
                new TransactionResponse(TransactionType.DEPOSIT, new BigDecimal("185.90"), LocalDateTime.now()));
        BDDMockito.given(ledgerService.getTransactionHistory()).willReturn(history);

        mockMvc.perform(get("/api/v1/ledger/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value("DEPOSIT"));
    }

    @Test
    void shouldReturnBalance() throws Exception {
        BDDMockito.given(ledgerService.getBalance())
                .willReturn(new BalanceResponse(new BigDecimal("450.0")));

        mockMvc.perform(get("/api/v1/ledger/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("450.0"));
    }
}