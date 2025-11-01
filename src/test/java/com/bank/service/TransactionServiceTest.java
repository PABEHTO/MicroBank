package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.exceptions.NotEnoughMoneyException;
import com.bank.exceptions.NotEnoughMoneyTransferException;
import com.bank.exceptions.SelfTransferException;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testDeposit() {
        //Arrange
        int amount = 100000000;
        Account account = new Account();

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

        //Act
        transactionService.deposit(account, amount);

        //Assert
        verify(transactionRepository).save(any(Transaction.class));
        verify(accountRepository).save(account);
    }

    @Test
    void testWithdraw() throws NotEnoughMoneyException {
        //Arrange
        int amount = 1000;
        Account account = new Account();
        account.setBalance(100000);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

        //Act
        transactionService.withdraw(account, amount);

        //Assert
        verify(transactionRepository).save(any(Transaction.class));
        verify(accountRepository).save(account);
    }

    @Test
    void testWithdraw_shouldThrowException() throws NotEnoughMoneyException {
        //Arrange
        int amount = 100000;
        Account account = new Account();
        account.setBalance(100);

        //Act + Assert
        assertThrows(NotEnoughMoneyException.class, () -> transactionService.withdraw(account, amount));
    }

    @Test
    void testTransfer() throws Exception{
        //Arrange
        Account account1 = new Account();
        account1.setBalance(100000);

        String accountToName = "Test";
        Account account2 = new Account();
        account2.setAccountNumber(accountToName);
        account2.setBalance(0);

        int amount = 1000;

        when(accountRepository.findByAccountNumber(accountToName)).thenReturn(account2);

        //Act
        transactionService.transfer(account1, accountToName, amount);

        //Assert
        verify(transactionRepository).save(any(Transaction.class));
        verify(accountRepository).save(account1);
        verify(accountRepository).save(account2);

        assertEquals(amount, account2.getBalance());
        assertEquals(99000, account1.getBalance());
    }

    @Test
    void testTransfer_shouldThrowAnException() throws Exception{
        //Arrange
        Account from = new Account();
        from.setBalance(10000);
        from.setAccountNumber("Test");

        Account to = new Account();
        to.setAccountNumber("Test");

        when(accountRepository.findByAccountNumber("Test")).thenReturn(from);

        //Act + Assert
        assertThrows(SelfTransferException.class, () -> transactionService.transfer(from, "Test", 1000));
    }
}