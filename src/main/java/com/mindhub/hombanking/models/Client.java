package com.mindhub.hombanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id //clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")//genera un id de 1 en 1
    @GenericGenerator(name = "native",strategy = "native")//verifica que no se repita el id
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<Account> Accounts = new HashSet<>();// crea un nuevo espacio en memoria

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    public Client(){
    }

    public Client(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts(){
        return Accounts;
    }

    /*
    public void addAcount(Account account){
        account.setUser(this);
        Accounts.add(account);
    }
    */

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
