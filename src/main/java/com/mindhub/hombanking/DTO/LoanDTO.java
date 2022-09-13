package com.mindhub.hombanking.DTO;

import com.mindhub.hombanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private long id;
    private String name;
    private double maxAmount;
    private List<Integer> payments;
    private double percentage;
    private int percentageInt;

    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.maxAmount = loan.getMaxAmount();
        this.name = loan.getName();
        this.payments = loan.getPayments();
        this.percentage = loan.getPercentage();
        this.percentageInt = loan.getPercentageInt();
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
}
