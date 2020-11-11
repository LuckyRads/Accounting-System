package accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import accountingsystem.model.AccountingSystem;
import accountingsystem.service.ViewService;

import java.io.IOException;

public class MainMenuController implements Controller {

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

    @Override
    public void updateWindow() {
        loadSystemInfo();
        Stage stage = (Stage) companyTextField.getScene().getWindow();
        stage.show();
    }

    @FXML
    public void openCategories() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Categories.fxml"));
        Parent root = loader.load();

        CategoriesController categoriesController = loader.getController();
        categoriesController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) categoriesBtn.getScene().getWindow(), root);
        categoriesController.loadCategories();
    }

    @FXML
    public void openUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Users.fxml"));
        Parent root = loader.load();

        UsersController usersController = loader.getController();
        usersController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) usersBtn.getScene().getWindow(), root);
        usersController.updateWindow();
    }

    @FXML
    public void openExport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Export.fxml"));
        Parent root = loader.load();

        ExportController exportController = loader.getController();
        exportController.setAccountingSystem(accountingSystem);
        exportController.populateDataTypes();

        ViewService.newWindow(root, "Export");
    }

    @FXML
    public void openImport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Import.fxml"));
        Parent root = loader.load();

        ImportController importController = loader.getController();
        importController.setAccountingSystem(accountingSystem);
        importController.populateDataTypes();
        importController.setController(this);

        ViewService.newWindow(root, "Import");
    }

    public void loadSystemInfo() {
        companyTextField.setText(this.accountingSystem.getCompany());
        createdAtDatePicker.setText(this.accountingSystem.getDateCreated().toString());
        versionTextField.setText(this.accountingSystem.getVersion());
    }

    @FXML
    public void updateSystemInfo() {
        accountingSystem.setCompany(companyTextField.getText());
//        accountingSystem.setDateCreated(createdAtDatePicker.getText().toString());
        accountingSystem.setVersion(versionTextField.getText());
        updateWindow();
    }

}
