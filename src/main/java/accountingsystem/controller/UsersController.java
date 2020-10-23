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
    private Button backBtn;

    @FXML
    private Button peopleBtn;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }



    public void loadUsers() {
        //
    }

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) backBtn.getScene().getWindow(), root);
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

}
