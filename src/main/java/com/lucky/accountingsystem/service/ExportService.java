package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.model.AccountingSystem;
import com.lucky.accountingsystem.model.Company;
import com.lucky.accountingsystem.model.Person;
import com.lucky.accountingsystem.model.SubCategory;

import java.io.*;
import java.util.List;

public class ExportService {

    public static void manageExports(AccountingSystem accountingSystem) {
        String message[] = {"Choose action",
                "all - export all available data",
                "cat - export all category data",
                "usr - user data export options",
                "sys - system data export options",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "all":
                    exportAllData(accountingSystem);
                    break;
                case "cat":
                    exportCategoryData(accountingSystem.getSubCategories());
                    break;
                case "usr":
                    manageUserExports(accountingSystem.getPeople(), accountingSystem.getCompanies());
                    break;
                case "sys":
                    exportSystemData(accountingSystem);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void manageUserExports(List<Person> people, List<Company> companies) {
        String message[] = {"Choose action",
                "per - export people data",
                "com - export companies data",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "per":
                    exportPeopleData(people);
                    break;
                case "com":
                    exportCompaniesData(companies);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void exportAllData(AccountingSystem accountingSystem) {
        MessageService.showMessage(new String[]{"Export data",
                "To save all data in current directory, type default",
                "To save elsewhere, please put in destination file path where to save the output file"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(accountingSystem);

            for (Person person : accountingSystem.getPeople()) {
                objectOutputStream.writeObject(person);
            }

            for (Company company : accountingSystem.getCompanies()) {
                objectOutputStream.writeObject(company);
            }

            for (SubCategory subCategory : accountingSystem.getSubCategories()) {
                objectOutputStream.writeObject(subCategory);
            }

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error! Something went wrong while exporting data." +
                    "Please check if the destination path is correct");
            exportAllData(accountingSystem);
            return;
        }
    }

    public static void exportCategoryData(List<SubCategory> categories) {
        MessageService.showMessage(new String[]{"Export data",
                "To save all data in current directory, type default",
                "To save elsewhere, please put in destination file path where to save the output file"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (SubCategory subCategory : categories) {
                objectOutputStream.writeObject(subCategory);
            }

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error! Something went wrong while exporting data." +
                    "Please check if the destination path is correct");
            exportCategoryData(categories);
            return;
        }
    }

    public static void exportPeopleData(List<Person> people) {
        MessageService.showMessage(new String[]{"Export data",
                "To save all data in current directory, type default",
                "To save elsewhere, please put in destination file path where to save the output file"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Person person : people) {
                objectOutputStream.writeObject(person);
            }

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error! Something went wrong while exporting data." +
                    "Please check if the destination path is correct");
            exportPeopleData(people);
            return;
        }
    }

    public static void exportCompaniesData(List<Company> companies) {
        MessageService.showMessage(new String[]{"Export data",
                "To save all data in current directory, type default",
                "To save elsewhere, please put in destination file path where to save the output file"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Company company : companies) {
                objectOutputStream.writeObject(company);
            }

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error! Something went wrong while exporting data." +
                    "Please check if the destination path is correct");
            exportCompaniesData(companies);
            return;
        }
    }

    public static void exportSystemData(AccountingSystem accountingSystem) {
        MessageService.showMessage(new String[]{"Export data",
                "To save all data in current directory, type default",
                "To save elsewhere, please put in destination file path where to save the output file"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(accountingSystem);

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error! Something went wrong while exporting data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            exportSystemData(accountingSystem);
            return;
        }
    }

}
