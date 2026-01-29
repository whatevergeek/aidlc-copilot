package com.eventplanning.service;

import com.eventplanning.model.User;
import com.eventplanning.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_Success() {
        // Given
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(authenticationService.hashPassword("password123")).thenReturn("hashedPassword");
        
        User savedUser = new User();
        savedUser.setEmail("test@example.com");
        savedUser.setName("testuser");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        User result = userService.registerUser("test@example.com", "testuser", "password123");

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getName());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void registerUser_EmailExists_ThrowsException() {
        // Given
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When & Then
        assertThrows(RuntimeException.class, () -> 
            userService.registerUser("test@example.com", "testuser", "password123"));
    }

    @Test
    void authenticateUser_Success() {
        // Given
        User user = new User();
        user.setId("user123");
        user.setEmail("test@example.com");
        user.setPassword("hashedPassword");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(authenticationService.verifyPassword("password123", "hashedPassword")).thenReturn(true);
        when(authenticationService.generateToken("user123", "test@example.com")).thenReturn("jwt-token");

        // When
        String result = userService.authenticateUser("test@example.com", "password123");

        // Then
        assertNotNull(result);
        assertEquals("jwt-token", result);
    }

    @Test
    void authenticateUser_InvalidPassword_ThrowsException() {
        // Given
        User user = new User();
        user.setPassword("hashedPassword");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(authenticationService.verifyPassword("wrongpassword", "hashedPassword")).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> 
            userService.authenticateUser("test@example.com", "wrongpassword"));
    }

    @Test
    void authenticateUser_UserNotFound_ThrowsException() {
        // Given
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> 
            userService.authenticateUser("nonexistent@example.com", "password123"));
    }

    @Test
    void getUserById_Success() {
        // Given
        User user = new User();
        user.setId("user123");
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userService.getUserById("user123");

        // Then
        assertTrue(result.isPresent());
        assertEquals("user123", result.get().getId());
    }

    @Test
    void getUserByEmail_Success() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userService.getUserByEmail("test@example.com");

        // Then
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
    }

    @Test
    void updateUserProfile_Success() {
        // Given
        User user = new User();
        user.setId("user123");
        user.setName("oldname");
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User result = userService.updateUserProfile("user123", "newname");

        // Then
        assertNotNull(result);
        assertEquals("newname", result.getName());
    }

    @Test
    void updateUserProfile_UserNotFound_ThrowsException() {
        // Given
        when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> 
            userService.updateUserProfile("nonexistent", "newname"));
    }
}