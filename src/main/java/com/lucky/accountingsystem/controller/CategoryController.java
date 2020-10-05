package com.lucky.accountingsystem.controller;

import com.lucky.accountingsystem.model.*;
import com.lucky.accountingsystem.service.InputService;
import com.lucky.accountingsystem.service.MessageService;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    public static void manageCategories(List<SubCategory> categories) {
        String message[] = {"Choose an action",
                "show - show categories",
                "cre - create category",
                "desc - describe category",
                "upd - update category",
                "rem - remove category",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "show":
                    showCategories(categories);
                    break;
                case "cre":
                    categories.add(createCategory());
                    break;
                case "desc":
                    describeCategory(categories);
                    break;
                case "upd":
                    updateCategory(categories);
                    break;
                case "rem":
                    removeCategory(categories);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void showCategories(List<SubCategory> categories) {
        System.out.println("Categories:");
        for (SubCategory category : categories) {
            System.out.println(category.getName());
        }
    }

    public static SubCategory createCategory() {
        System.out.println("Category creation");

        System.out.println("Enter name:");
        String name = InputService.getInput();

        System.out.println("Enter description:");
        String description = InputService.getInput();

        return new SubCategory(name, description, new ArrayList<Transaction>(), new ArrayList<SubCategory>());

    }

    public static void describeCategory(List<SubCategory> categories) {
        MessageService.showMessage(new String[]{"Choose which categories info you would like to see:"});

        showCategories(categories);

        String chosenCategory = InputService.getInput();

        for (SubCategory category : categories) {
            if (chosenCategory.equals(category.getName())) {
                describeCategory(category);
            }
        }
    }

    public static void updateCategory(List<SubCategory> categories) {
        MessageService.showMessage(new String[]{"Choose which category's info you would like to update:"});

        showCategories(categories);

        String chosenCategory = InputService.getInput();

        for (SubCategory subCategory : categories) {
            if (chosenCategory.equals(subCategory.getName())) {
                categories.remove(subCategory);
                subCategory = updateCategory(subCategory);
                categories.add(subCategory);
            }
        }
    }

    public static void removeCategory(List<SubCategory> categories) {
        MessageService.showMessage(new String[]{"Choose which category you would like to remove:"});

        showCategories(categories);

        String chosenCategory = InputService.getInput();

        for (Category category : categories) {
            if (chosenCategory.equals(category.getName())) {
                categories.remove(category);
                return;
            }
        }
    }

    private static void describeCategory(SubCategory category) {
        String transactionsString = "";
        String subCategoryString = "";

        for (Transaction transaction : category.getTransactions()) {
            transactionsString += transaction.getName() + ",";
        }

        for (SubCategory subCategory : category.getSubCategories()) {
            subCategoryString += subCategory.getName() + ",";
        }

        MessageService.showMessage(new String[]{"Category info",
                "name: " + category.getName(),
                "description: " + category.getDescription(),
                "transactions: " + transactionsString,
                "subcategories: " + subCategoryString});

        chooseManageTransactions(category);
        chooseManageSubCategories(category);
    }

    private static SubCategory updateCategory(SubCategory category) {
        MessageService.showMessage(new String[]{"Edit category's info",
                "Enter new category's info in format:",
                "name;description"});

        String categoryInfo[] = InputService.getInput().split(";");

        chooseManageTransactions(category);
        chooseManageSubCategories(category);

        return new SubCategory(categoryInfo[0], categoryInfo[1], category.getTransactions(), category.getSubCategories());
    }

    private static void chooseManageTransactions(SubCategory category) {
        MessageService.showMessage(new String[]{"Would you like to manage transactions of this category? (y/n)"});
        switch (InputService.getInput()) {
            case "y":
                TransactionController.manageTransactions(category.getTransactions());
                break;
            case "n":
                break;
        }
    }

    private static void chooseManageSubCategories(SubCategory category) {
        MessageService.showMessage(new String[]{"Would you like to manage subcategories of this category? (y/n)"});
        switch (InputService.getInput()) {
            case "y":
                CategoryController.manageCategories(category.getSubCategories());
                break;
            case "n":
                break;
        }
    }

}