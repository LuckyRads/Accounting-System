package com.lucky.accountingsystem.model;

import java.io.Serializable;

public class Expense implements Serializable {

    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
