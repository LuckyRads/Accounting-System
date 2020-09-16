package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.exception.BadLoginException;

public class LoginService {

    public void login() {
        MessageService messages = new MessageService();
        InputService input = new InputService();
        AuthService authService = new AuthService();
        MenuService menu = new MenuService();

        messages.showMessage(new String[] { "Enter your email:" });
        String email = input.getInput();

        messages.showMessage(new String[] { "Enter your password:" });
        String password = input.getInput();

        try {
            authService.authenticate(email, password);
        } catch (BadLoginException e) {
            System.out.println(e.getMessage());
        }
        menu.showMenu();
    }

}
