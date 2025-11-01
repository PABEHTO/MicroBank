package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.entity.User;
import com.bank.exceptions.ExistsUserException;
import com.bank.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private Logger LOG = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, AccountService accountRepository) {
        this.accountService = accountRepository;
        this.userRepository = userRepository;
    }

    public User getTheUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User saveUser(User user) throws ExistsUserException {
       if (userRepository.existsByEmail(user.getEmail())) {
           throw new ExistsUserException("User already exists");
       }
        Account account = accountService.createAccount();
        user.setAccount(account);
        userRepository.save(user);
        return user;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public Account getTheAccount() {
        return getTheUser().getAccount();
    }

    public List<Transaction> getTheIncomingTransactions() {
        return getTheAccount().getIncomingTransactions();
    }

    public List<Transaction> getTheOutcomingTransactions() {
        return getTheAccount().getOutTransactions();
    }
}
