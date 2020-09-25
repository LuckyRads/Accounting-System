package com.lucky.accountingsystem.model;

import java.io.Serializable;
import java.util.List;

public class SubCategory implements Serializable, Category {

    private String name;
    private String description;
    private List<Expense> expenses;
    private List<SubCategory> subCategories;

    public SubCategory(String name, String description, List<Expense> expenses, List<SubCategory> subCategories) {
        this.name = name;
        this.description = description;
        this.expenses = expenses;
        this.subCategories = subCategories;
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
    public List<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    @Override
    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public void addCategory(String name, String description, List<Expense> expenses, List<SubCategory> subCategories) {
        subCategories.add(new SubCategory(name, description, expenses, subCategories));
    }

}
