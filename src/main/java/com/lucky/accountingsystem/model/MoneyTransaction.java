package com.lucky.accountingsystem.model;

import java.util.Date;

public class MoneyTransaction {

    private String sender;
    private String receiver;
    private double amount;
    private Date date;

    public MoneyTransaction(String sender, String receiver, double amount, Date date) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
