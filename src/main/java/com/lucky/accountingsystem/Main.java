package com.lucky.accountingsystem;

import com.lucky.accountingsystem.service.AuthService;
import com.lucky.accountingsystem.service.MenuService;

public class Main {

    public static void main(String[] args) {
        MenuService menu = new MenuService();
        AuthService authService = new AuthService();
        while (!authService.isLoggedIn()) {
            menu.showStartupScreen();
        }
    }

}
