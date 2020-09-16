package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.exception.InvalidPasswordException;

import java.util.Scanner;

public class RegisterService {

    public void register() {
        MessageService messageService = new MessageService();
        InputService input = new InputService();
        Scanner scanner = new Scanner(System.in);

        String[] messages = { "Register to the system", "Please enter your email:" };
        messageService.showMessage(messages);
        String email = input.getInput();

        boolean passwordIsValid = false;
        while (passwordIsValid != true) {
            passwordIsValid = enterPassword();
        }

        // TODO: Add user to the database

        System.out.println("You have successfully registered with email: " + email);
    }

    private boolean enterPassword() {
        InputService input = new InputService();
        MessageService  messages = new MessageService();
        PasswordService passwordService = new PasswordService();

        messages.showMessage(new String[] { "Please enter your password:" });
        String password = input.getInput();

        messages.showMessage(new String[] { "Please confirm your password" });
        String confirmPassword = input.getInput();

        try {
            passwordService.isValid(password, confirmPassword);
        } catch (InvalidPasswordException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
