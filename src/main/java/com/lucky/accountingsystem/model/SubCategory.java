package com.lucky.accountingsystem.model;

import java.io.Serializable;
import java.util.List;

public class SubCategory implements Serializable, Category {

    private String name;
    private String description;
    private List<Transaction> transactions;
    private List<SubCategory> subCategories;
    private List<Person> responsibleUsers;

    public SubCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SubCategory(String name, String description, List<Transaction> transactions, List<SubCategory> subCategories, List<Person> responsibleUsers) {
        this.name = name;
        this.description = description;
        this.transactions = transactions;
        this.subCategories = subCategories;
        this.responsibleUsers = responsibleUsers;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    @Override
    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public List<Person> getResponsibleUsers() {
        return this.responsibleUsers;
    }

    @Override
    public void setResponsibleUsers(List<Person> ResponsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public void addResponsibleUser(Person person) {
        this.responsibleUsers.add(person);
    }

}
