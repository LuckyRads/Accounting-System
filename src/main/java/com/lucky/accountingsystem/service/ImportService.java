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
                    importAllData();
                    break;
                case "cat":
                    importCategoryData(categories);
                    break;
                case "usr":
                    manageUserImports();
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void importAllData() {

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

    public static void manageUserImports() {

    }

}
