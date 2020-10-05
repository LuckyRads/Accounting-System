package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.controller.CategoryController;
import com.lucky.accountingsystem.controller.UserController;
import com.lucky.accountingsystem.model.Company;
import com.lucky.accountingsystem.model.Person;
import com.lucky.accountingsystem.model.SubCategory;

import java.util.List;

public class MenuService {

    /**
     * Shows startup login screen
     */
    public static void showStartupScreen() {
        InputService inputService = new InputService();

        String[] message = {"Choose an action",
                "log - login",
                "reg - register"};
        MessageService.showMessage(message);

        String choice = inputService.getInput();

        switch (choice) {
            case "log":
                LoginService.login();
                break;
            case "reg":
                RegisterService.register();
                break;
        }
    }


    /**
     * Shows the main program menu
     *
     * @param people
     * @param categories
     */
    public static void showMenu(List<Person> people, List<Company> companies, List<SubCategory> categories) {
        String[] message = {"Choose action",
                "cat - Categories",
                "usr - Users",
                "ex - Export data",
                "im - Import data"};
        MessageService.showMessage(message);
        String choice = InputService.getInput();

        switch (choice) {
            case "cat":
                CategoryController.manageCategories(categories, people);
                break;
            case "usr":
                UserController.manageUsers(people, companies);
                break;
            case "ex":
                ExportService.manageExports(people, companies, categories);
                break;
            case "im":
                ImportService.manageImports(people, companies, categories);
                break;
        }
    }

}