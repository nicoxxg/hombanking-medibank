package com.mindhub.hombanking.DTO;

import com.mindhub.hombanking.models.Client;
import com.mindhub.hombanking.models.ClientLoan;
import com.mindhub.hombanking.models.Loan;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientLoanDTO {
    private long id;
    private double amount;
    private int payments;
    private long loanId;
    private String name;
    private double amountPayment;

    public ClientLoanDTO() {
    }
    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.name = clientLoan.getLoan().getName();
        this.amountPayment = clientLoan.getAmountPayment();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(double amountPayment) {
        this.amountPayment = amountPayment;
    }
}
