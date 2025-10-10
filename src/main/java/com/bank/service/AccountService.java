package com.bank.service;

import com.bank.entity.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@Transactional
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

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
