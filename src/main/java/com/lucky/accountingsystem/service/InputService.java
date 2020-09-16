package com.lucky.accountingsystem.service;

import java.util.Scanner;

public class InputService {

    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if (input.equals("exit")) {
            System.out.println("You have closed the program.");
            System.exit(0);
        }
        return input;
    }

}
