package com.lucky.accountingsystem.model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    private String name;
    private String description;
    private List<Transaction> transactions;
    private List<Category> subCategories;
    private List<Person> responsiblePeople;

    public Category(String name, String description, List<Transaction> transactions, List<Category> categories, List<Person> responsiblePeople) {
        this.name = name;
        this.description = description;
        this.transactions = transactions;
        this.subCategories = categories;
        this.responsiblePeople = responsiblePeople;
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

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Person> getResponsiblePeople() {
        return responsiblePeople;
    }

    public void setResponsiblePeople(List<Person> responsiblePeople) {
        this.responsiblePeople = responsiblePeople;
    }

    public void addResponsiblePerson(Person person) {
        this.responsiblePeople.add(person);
    }

}
