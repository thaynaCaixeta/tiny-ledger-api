package com.tackr.tinyledger.exception;

import com.tackr.tinyledger.dto.response.ApiErrorResponse;
import com.tackr.tinyledger.utils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;

import java.time.LocalDateTime;

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
    void shouldHandleMessageConversionException() {
        HttpMessageConversionException ex = new HttpMessageConversionException("Unparseable JSON");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/ledger/transactions");

        ResponseEntity<ApiErrorResponse> response = handler.handleMessageConversionException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Malformed JSON request or invalid data types", body.message());
        assertEquals("/api/v1/ledger/transactions", body.path());
        assertEquals(400, body.statusCode());
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