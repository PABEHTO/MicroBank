package com.bank.controller.common;

import com.bank.entity.User;
import com.bank.entity.UserRole;
import com.bank.exceptions.ExistsUserException;
import com.bank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicAuthorizationControllerTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @InjectMocks
    private PublicAuthorizationController controller;

    @Test
    void getLoginPage() {
        //Arrange
        String loginPage = "public/authorization/login-page";

        //Act
        String result = controller.getLoginPage();

        //Assert
        assertEquals(loginPage, result);
    }

    @Test
    void getRegistrationPage() {
        //Arrange
        String registrationPage = "public/authorization/registration-page";

        //Act
        String result = controller.getRegistrationPage();

        //Assert
        assertEquals(registrationPage, result);
    }

    @Test
    void createUserTest() throws ExistsUserException {
        // Arrange
        String username = "Test";
        String email = "test@test.com";
        String password = "12345";
        String encodedPassword = "encoded12345";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userService.saveUser(any(User.class))).thenReturn(new User(username, email, encodedPassword, UserRole.USER));

        // Act
        String result = controller.createUser(username, email, password);

        // Assert
        assertEquals("redirect:/my-page", result);
        verify(passwordEncoder).encode(password);
        verify(userService, times(1)).saveUser(any(User.class));
    }
}