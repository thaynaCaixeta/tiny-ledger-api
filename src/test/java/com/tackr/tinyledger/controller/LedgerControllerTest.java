package com.tackr.tinyledger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tackr.tinyledger.domain.TransactionStatus;
import com.tackr.tinyledger.domain.TransactionType;
import com.tackr.tinyledger.dto.request.TransactionRequest;
import com.tackr.tinyledger.dto.response.BalanceResponse;
import com.tackr.tinyledger.dto.response.TransactionResponse;
import com.tackr.tinyledger.service.LedgerService;
import com.tackr.tinyledger.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

    @MockBean
    private LedgerService ledgerService;

    @Test
    void shouldReturn201WhenRegisteringTransaction() throws Exception {
        TransactionResponse expectedResponse =
                new TransactionResponse(TransactionType.DEPOSIT, new BigDecimal("150.00"), DateUtils.toCustomFormat(LocalDateTime.now()), TransactionStatus.COMPLETED);
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
    void shouldReturn400AndErrorResponseWhenJsonIsInvalid() throws Exception {
        String malformedJson = "{ \"amount\": \"invalidNumber\", \"type\": \"DEPOSIT\" }";

        mockMvc.perform(post("/api/v1/ledger/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Malformed JSON request or invalid data types"))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/api/v1/ledger/transaction"));
    }

    @Test
    void shouldReturnTransactionHistory() throws Exception {
        List<TransactionResponse> history = List.of(
                new TransactionResponse(TransactionType.DEPOSIT, new BigDecimal("185.90"), DateUtils.toCustomFormat(LocalDateTime.now()), TransactionStatus.COMPLETED));
        BDDMockito.given(ledgerService.getTransactionHistory()).willReturn(history);

        mockMvc.perform(get("/api/v1/ledger/transaction/history"))
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