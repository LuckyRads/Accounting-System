package com.lucky.accountingsystem.model;

import java.io.Serializable;
import java.util.List;

public class SubCategory implements Serializable, Category {

    private String name;
    private String description;
    private List<Transaction> transactions;
    private List<SubCategory> subCategories;
    private List<Person> responsibleUsers;

    public SubCategory(String name, String description, List<Transaction> transactions, List<SubCategory> subCategories, List<Person> responsibleUsers) {
        this.name = name;
        this.description = description;
        this.transactions = transactions;
        this.subCategories = subCategories;
        this.responsibleUsers = responsibleUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Person> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(List<Person> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public void addResponsibleUser(Person person) {
        this.responsibleUsers.add(person);
    }

}
