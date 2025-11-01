package com.bank.controller.secured;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.entity.TransactionType;
import com.bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrivateTransactionHistoryControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private PrivateTransactionHistoryController controller;

    @Test
    void getTransactionHistoryPage() {
        //Arrange
        String actual = "private/transaction-history-page";
        String type = "income";
        Model model = Mockito.mock(Model.class);

        List<Transaction> transactionsIn = List.of(
                new Transaction());

        List<Transaction> transactionsOut = List.of(
                new Transaction());

        when(userService.getTheIncomingTransactions()).thenReturn(transactionsIn);
        when(userService.getTheOutcomingTransactions()).thenReturn(transactionsOut);

        //Act
        String result = controller.getTransactionHistoryPage(type, model);

        //Assert
        assertEquals(actual, result);
        verify(userService).getTheIncomingTransactions();
        verify(userService).getTheOutcomingTransactions();
        verify(model).addAttribute("transactionsIn", transactionsIn);
        verify(model).addAttribute("transactionsOut", transactionsOut);
        verify(model).addAttribute("show_type", type);
    }


}