package com.mindhub.hombanking.DTO;

import com.mindhub.hombanking.models.Account;
import com.mindhub.hombanking.models.AccountType;
import com.mindhub.hombanking.models.Client;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double Balance;
    private Set<TransactionDTO> transactions;
    private long clientId;
    private boolean existAccount;
    private AccountType accountType;
    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.clientId = account.getClient().getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.Balance = account.getBalance();
        this.transactions = account.getTransaction().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.existAccount = account.isExistAccount();
        this.accountType = account.getAccountType();

    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isExistAccount() {
        return existAccount;
    }

    public void setExistAccount(boolean existAccount) {
        this.existAccount = existAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}
