package com.bank.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "source_account_id")
    private Account sourceAccountId;

    @ManyToOne
    @JoinColumn(name = "target_account_id")
    private Account targetAccountId;

    public Transaction() {}

    public Transaction(TransactionType type, int amount, LocalDateTime date, Account sourceAccountId, Account targetAccountId) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
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

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Account getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Account sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Account getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(Account targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
}
