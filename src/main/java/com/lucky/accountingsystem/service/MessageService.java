package com.lucky.accountingsystem.service;

public class MessageService {

    public static void showMessage(String[] message) {
        clearConsole();
        System.out.println("To quit the program type \"exit\"");
        for (String line : message) {
            System.out.println("\t" + line);
        }
    }

    public static void showMessageNoCls(String[] message) {
        System.out.println("To quit the program type \"exit\"");
        for (String line : message) {
            System.out.println("\t" + line);
        }
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
