package com.bank.controller.secured;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.exceptions.NotEnoughMoneyException;
import com.bank.exceptions.NotEnoughMoneyTransferException;
import com.bank.service.TransactionService;
import com.bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrivateTransactionControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private PrivateTransactionController controller;

    @Test
    void getDepositPage() {
        //Arrange
        String actual = "private/deposit-page";
        Model model = Mockito.mock(Model.class);
        User user = new User();
        user.setUsername("Test");
        Account account = new Account();
        user.setAccount(account);
        when(userService.getTheUser()).thenReturn(user);

        //Act
        String result = controller.getDepositPage(model);

        //Assert
        assertEquals(actual, result);
        verify(model).addAttribute("myAccount", account);
        verify(model).addAttribute("myName", user.getUsername());
    }

    @Test
    void doDeposit() throws NotEnoughMoneyException {
        //Arrange
        String actual = "redirect:/my-page/deposit";
        int amount = 1000;
        User user = new User();
        Account account = new Account();
        user.setAccount(account);

        when(userService.getTheUser()).thenReturn(user);

        //Act
        String result = controller.doDeposit(amount);

        //Assert
        assertEquals(actual, result);
        verify(userService).getTheUser();
        verify(transactionService).deposit(account, amount);
    }

    @Test
    void getWithdrawPage() {
        String actual = "private/withdraw-page";
        User user = new User();
        Account account = new Account();
        user.setAccount(account);
        Model model = Mockito.mock(Model.class);
        when(userService.getTheUser()).thenReturn(user);

        //Act
        String result = controller.getWithdrawPage(model);

        //Assert
        assertEquals(actual, result);
        verify(model).addAttribute("myAccount", account);
        verify(model).addAttribute("myName", user.getUsername());
    }

    @Test
    void testDoWithdraw() throws NotEnoughMoneyException {
        String actual = "redirect:/my-page/withdraw";
        int amount = 10000;
        User user = new User();
        Account account = new Account();
        user.setAccount(account);
        when(userService.getTheUser()).thenReturn(user);

        //Act
        String result = controller.doWithdraw(amount);

        //Assert
        assertEquals(actual, result);
        verify(userService).getTheUser();
        verify(transactionService).withdraw(account, amount);
    }

    @Test
    void doWithdraw_shouldThrowException() throws NotEnoughMoneyException {
        //Arrange
        int amount = 1000000000;
        User user = new User();
        Account account = new Account();
        user.setAccount(account);
        when(userService.getTheUser()).thenReturn(user);

        doThrow(new NotEnoughMoneyException()).when(transactionService).withdraw(account, amount);

        //Assert
        assertThrows(NotEnoughMoneyException.class, () -> controller.doWithdraw(amount));
        verify(userService).getTheUser();
        verify(transactionService).withdraw(account, amount);
    }

    @Test
    void getTransferPage() {
        //Arrange
        String actual = "private/transfer-page";
        User user = new User();
        Account account = new Account();
        user.setAccount(account);
        when(userService.getTheUser()).thenReturn(user);
        Model model = Mockito.mock(Model.class);

        //Act
        String result = controller.getTransferPage(model);

        //Assert
        assertEquals(actual, result);
        verify(model).addAttribute("myAccount", account);
        verify(model).addAttribute("myName", user.getUsername());
    }

    @Test
    void testDoTransfer() throws Exception {
        //Arrange
        String actual = "redirect:/my-page/transfer";
        Model model = Mockito.mock(Model.class);
        User user = new User();
        Account account = new Account();
        user.setAccount(account);
        String destinyAccountName = "Test";
        int amoun = 10000;

        when(userService.getTheUser()).thenReturn(user);

        //Act
        String result = controller.doTransfer(destinyAccountName, amoun, model);

        //Assert
        assertEquals(actual, result);
        verify(userService).getTheUser();
        verify(transactionService).transfer(account, destinyAccountName, amoun);
    }

    @Test
    void doTransfer_shouldThrowAnException() throws Exception {
        //Arrange
        Model model = Mockito.mock(Model.class);
        User user = new User();
        Account account = new Account();
        user.setAccount(account);
        String destinyAccountName = "Test";
        int amount = 100000000;

        when(userService.getTheUser()).thenReturn(user);
        doThrow(new NotEnoughMoneyTransferException())
                .when(transactionService)
                .transfer(account, destinyAccountName, amount);

        //Act + Assert
        assertThrows(NotEnoughMoneyTransferException.class,
                () -> controller.doTransfer(destinyAccountName, amount, model));
        verify(userService).getTheUser();
        verify(transactionService).transfer(account, destinyAccountName, amount);
    }
}