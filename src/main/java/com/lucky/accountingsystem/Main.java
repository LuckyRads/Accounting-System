package com.lucky.accountingsystem;

import com.lucky.accountingsystem.model.*;
import com.lucky.accountingsystem.service.MenuService;

import java.util.ArrayList;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 5);

        AccountingSystem accountingSystem = new AccountingSystem(
                "VGTU",
                calendar.getTime(),
                "1.0.0",
                new ArrayList<SubCategory>(),
                new ArrayList<Person>(),
                new ArrayList<Company>()
        );

//         Program loop
        // TODO: Authentication
//        while (!AuthService.isLoggedIn()) {
//            MenuService.showStartupScreen();
//        }
        // Main menu loop
        while (true) {
            MenuService.showMenu(accountingSystem);
        }
    }

}
