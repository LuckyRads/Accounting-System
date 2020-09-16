package com.lucky.accountingsystem.service;

public class MessageService {

    public void showMessage(String[] message) {
        clearConsole();
        System.out.println("To quit the program type \"exit\"");
        for (String line : message) {
            System.out.println(line);
        }
    }

    public void showMessageNoCls(String[] message) {
        System.out.println("To quit the program type \"exit\"");
        for (String line : message) {
            System.out.println(line);
        }
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
