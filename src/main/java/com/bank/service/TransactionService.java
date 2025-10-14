package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.entity.TransactionType;
import com.bank.exceptions.NotEnoughMoneyException;
import com.bank.exceptions.NotEnoughMoneyTransferException;
import com.bank.exceptions.SelfTransferException;
import com.bank.exceptions.TransferUserNotFoundException;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public void deposit(Account account, int amount) {
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(
                TransactionType.DEPOSIT,
                amount,
                LocalDateTime.now(),
                null,
                account);
        transactionRepository.save(transaction);
        accountRepository.save(account);
    }

    public void withdraw(Account account, int amount) throws NotEnoughMoneyException {
        if (account.getBalance() < amount) {
            throw new NotEnoughMoneyException();
        }
        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction(TransactionType.WITHDRAW, amount, LocalDateTime.now(), account, null);
        transactionRepository.save(transaction);
        accountRepository.save(account);
    }

    public void transfer(Account from, String toAccount, int amount)
            throws NotEnoughMoneyTransferException,
            SelfTransferException,
            TransferUserNotFoundException {
        Account to = accountRepository.findByAccountNumber(toAccount);
        if (from.getBalance() < amount) {
            throw new NotEnoughMoneyTransferException();
        } else {
            if (to == null) {
                throw new TransferUserNotFoundException();
            }
            if (Objects.equals(from.getAccountNumber(), to.getAccountNumber())) {
                throw new SelfTransferException();
            }
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        Transaction transaction = new Transaction(TransactionType.TRANSFER, amount, LocalDateTime.now(), from, to);

        transactionRepository.save(transaction);
        accountRepository.save(from);
        accountRepository.save(to);
    }
}
