package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.controller.CategoryController;
import com.lucky.accountingsystem.controller.CompanyController;
import com.lucky.accountingsystem.controller.PersonController;
import com.lucky.accountingsystem.model.AccountingSystem;
import com.lucky.accountingsystem.model.Company;
import com.lucky.accountingsystem.model.Person;
import com.lucky.accountingsystem.model.SubCategory;

import java.io.*;
import java.util.List;

public class ImportService {

    public static void manageImports(AccountingSystem accountingSystem) {
        String message[] = {"Choose action",
                "all - import all available data",
                "cat - import all category data",
                "usr - user data import options",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "all":
                    importAllData(accountingSystem);
                    break;
                case "cat":
                    importCategoryData(accountingSystem.getSubCategories());
                    break;
                case "usr":
                    manageUserImports(accountingSystem.getPeople(), accountingSystem.getCompanies());
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void manageUserImports(List<Person> people, List<Company> companies) {
        String message[] = {"Choose action",
                "per - import people data",
                "com - import companies data",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "per":
                    importPeopleData(people);
                    break;
                case "com":
                    importCompaniesData(companies);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void importAllData(AccountingSystem accountingSystem) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allPeopleRead = false;
            while (!allPeopleRead) {
                try {
                    Object object = objectInputStream.readObject();
                    try {
                        PersonController.addReplacePerson((Person) object,
                                accountingSystem.getPeople());
                    } catch (ClassCastException e) {
                        CategoryController.addReplaceCategory((SubCategory) object,
                                accountingSystem.getSubCategories());
                    }
                } catch (EOFException e) {
                    allPeopleRead = true;
                }
            }

            boolean allCompaniesRead = false;
            while (!allCompaniesRead) {
                try {
                    CompanyController.addReplaceCompany((Company) objectInputStream.readObject(),
                            accountingSystem.getCompanies());
                } catch (EOFException e) {
                    allCompaniesRead = true;
                }
            }

            boolean allCategoriesRead = false;
            while (!allCategoriesRead) {
                try {
                    CategoryController.addReplaceCategory((SubCategory) objectInputStream.readObject(),
                            accountingSystem.getSubCategories());
                } catch (EOFException e) {
                    allCategoriesRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            importAllData(accountingSystem);
            return;
        }
    }

    public static void importCategoryData(List<SubCategory> categories) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allCategoriesRead = false;
            while (!allCategoriesRead) {
                try {
                    CategoryController.addReplaceCategory((SubCategory) objectInputStream.readObject(),
                            categories);
                } catch (EOFException e) {
                    allCategoriesRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            importCategoryData(categories);
            return;
        }
    }

    public static void importPeopleData(List<Person> people) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allPeopleRead = false;
            while (!allPeopleRead) {
                try {
                    PersonController.addReplacePerson((Person) objectInputStream.readObject(),
                            people);
                } catch (EOFException e) {
                    allPeopleRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            importPeopleData(people);
            return;
        }
    }

    public static void importCompaniesData(List<Company> companies) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allCompaniesRead = false;
            while (!allCompaniesRead) {
                try {
                    CompanyController.addReplaceCompany((Company) objectInputStream.readObject(),
                            companies);
                } catch (EOFException e) {
                    allCompaniesRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            importCompaniesData(companies);
            return;
        }
    }

}
