package com.bank.service;

import com.bank.entity.Account;
import com.bank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    void createAccount_shouldCreateAccount() {
        //act
        Account account = accountService.createAccount();

        //assert
        assertNotNull(account);
        assertNotNull(account.getAccountNumber());
        assertTrue(account.getBalance() > 0 && account.getBalance() < 100_000);

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void getAccountByAccountNumber_shouldReturnAccount() {
        //arrangement
        String number = "123456789";
        Account account = new Account(number, 1000);
        account.setAccountNumber(number);
        when(accountRepository.findByAccountNumber(number)).thenReturn(account);

        //act
        assertNotNull(accountService.getAccountByAccountNumber(number));
        assertEquals(account, accountService.getAccountByAccountNumber(number));

        //assert
        verify(accountRepository, times(2)).findByAccountNumber(number);
    }
}