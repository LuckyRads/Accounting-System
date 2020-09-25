package com.lucky.accountingsystem.model;

import java.io.Serializable;

public class Expense implements Serializable {

    private String name;
    private MoneyTransaction moneyTransaction;

    public Expense(String name, MoneyTransaction moneyTransaction) {
        this.name = name;
        this.moneyTransaction = moneyTransaction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MoneyTransaction getMoneyTransaction() {
        return moneyTransaction;
    }

    public void setMoneyTransaction(MoneyTransaction moneyTransaction) {
        this.moneyTransaction = moneyTransaction;
    }

}
