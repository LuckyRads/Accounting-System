package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.model.Company;
import com.lucky.accountingsystem.model.Person;
import com.lucky.accountingsystem.model.SubCategory;

import java.io.*;
import java.util.List;

public class ImportService {

    public static void manageImports(List<Person> people, List<Company> companies, List<SubCategory> categories) {
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
                    importAllData(people, companies, categories);
                    break;
                case "cat":
                    importCategoryData(categories);
                    break;
                case "usr":
                    manageUserImports(people, companies);
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

    public static void importAllData(List<Person> people, List<Company> companies, List<SubCategory> categories) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            categories.add((SubCategory) objectInputStream.readObject());
            people.add((Person) objectInputStream.readObject());
            companies.add((Company) objectInputStream.readObject());

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

    public static void importCategoryData(List<SubCategory> categories) {
        MessageService.showMessage(new String[]{"Import data",
                "To import all data from current directory, type default",
                "Please put in destination file path of the file which contains all serialized data"});

        String choice = InputService.getInput();

        String file = choice.equals("default") ? "output.ser" : choice;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            categories.add((SubCategory) objectInputStream.readObject());

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

            people.add((Person) objectInputStream.readObject());

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

            companies.add((Company) objectInputStream.readObject());

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
