package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.service.ViewService;

import java.io.IOException;

public class SystemController {

    private AccountingSystem accountingSystem;

    @FXML
    private Button backBtn;

    @FXML
    private TextField companyTextField;

    @FXML
    private TextField createdAtTextField;

    @FXML
    private TextField versionTextField;

//    public static void manageSystem(AccountingSystem accountingSystem) {
//        String message[] = {"Choose an action",
//                "show - show system info",
//                "upd - update system info",
//                "back - to go back up one menu level"};
//
//        String choice = "";
//        while (true) {
//            MessageService.showMessage(message);
//            choice = InputService.getInput();
//
//            switch (choice) {
//                case "show":
//                    showSystemInfo(accountingSystem);
//                    break;
//                case "upd":
//                    updateSystemInfo(accountingSystem);
//                    break;
//                case "back":
//                    return;
//            }
//        }
//    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    public void loadSystemInfo() {
        companyTextField.setText(this.accountingSystem.getCompany());
        createdAtTextField.setText(this.accountingSystem.getDateCreated().toString());
        versionTextField.setText(this.accountingSystem.getVersion());
    }

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) backBtn.getScene().getWindow(), root);
    }

//    public static void updateSystemInfo(AccountingSystem accountingSystem) {
//        DateUtil dateUtil = new DateUtil();
//
//        String[] message = {"Update system info",
//                "Update info in format:",
//                "company;dateCreated(dd/MM/yyyy);version",
//        };
//
//        MessageService.showMessage(message);
//
//        String[] infoString = InputService.getInput().split(";");
//
//        accountingSystem.setCompany(infoString[0]);
//        accountingSystem.setDateCreated(dateUtil.parseDate(infoString[1]));
//        accountingSystem.setVersion(infoString[2]);
//    }

}