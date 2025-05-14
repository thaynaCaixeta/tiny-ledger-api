package com.tackr.tinyledger.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response format")
public record ApiErrorResponse (
        @Schema(description = "Describes when the error occurred", example = "13-05-2025 14:22:00")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        String timestamp,

        @Schema(description = "HTTP status code", example = "404")
        int statusCode,

        @Schema(description = "Error message", example = "Transaction rejected: insufficient funds")
        String message,

        @Schema(description = "Request path called that returned an error", example = "/api/v1/ledger/transaction")
        String path
){}
