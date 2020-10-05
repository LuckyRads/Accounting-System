package com.lucky.accountingsystem.controller;

import com.lucky.accountingsystem.model.Transaction;
import com.lucky.accountingsystem.model.TransactionType;
import com.lucky.accountingsystem.service.InputService;
import com.lucky.accountingsystem.service.MessageService;
import com.lucky.accountingsystem.util.DateUtil;

import java.util.List;

public class TransactionController {

    public static void manageTransactions(List<Transaction> transactions) {
        String message[] = {"Choose an action",
                "show - show transactions",
                "cre - create transaction",
                "desc - describe transaction",
                "upd - update transaction",
                "rem - remove transaction",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "show":
                    showTransactions(transactions);
                    break;
                case "cre":
                    transactions.add(createTransaction(transactions));
                    break;
                case "desc":
                    describeTransaction(transactions);
                    break;
                case "upd":
                    updateTransaction(transactions);
                    break;
                case "rem":
                    removeTransaction(transactions);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void showTransactions(List<Transaction> transactions) {
        System.out.println("Transactions:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getName());
        }
    }

    public static Transaction createTransaction(List<Transaction> transactions) {
        DateUtil dateUtil = new DateUtil();

        System.out.println("Transaction creation");

        System.out.println("Enter name:");
        String name = InputService.getInput();

        System.out.println("Enter transaction type (EXPENSE/INCOME):");
        TransactionType transactionType = InputService.getInput().toUpperCase().equals("EXPENSE") ?
                TransactionType.EXPENSE : TransactionType.INCOME;

        System.out.println("Enter sender:");
        String sender = InputService.getInput();

        System.out.println("Enter receiver:");
        String receiver = InputService.getInput();

        System.out.println("Enter amount (use . for decimals):");
        String amount = InputService.getInput();

        System.out.println("Enter date (dd/MM/yyyy):");
        String date = InputService.getInput();

        return new Transaction(name, transactionType, sender, receiver, Double.parseDouble(amount), dateUtil.parseDate(date));
    }

    public static void describeTransaction(List<Transaction> transactions) {
        MessageService.showMessage(new String[]{"Choose which transaction's info you would like to see:"});

        showTransactions(transactions);

        String chosenTransaction = InputService.getInput();

        for (Transaction transaction : transactions) {
            if (chosenTransaction.equals(transaction.getName())) {
                describeTransaction(transaction);
            }
        }
    }

    public static void updateTransaction(List<Transaction> transactions) {
        MessageService.showMessage(new String[]{"Choose which transaction's info you would like to update:"});

        showTransactions(transactions);

        String chosenTransaction = InputService.getInput();

        for (Transaction transaction : transactions) {
            if (chosenTransaction.equals(transaction.getName())) {
                transactions.remove(transaction);
                transaction = updateTransaction(transaction);
                transactions.add(transaction);
            }
        }
    }

    public static void removeTransaction(List<Transaction> transactions) {
        MessageService.showMessage(new String[]{"Choose which transaction you would like to remove:"});

        showTransactions(transactions);

        String choseTransaction = InputService.getInput();

        for (Transaction transaction : transactions) {
            if (choseTransaction.equals(transaction.getName())) {
                transactions.remove(transaction);
                return;
            }
        }
    }

    private static void describeTransaction(Transaction transaction) {
        MessageService.showMessage(new String[]{"Transaction info",
                "name: " + transaction.getName(),
                "transaction type: " + transaction.getTransactionType(),
                "sender: " + transaction.getSender(),
                "receiver: " + transaction.getReceiver(),
                "amount: " + transaction.getAmount(),
                "date: " + transaction.getDate()});
    }

    private static Transaction updateTransaction(Transaction transaction) {
        DateUtil dateUtil = new DateUtil();

        MessageService.showMessage(new String[]{"Edit transaction's info",
                "Enter new transaction's info in format:",
                "name;transactionType;sender;receiver;amount;date"});

        String transactionInfo[] = InputService.getInput().split(";");

        return new Transaction(transactionInfo[0], transactionInfo[1].toUpperCase().equals("EXPENSE") ?
                TransactionType.EXPENSE : TransactionType.INCOME,
                transactionInfo[2], transactionInfo[3], Double.parseDouble(transactionInfo[4]),
                dateUtil.parseDate(transactionInfo[5]));
    }

}
