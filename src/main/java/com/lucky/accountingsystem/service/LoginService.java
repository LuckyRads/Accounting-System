package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.exception.BadLoginException;

public class LoginService {

    public static void login() {
        MessageService messageService = new MessageService();
        InputService inputService = new InputService();
        AuthService authService = new AuthService();
        MenuService menuService = new MenuService();

        messageService.showMessage(new String[] { "Enter your email:" });
        String email = inputService.getInput();

        messageService.showMessage(new String[] { "Enter your password:" });
        String password = inputService.getInput();

        try {
            AuthService.authenticate(email, password);
        } catch (BadLoginException e) {
            System.out.println(e.getMessage());
        }
    }

}
