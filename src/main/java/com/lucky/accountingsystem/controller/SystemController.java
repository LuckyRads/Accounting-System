package com.lucky.accountingsystem.controller;

import com.lucky.accountingsystem.model.AccountingSystem;
import com.lucky.accountingsystem.service.InputService;
import com.lucky.accountingsystem.service.MessageService;
import com.lucky.accountingsystem.util.DateUtil;

public class SystemController {

    public static void manageSystem(AccountingSystem accountingSystem) {
        String message[] = {"Choose an action",
                "show - show system info",
                "upd - update system info",
                "back - to go back up one menu level"};

        String choice = "";
        while (true) {
            MessageService.showMessage(message);
            choice = InputService.getInput();

            switch (choice) {
                case "show":
                    showSystemInfo(accountingSystem);
                    break;
                case "upd":
                    updateSystemInfo(accountingSystem);
                    break;
                case "back":
                    return;
            }
        }
    }

    public static void showSystemInfo(AccountingSystem accountingSystem) {
        String[] message = {"System info",
                "company: " + accountingSystem.getCompany(),
                "created at: " + accountingSystem.getDateCreated(),
                "version: " + accountingSystem.getVersion()
        };

        MessageService.showMessage(message);
    }

    public static void updateSystemInfo(AccountingSystem accountingSystem) {
        DateUtil dateUtil = new DateUtil();

        String[] message = {"Update system info",
                "Update info in format:",
                "company;dateCreated(dd/MM/yyyy);version",
        };

        MessageService.showMessage(message);

        String[] infoString = InputService.getInput().split(";");

        accountingSystem.setCompany(infoString[0]);
        accountingSystem.setDateCreated(dateUtil.parseDate(infoString[1]));
        accountingSystem.setVersion(infoString[2]);
    }

}