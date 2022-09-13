package com.mindhub.hombanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double Balance;
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.EAGER)//creo una relacion
    @JoinColumn(name = "user_id")
    private Client client;

    private boolean existAccount;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    //@OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    //private Set<TransactionDisabled> transactionDisableds = new HashSet<>();

    public Account(){}; //inicializa un objeto de una clase

    public Account(String number,LocalDateTime creationDate,double Balance,Client client,boolean existAccount,AccountType accountType){ //le paso argumentos //cuando lo inicializamos ahi le podemos dar los valores del objeto
        this.number = number;
        this.creationDate = creationDate;
        this.Balance = Balance;
        this.client = client;
        this.existAccount = existAccount;
        this.accountType = accountType;

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

    public String getNumber() {
        return number;
    }

    public long getId() {
        return id;
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

    public Set<Transaction> getTransaction() {
        return transactions;
    }
    //tiene que ser el jackson  //jackson transforma el json que recibo del front a un objeto para poder trabajar en java


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
