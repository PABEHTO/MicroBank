package com.bank.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private TransactionType type;

    @Column(name = "amount")
    private int amount;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "source_account")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "target_account")
    private Account targetAccount;

    public Transaction() {}

    public Transaction(TransactionType type, int amount, LocalDateTime date, Account sourceAccountId, Account targetAccountId) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.sourceAccount = sourceAccountId;
        this.targetAccount = targetAccountId;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getFormatedDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss"));
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Account getSourceAccountId() {
        return sourceAccount;
    }

    public void setSourceAccountId(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getTargetAccountId() {
        return targetAccount;
    }

    public void setTargetAccountId(Account targetAccount) {
        this.targetAccount = targetAccount;
    }
}
