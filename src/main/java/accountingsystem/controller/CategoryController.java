package main.java.accountingsystem.controller;

import main.java.accountingsystem.model.*;
import main.java.accountingsystem.service.InputService;
import main.java.accountingsystem.service.MessageService;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    public static void manageCategories(List<Category> categories, List<Person> people) {
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
                    categories.add(createCategory(people));
                    break;
                case "desc":
                    describeCategory(categories, people);
                    break;
                case "upd":
                    updateCategory(categories, people);
                    break;
                case "rem":
                    removeCategory(categories);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void showCategories(List<Category> categories) {
        System.out.println("Categories:");
        for (Category category : categories) {
            System.out.println(category.getName());
        }
    }

    public static Category createCategory(List<Person> people) {
        System.out.println("Category creation");

        System.out.println("Enter name:");
        String name = InputService.getInput();

        System.out.println("Enter description:");
        String description = InputService.getInput();

        Category category = new Category(name, description, new ArrayList<Transaction>(), new ArrayList<Category>(), new ArrayList<Person>());

        chooseResponsiblePeople(category, people);

        return category;

    }

    public static void describeCategory(List<Category> categories, List<Person> people) {
        MessageService.showMessage(new String[]{"Choose which categories info you would like to see:"});

        showCategories(categories);

        String chosenCategory = InputService.getInput();

        for (Category category : categories) {
            if (chosenCategory.equals(category.getName())) {
                describeCategory(category, people);
            }
        }
    }

    public static void updateCategory(List<Category> categories, List<Person> people) {
        MessageService.showMessage(new String[]{"Choose which category's info you would like to update:"});

        showCategories(categories);

        String chosenCategory = InputService.getInput();

        for (Category category : categories) {
            if (chosenCategory.equals(category.getName())) {
                categories.remove(category);
                category = updateCategory(category, people);
                categories.add(category);
            }
        }
    }

    public static void removeCategory(List<Category> categories) {
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

    private static void describeCategory(Category category, List<Person> people) {
        String transactionsString = "";
        String subCategoryString = "";
        String responsiblePeopleString = "";

        for (Transaction transaction : category.getTransactions()) {
            transactionsString += transaction.getName() + ",";
        }

        for (Category subCategory : category.getSubCategories()) {
            subCategoryString += subCategory.getName() + ",";
        }

        for (Person person : category.getResponsiblePeople()) {
            responsiblePeopleString += person.getEmail() + ",";
        }

        MessageService.showMessage(new String[]{"Category info",
                "name: " + category.getName(),
                "description: " + category.getDescription(),
                "transactions: " + transactionsString,
                "subcategories: " + subCategoryString,
                "responsible users: " + responsiblePeopleString});
    }

    private static Category updateCategory(Category category, List<Person> people) {
        MessageService.showMessage(new String[]{"Edit category's info",
                "Enter new category's info in format:",
                "name;description"});

        String categoryInfo[] = InputService.getInput().split(";");

        chooseManageTransactions(category);
        chooseManageSubCategories(category, people);
        chooseResponsiblePeople(category, people);

        return new Category(categoryInfo[0], categoryInfo[1], category.getTransactions(), category.getSubCategories(), category.getResponsiblePeople());
    }

    private static void chooseManageTransactions(Category category) {
        MessageService.showMessage(new String[]{"Would you like to manage transactions of this category? (y/n)"});
        switch (InputService.getInput()) {
            case "y":
                TransactionController.manageTransactions(category.getTransactions());
                break;
            case "n":
                break;
        }
    }

    private static void chooseManageSubCategories(Category category, List<Person> people) {
        MessageService.showMessage(new String[]{"Would you like to manage subcategories of this category? (y/n)"});
        switch (InputService.getInput()) {
            case "y":
                CategoryController.manageCategories(category.getSubCategories(), people);
                break;
            case "n":
                break;
        }
    }

    private static void chooseResponsiblePeople(Category category, List<Person> people) {
        MessageService.showMessage(new String[]{"Would you like to manage responsible people of this category? (y/n)"});
        switch (InputService.getInput()) {
            case "y":
                String message[] = {"Please choose a responsible people for this category from the list below.",
                        "If you are satisfied with the amount of responsible people, please type \"done\""};

                PersonController.showPeople(people);

                String choice = "";
                while (true) {
                    MessageService.showMessage(message);
                    choice = InputService.getInput();

                    switch (choice) {
                        case "done":
                            return;
                        default:
                            for (Person person : people) {
                                if (person.getEmail().equals(choice)) {
                                    category.addResponsiblePerson(person);
                                }
                            }
                    }
                }
            case "n":
                break;
        }
    }

    public static void addReplaceCategory(Category categoryToAdd, List<Category> subCategories) {
        for (Category category : subCategories) {
            if (categoryToAdd.getName().equals(category.getName())) {
                subCategories.remove(category);
                break;
            }
        }
        subCategories.add(categoryToAdd);
    }

}
