package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.exception.InvalidPasswordException;

import java.util.Scanner;

public class RegisterService {

    public static void register() {
        MessageService messageService = new MessageService();
        InputService inputService = new InputService();
        Scanner scanner = new Scanner(System.in);

        String[] messages = { "Register to the system", "Please enter your email:" };
        messageService.showMessage(messages);
        String email = inputService.getInput();

        boolean passwordIsValid = false;
        while (passwordIsValid != true) {
            passwordIsValid = enterPassword();
        }

        // TODO: Add user to the database

        System.out.println("You have successfully registered with email: " + email);
    }

    private static boolean enterPassword() {
        InputService inputService = new InputService();
        MessageService  messageService = new MessageService();
        PasswordService passwordService = new PasswordService();

        messageService.showMessage(new String[] { "Please enter your password:" });
        String password = inputService.getInput();

        messageService.showMessage(new String[] { "Please confirm your password" });
        String confirmPassword = inputService.getInput();

        try {
            passwordService.isValid(password, confirmPassword);
        } catch (InvalidPasswordException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
