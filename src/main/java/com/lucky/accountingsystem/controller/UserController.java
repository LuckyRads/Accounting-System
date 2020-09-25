package com.lucky.accountingsystem.controller;

import com.lucky.accountingsystem.model.Company;
import com.lucky.accountingsystem.model.Person;
import com.lucky.accountingsystem.service.InputService;
import com.lucky.accountingsystem.service.MessageService;

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
