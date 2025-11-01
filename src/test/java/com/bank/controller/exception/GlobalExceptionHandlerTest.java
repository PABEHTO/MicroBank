package com.bank.controller.exception;

import com.bank.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleNotEnoughMoneyException() {
        //Arrange
        String actual = "redirect:/my-page/withdraw?notEnoughMoney=true";
        NotEnoughMoneyException notEnoughMoneyException = new NotEnoughMoneyException();

        //Act
        String result = globalExceptionHandler.handleNotEnoughMoneyException(notEnoughMoneyException);

        //Assert
        assertEquals(actual, result);
    }

    @Test
    void handleNotEnoughMoneyTransferException() {
        //Arrange
        String actual = "redirect:/my-page/transfer?notEnoughMoney=true";
        NotEnoughMoneyTransferException ex = new NotEnoughMoneyTransferException();

        //Act
        String result = globalExceptionHandler.handleNotEnoughMoneyTransferException(ex);

        //Assert
        assertEquals(actual, result);
    }

    @Test
    void handleSelfTransferException() {
        //Arrange
        String actual = "redirect:/my-page/transfer?selfTransfer=true";
        SelfTransferException ex = new SelfTransferException();

        //Act
        String result = globalExceptionHandler.handleSelfTransferException(ex);

        //Assert
        assertEquals(actual, result);
    }

    @Test
    void handleTransferUserNotFoundException() {
        //Arrange
        String actual = "redirect:/my-page/transfer?transferUserNotFound=true";
        TransferUserNotFoundException ex = new TransferUserNotFoundException();

        //Act
        String result = globalExceptionHandler.handleTransferUserNotFoundException(ex);

        //Assert
        assertEquals(actual, result);
    }

    @Test
    void handleException() {
        //Arrange
        String actual = "redirect:/error";

        //Act
        String result = globalExceptionHandler.handleException();

        //Assert
        assertEquals(actual, result);
    }

    @Test
    void getErrorPage() {
        //Arrange
        String actual = "public/error/error-page";

        //Act
        String result = globalExceptionHandler.getErrorPage();

        //Assert
        assertEquals(actual, result);
    }

    @Test
    void handleExistsUserException() {
        //Arrange
        ExistsUserException ex = new ExistsUserException();
        String actual = "redirect:/registration?alreadyExists=true";

        //Act
        String result = globalExceptionHandler.handleExistsUserException(ex);

        //Assert
        assertEquals(actual, result);
    }
}