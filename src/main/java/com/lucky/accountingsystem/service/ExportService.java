package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.model.Category;
import com.lucky.accountingsystem.model.Company;
import com.lucky.accountingsystem.model.Person;
import com.lucky.accountingsystem.model.SubCategory;

import java.io.*;
import java.util.List;

public class ExportService {

    public static void manageExports(List<Person> people, List<Company> companies, List<SubCategory> categories) {
        String message[] = {"Choose action",
                "all - export all available data",
                "cat - export all category data",
                "usr - user data export options",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "all":
                    exportAllData();
                    break;
                case "cat":
                    exportCategoryData(categories);
                    break;
                case "usr":
                    manageUserExports();
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void exportAllData() {

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
            objectOutputStream.writeObject(categories);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error! Something went wrong while exporting data." +
                    "Please check if the destination path is correct");
            exportCategoryData(categories);
            return;
        }
    }

    public static void manageUserExports() {

    }

}
