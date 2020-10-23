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
    private Button menuBtn;

    @FXML
    private TextField companyTextField;

    @FXML
    private TextField createdAtTextField;

    @FXML
    private TextField versionTextField;

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

        ViewService.openView((Stage) menuBtn.getScene().getWindow(), root);
    }

}