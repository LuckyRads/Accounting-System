package main.java.accountingsystem.controller;

import main.java.accountingsystem.model.Company;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.service.InputService;
import main.java.accountingsystem.service.MessageService;

import java.util.List;

public class UserController {

    public static void manageUsers(List<Person> people, List<Company> companies) {
        String message[] = {"Choose an action",
                "per - persons",
                "com - companies",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "per":
                    PersonController.managePeople(people);
                    break;
                case "com":
                    CompanyController.manageCompanies(companies, people);
                    break;
                case "back":
                    return;
            }
        }
    }

}
