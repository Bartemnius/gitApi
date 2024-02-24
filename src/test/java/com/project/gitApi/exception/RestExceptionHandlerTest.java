package com.project.gitApi.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestExceptionHandlerTest {
    private RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    public void testHandleGithubUserNotFoundException() {
        // Arrange
        String errorMessage = "User not found!";
        GitHubUserNotFoundException exception = new GitHubUserNotFoundException(errorMessage);

        // Act
        ResponseEntity<ErrorResponse> responseEntity = restExceptionHandler.handleGithubUserNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND, errorResponse.getHttpStatus());
        assertEquals(errorMessage, errorResponse.getMessage());
    }
}