package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.service.ViewService;

import java.io.IOException;

public class UsersController implements Controller {

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

    @Override
    public void updateWindow() {

    }

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setAccountingSystem(accountingSystem);
        mainMenuController.loadSystemInfo();

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

    //region importAndExport

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
        importController.setController(this);

        ViewService.newWindow(root, "Import");
    }

    //endregion

}
