package com.lucky.accountingsystem;

import com.lucky.accountingsystem.model.*;
import com.lucky.accountingsystem.service.AuthService;
import com.lucky.accountingsystem.service.MenuService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Person> people = new ArrayList<Person>();
        List<Company> companies = new ArrayList<Company>();
        List<SubCategory> categories = new ArrayList<SubCategory>();
        
//        categories.add(new SubCategory("cars", "cars are cool", new ArrayList<Expense>(), new ArrayList<SubCategory>()));
//       people.add(new Person("matas@mail.com", "password", "matas", "malickas", "+37061234567"));

//         Program loop
        // TODO: Authentication
//        while (!AuthService.isLoggedIn()) {
//            MenuService.showStartupScreen();
//        }
        // Main menu loop
        while (true) {
            MenuService.showMenu(people, companies, categories);
        }
    }

}
