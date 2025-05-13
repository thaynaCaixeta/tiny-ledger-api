package com.tackr.tinyledger.dto.request;


import com.tackr.tinyledger.domain.TransactionType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWithValidData() {
        TransactionRequest request = new TransactionRequest();
        request.setType(TransactionType.DEPOSIT);
        request.setAmount(BigDecimal.TEN);

        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWhenTypeIsNull() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(BigDecimal.TEN);

        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(
                        v -> v.getPropertyPath().toString().equals("type")
                ));
    }

    @Test
    void shouldFailValidationWhenAmountIsNull() {
        TransactionRequest request = new TransactionRequest();
        request.setType(TransactionType.DEPOSIT);

        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Validation should fail when amount is null.");
        assertTrue(violations.stream()
                .anyMatch(
                        v -> v.getPropertyPath().toString().equals("amount")
                ));
    }

    @Test
    void shouldFailValidationWhenAmountIsNegative() {
        TransactionRequest request = new TransactionRequest();
        request.setType(TransactionType.DEPOSIT);
        request.setAmount(new BigDecimal("-10.07"));

        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Validation should fail for negative amount.");
        assertTrue(violations.stream()
                .anyMatch(
                        v -> v.getPropertyPath().toString().equals("amount")
                ));
    }

    @Test
    void shouldFailValidationWhenAmountIsZero() {
        TransactionRequest request = new TransactionRequest();
        request.setType(TransactionType.DEPOSIT);
        request.setAmount(BigDecimal.ZERO);

        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Validation should fail for zero amount..");
        assertTrue(violations.stream()
                .anyMatch(
                        v -> v.getPropertyPath().toString().equals("amount")
                ));
    }
}