package com.lucky.accountingsystem.controller;

import com.lucky.accountingsystem.model.Expense;
import com.lucky.accountingsystem.model.MoneyTransaction;
import com.lucky.accountingsystem.service.InputService;
import com.lucky.accountingsystem.service.MessageService;
import com.lucky.accountingsystem.util.DateUtil;

import java.util.Date;
import java.util.List;

public class ExpenseController {

    public static void manageExpenses(List<Expense> expenses) {
        String message[] = {"Choose an action",
                "show - show expenses",
                "cre - create expense",
                "desc - describe expense",
                "upd - update expense",
                "rem - remove expense",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "show":
                    showExpenses(expenses);
                    break;
                case "cre":
                    expenses.add(createExpense(expenses));
                    break;
                case "desc":
                    describeExpense(expenses);
                    break;
                case "upd":
                    updateExpense(expenses);
                    break;
                case "rem":
                    removeExpense(expenses);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void showExpenses(List<Expense> expenses) {
        System.out.println("Expenses:");
        for (Expense expense : expenses) {
            System.out.println(expense.getName());
        }
    }

    public static Expense createExpense(List<Expense> expenses) {
        DateUtil dateUtil = new DateUtil();

        System.out.println("Expense creation");

        System.out.println("Enter name:");
        String name = InputService.getInput();

        System.out.println("Enter sender:");
        String sender = InputService.getInput();

        System.out.println("Enter receiver:");
        String receiver = InputService.getInput();

        System.out.println("Enter amount (use . for decimals):");
        String amount = InputService.getInput();

        System.out.println("Enter date (dd/MM/yyyy):");
        String date = InputService.getInput();

        return new Expense(name, new MoneyTransaction(sender, receiver, Double.parseDouble(amount), dateUtil.parseDate(date)));
    }

    public static void describeExpense(List<Expense> expenses) {
        MessageService.showMessage(new String[]{"Choose which expense's info you would like to see:"});

        showExpenses(expenses);

        String chosenExpense = InputService.getInput();

        for (Expense expense : expenses) {
            if (chosenExpense.equals(expense.getName())) {
                describeExpense(expense);
            }
        }
    }

    public static void updateExpense(List<Expense> expenses) {
        MessageService.showMessage(new String[]{"Choose which expense's info you would like to update:"});

        showExpenses(expenses);

        String chosenExpense = InputService.getInput();

        for (Expense expense : expenses) {
            if (chosenExpense.equals(expense.getName())) {
                expenses.remove(expense);
                expense = updateExpense(expense);
                expenses.add(expense);
            }
        }
    }

    public static void removeExpense(List<Expense> expenses) {
        MessageService.showMessage(new String[]{"Choose which expense you would like to remove:"});

        showExpenses(expenses);

        String choseExpense = InputService.getInput();

        for (Expense expense : expenses) {
            if (choseExpense.equals(expense.getName())) {
                expenses.remove(expense);
                return;
            }
        }
    }

    private static void describeExpense(Expense expense) {
        MessageService.showMessage(new String[]{"Expense info",
                "name: " + expense.getName(),
                "sender: " + expense.getMoneyTransaction().getSender(),
                "receiver: " + expense.getMoneyTransaction().getReceiver(),
                "amount: " + expense.getMoneyTransaction().getAmount(),
                "date: " + expense.getMoneyTransaction().getDate()});
    }

    private static Expense updateExpense(Expense expense) {
        DateUtil dateUtil = new DateUtil();

        MessageService.showMessage(new String[]{"Edit epense's info",
                "Enter new expense's info in format:",
                "name;sender;receiver;amount;date"});

        String expenseInfo[] = InputService.getInput().split(";");

        return new Expense(expenseInfo[0], new MoneyTransaction(expenseInfo[1], expenseInfo[2], Double.parseDouble(expenseInfo[3]), dateUtil.parseDate(expenseInfo[4])));
    }

}
