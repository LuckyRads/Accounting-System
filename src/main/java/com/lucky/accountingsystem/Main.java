package com.lucky.accountingsystem;

import com.lucky.accountingsystem.model.*;
import com.lucky.accountingsystem.service.AuthService;
import com.lucky.accountingsystem.service.MenuService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        List<User> users = new ArrayList<User>();
//        users.add(new Person("matas@mail.com", "password", "matas", "malickas", "+37061234567"));

        List<Person> people = new ArrayList<Person>();
        people.add(new Person("matas@mail.com", "password", "matas", "malickas", "+37061234567"));

        List<SubCategory> categories = new ArrayList<SubCategory>();
        categories.add(new SubCategory("cars", "cars are cool", new ArrayList<Expense>(), new ArrayList<SubCategory>()));

        // Program loop
        // TODO: Authentication
//        while (!AuthService.isLoggedIn()) {
//            MenuService.showStartupScreen();
//        }
        // Main menu loop
        while (true) {
            MenuService.showMenu(people, categories);
        }
    }

}
