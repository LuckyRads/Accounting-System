package com.lucky.accountingsystem.service;

public class MenuService {

    public void showStartupScreen () {
        InputService input = new InputService();
        MessageService messages = new MessageService();

        String[] message = { "Choose an action", "1 - login", "2 - register" };
        messages.showMessage(message);

        String choice = input.getInput();

        if (choice.equals("1")) {
            LoginService loginService = new LoginService();
            loginService.login();
        } else if (choice.equals("2")) {
            RegisterService registerService = new RegisterService();
            registerService.register();
        }
    }

    public void showMenu() {
        MessageService messages = new MessageService();
        InputService input = new InputService();

        String[] message = { "Choose action", "1 - Categories" };
        messages.showMessage(message);
        String choice = input.getInput();

        // TODO: Add functionality from laboras 2
        if (choice.equals("1")) {
            messages.showMessage(new String [] { "show categories" });
        }
    }

}