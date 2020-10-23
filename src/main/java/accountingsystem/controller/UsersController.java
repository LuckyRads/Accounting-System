package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.service.ViewService;

import java.io.IOException;

public class UsersController {

    private AccountingSystem accountingSystem;

    @FXML
    private Button menuBtn;

    @FXML
    private Button peopleBtn;

    @FXML
    private Button companiesBtn;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) menuBtn.getScene().getWindow(), root);
    }

    @FXML
    public void openPeople() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/People.fxml"));
        Parent root = loader.load();

        PeopleController peopleController = loader.getController();
        peopleController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) peopleBtn.getScene().getWindow(), root);
        peopleController.loadPeople();
    }

    @FXML
    public void openCompanies() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Companies.fxml"));
        Parent root = loader.load();

        CompaniesController companiesController = loader.getController();
        companiesController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) companiesBtn.getScene().getWindow(), root);
        companiesController.loadCompanies();
    }

}
