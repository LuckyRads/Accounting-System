package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.service.ViewService;

import java.io.IOException;

public class MainMenuController {

    private AccountingSystem accountingSystem;

    @FXML
    private Button systemBtn;

    @FXML
    private Button categoriesBtn;

    @FXML
    private Button usersBtn;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @FXML
    public void openSystem() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/System.fxml"));
        Parent root = loader.load();

        SystemController systemController = loader.getController();
        systemController.setAccountingSystem(accountingSystem);
        systemController.loadSystemInfo();

        ViewService.openView((Stage) systemBtn.getScene().getWindow(), root);
    }

    @FXML
    public void openCategories() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Categories.fxml"));
        Parent root = loader.load();

        CategoriesController categoriesController = loader.getController();
        categoriesController.setAccountingSystem(accountingSystem);
        categoriesController.loadCategories();

        ViewService.openView((Stage) categoriesBtn.getScene().getWindow(), root);
    }

    @FXML
    public void openUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Users.fxml"));
        Parent root = loader.load();

        UsersController users = loader.getController();
        users.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) usersBtn.getScene().getWindow(), root);
    }

}
