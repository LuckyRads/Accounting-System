package com.lucky.accountingsystem.model;

import java.util.List;

public interface Category {

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public List<Expense> getExpenses();

    public void setExpenses(List<Expense> expenses);

    public List<SubCategory> getSubCategories();

    public void setSubCategories(List<SubCategory> subCategories);

}
