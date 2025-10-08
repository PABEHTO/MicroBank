package com.bank.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private int balance;

    @OneToOne(mappedBy = "account")
    private User user;

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL)
    public List<Transaction> outTransactions;

    @OneToMany(mappedBy = "targetAccount", cascade = CascadeType.ALL)
    public List<Transaction> incomingTransactions;

    public Account() {}

    public Account(String accountNumber, int balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;

    }

    public List<Transaction> getOutTransactions() {
        return outTransactions;
    }

    public void setOutTransactions(List<Transaction> outTransactions) {
        this.outTransactions = outTransactions;
    }

    public List<Transaction> getIncomingTransactions() {
        return incomingTransactions;
    }

    public void setIncomingTransactions(List<Transaction> incomingTransactions) {
        this.incomingTransactions = incomingTransactions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
