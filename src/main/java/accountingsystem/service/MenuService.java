package main.java.accountingsystem.service;

import main.java.accountingsystem.controller.CategoryController;
import main.java.accountingsystem.controller.SystemController;
import main.java.accountingsystem.controller.UserController;
import main.java.accountingsystem.model.AccountingSystem;

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
                "sys - System",
                "ex - Export data",
                "im - Import data"};
        MessageService.showMessage(message);
        String choice = InputService.getInput();

        switch (choice) {
            case "cat":
                CategoryController.manageCategories(accountingSystem.getCategories(), accountingSystem.getPeople());
                break;
            case "usr":
                UserController.manageUsers(accountingSystem.getPeople(), accountingSystem.getCompanies());
                break;
            case "sys":
//                SystemController.manageSystem(accountingSystem);
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