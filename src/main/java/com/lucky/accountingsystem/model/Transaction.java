package com.lucky.accountingsystem.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private String name;
    private TransactionType transactionType;
    private String sender;
    private String receiver;
    private double amount;
    private Date date;

    public Transaction(String name, TransactionType transactionType, String sender, String receiver, double amount, Date date) {
        this.name = name;
        this.transactionType = transactionType;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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