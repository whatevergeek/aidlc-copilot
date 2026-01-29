package com.eventplanning.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void hashPassword_Success() {
        // When
        String hashedPassword = authenticationService.hashPassword("password123");

        // Then
        assertNotNull(hashedPassword);
        assertNotEquals("password123", hashedPassword);
        assertTrue(hashedPassword.startsWith("$2a$"));
    }

    @Test
    void verifyPassword_Success() {
        // Given
        String password = "password123";
        String hashedPassword = authenticationService.hashPassword(password);

        // When
        boolean result = authenticationService.verifyPassword(password, hashedPassword);

        // Then
        assertTrue(result);
    }

    @Test
    void verifyPassword_WrongPassword_ReturnsFalse() {
        // Given
        String password = "password123";
        String wrongPassword = "wrongpassword";
        String hashedPassword = authenticationService.hashPassword(password);

        // When
        boolean result = authenticationService.verifyPassword(wrongPassword, hashedPassword);

        // Then
        assertFalse(result);
    }

    @Test
    void generateToken_Success() {
        // Given
        ReflectionTestUtils.setField(authenticationService, "jwtSecret", "mySecretKey123456789012345678901234567890");
        ReflectionTestUtils.setField(authenticationService, "jwtExpiration", 86400000);

        // When
        String token = authenticationService.generateToken("user123", "test@example.com");

        // Then
        assertNotNull(token);
        assertTrue(token.contains("."));
    }

    @Test
    void validateToken_ValidToken_ReturnsTrue() {
        // Given
        ReflectionTestUtils.setField(authenticationService, "jwtSecret", "mySecretKey123456789012345678901234567890");
        ReflectionTestUtils.setField(authenticationService, "jwtExpiration", 86400000);
        String token = authenticationService.generateToken("user123", "test@example.com");

        // When
        boolean result = authenticationService.validateToken(token);

        // Then
        assertTrue(result);
    }

    @Test
    void validateToken_InvalidToken_ReturnsFalse() {
        // Given
        ReflectionTestUtils.setField(authenticationService, "jwtSecret", "mySecretKey123456789012345678901234567890");
        String invalidToken = "invalid.token.here";

        // When
        boolean result = authenticationService.validateToken(invalidToken);

        // Then
        assertFalse(result);
    }

    @Test
    void getUserIdFromToken_Success() {
        // Given
        ReflectionTestUtils.setField(authenticationService, "jwtSecret", "mySecretKey123456789012345678901234567890");
        ReflectionTestUtils.setField(authenticationService, "jwtExpiration", 86400000);
        String userId = "user123";
        String token = authenticationService.generateToken(userId, "test@example.com");

        // When
        String result = authenticationService.getUserIdFromToken(token);

        // Then
        assertEquals(userId, result);
    }
}