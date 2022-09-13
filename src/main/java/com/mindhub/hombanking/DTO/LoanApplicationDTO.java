package com.mindhub.hombanking.DTO;

import com.mindhub.hombanking.models.ClientLoan;

import java.util.List;
import java.util.stream.Collectors;

public class LoanApplicationDTO {
    private long loanId;
    private double amount;
    private int payments;
    private String number;
    private String name;
    private double maxAmount;
    private double percentage;
    private int percentageInt;

    public int getPercentageInt() {
        return percentageInt;
    }

    public void setPercentageInt(int percentageInt) {
        this.percentageInt = percentageInt;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    private List<Integer> listPayments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getListPayments() {
        return listPayments;
    }

    public void setListPayments(List<Integer> listPayments) {
        this.listPayments = listPayments;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
