package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.controller.CategoryController;
import com.lucky.accountingsystem.controller.UserController;
import com.lucky.accountingsystem.model.AccountingSystem;
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
     * @param accountingSystem
     */
    public static void showMenu(AccountingSystem accountingSystem) {
        String[] message = {"Choose action",
                "cat - Categories",
                "usr - Users",
                "ex - Export data",
                "im - Import data"};
        MessageService.showMessage(message);
        String choice = InputService.getInput();

        switch (choice) {
            case "cat":
                CategoryController.manageCategories(accountingSystem.getSubCategories(), accountingSystem.getPeople());
                break;
            case "usr":
                UserController.manageUsers(accountingSystem.getPeople(), accountingSystem.getCompanies());
                break;
            case "ex":
                ExportService.manageExports(accountingSystem);
                break;
            case "im":
                ImportService.manageImports(accountingSystem);
                break;
        }
    }

}