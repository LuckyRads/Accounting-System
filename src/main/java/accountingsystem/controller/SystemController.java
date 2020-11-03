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
    private Button updateSystemInfoBtn;

    @FXML
    private Button resetSystemInfoBtn;

    @FXML
    private TextField companyTextField;

    @FXML
    private TextField createdAtDatePicker;

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
        createdAtDatePicker.setText(this.accountingSystem.getDateCreated().toString());
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

    private void updateWindow() {
        Stage stage = (Stage) companyTextField.getScene().getWindow();
        stage.show();
    }

    @FXML
    public void updateSystemInfo() {
        accountingSystem.setCompany(companyTextField.getText());
//        accountingSystem.setDateCreated(createdAtDatePicker.getText().toString());
        accountingSystem.setVersion(versionTextField.getText());
        updateWindow();
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

        ViewService.newWindow(root, "Import");
    }

    //endregion

}