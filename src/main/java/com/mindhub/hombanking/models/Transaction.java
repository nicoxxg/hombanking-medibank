package com.mindhub.hombanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private TransactionType type;
    private Double amount;

    private String description;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    private boolean existTransaction;

    public Transaction(){};

    public Transaction(TransactionType type,Double amount,String description, LocalDateTime date,Account account,boolean existTransaction){
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
        this.existTransaction = existTransaction;
    };

    public boolean isExistTransaction() {
        return existTransaction;
    }

    public void setExistTransaction(boolean existTransaction) {
        this.existTransaction = existTransaction;
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate() {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
