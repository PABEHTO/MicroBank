package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount() {
        int randomBalance = new Random().nextInt(100_000);
        String randomAccountNumber = UUID.randomUUID().toString();
        Account accountToCreate = new Account(randomAccountNumber, randomBalance);
        accountRepository.save(accountToCreate);
        return accountToCreate;
    }
}
