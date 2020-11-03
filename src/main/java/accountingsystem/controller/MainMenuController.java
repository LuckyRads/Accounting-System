package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
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

    @FXML
    private MenuItem exportMenuItem;

    @FXML
    private MenuItem importMenuItem;

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

        ViewService.openView((Stage) systemBtn.getScene().getWindow(), root);
        systemController.loadSystemInfo();
    }

    @FXML
    public void openCategories() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Categories.fxml"));
        Parent root = loader.load();

        CategoriesController categoriesController = loader.getController();
        categoriesController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) categoriesBtn.getScene().getWindow(), root);
        categoriesController.loadCategories();
    }

    @FXML
    public void openUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Users.fxml"));
        Parent root = loader.load();

        UsersController usersController = loader.getController();
        usersController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) usersBtn.getScene().getWindow(), root);
    }

    @FXML
    public void openExport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Export.fxml"));
        Parent root = loader.load();

        ExportController exportController = loader.getController();
        exportController.setAccountingSystem(accountingSystem);
        exportController.populateDataTypes();

        ViewService.newWindow(root, "Export");
    }

    @FXML
    public void openImport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Import.fxml"));
        Parent root = loader.load();

        ImportController importController = loader.getController();
        importController.setAccountingSystem(accountingSystem);
        importController.populateDataTypes();

        ViewService.newWindow(root, "Import");
    }

}
