package com.danimo.report.movement.common.inputadapters.rest.handlers;

import com.danimo.report.common.infrastructure.inputadapters.rest.dto.RestApiError;
import com.danimo.report.common.infrastructure.inputadapters.rest.handlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleUserNotFoundException_returnsNotFoundResponse() {
        // Arrange
        String errorMessage = "Resource not found";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        // Act
        ResponseEntity<RestApiError> response = handler.handleUserNotFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
    }
}
