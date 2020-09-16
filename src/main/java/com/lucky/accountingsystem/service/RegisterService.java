package com.lucky.accountingsystem.service;

import java.util.Scanner;

public class LoginService {

    public boolean register() {
        Scanner scanner = new Scanner(System.in);
        PasswordService passwordService = new PasswordService();

        System.out.println("Register to the system");
        System.out.println("Please enter your email:");
        String email = scanner.next();

        System.out.println("Please enter your password:");
        String password = scanner.next();

        System.out.println("Please confirm your password");
        String confirmPassword = scanner.next();

        try {
            if (passwordService.isValid(password, confirmPassword)) {
                System.out.println("You have registered successfully.");
            }
        }


    }

}
