package com.tackr.tinyledger.exception;

import com.tackr.tinyledger.dto.response.ApiErrorResponse;
import com.tackr.tinyledger.utils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleInsufficientFundsException() {
        InsufficientFundsException ex = new InsufficientFundsException("Not enough balance");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/ledger/transactions");

        ResponseEntity<ApiErrorResponse> response = handler.handleInsufficientFundsException(ex, request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value());
        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Not enough balance", body.message());
        assertEquals("/api/v1/ledger/transactions", body.path());
    }

    @Test
    void shouldHandleRequestValidationException() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("transactionRequest", "amount", "must be greater than zero"),
                new FieldError("transactionRequest", "type", "must not be null")
        ));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/ledger/transactions");

        ResponseEntity<ApiErrorResponse> response = handler.handleRequestValidationException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertTrue(body.message().contains("Validation failed"));
        assertTrue(body.message().contains("amount: must be greater than zero"));
        assertTrue(body.message().contains("type: must not be null"));
        assertEquals("/api/v1/ledger/transactions", body.path());
    }

    @Test
    void shouldHandleGenericException() {
        Exception ex = new RuntimeException("Unexpected failure");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/ledger/transactions");

        ResponseEntity<ApiErrorResponse> response = handler.handleGenericException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value());

        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("An unexpected error occurred", body.message());
        assertEquals(500, body.statusCode());
        assertEquals("/api/v1/ledger/transactions", body.path());
        assertNotNull(body.timestamp());
        assertTrue(DateUtils.toLocalDateTime(body.timestamp()).isBefore(LocalDateTime.now().plusSeconds(1)));

    }
}