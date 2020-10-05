package com.lucky.accountingsystem.model;

import java.util.List;

public interface Category {

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    List<Expense> getExpenses();

    void setExpenses(List<Expense> expenses);

    List<SubCategory> getSubCategories();

    void setSubCategories(List<SubCategory> subCategories);

}
