package accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import accountingsystem.model.AccountingSystem;
import accountingsystem.model.Company;
import accountingsystem.model.Person;
import accountingsystem.service.PeopleService;
import accountingsystem.service.ViewService;

import java.io.IOException;

public class CompaniesController implements Controller {

    private AccountingSystem accountingSystem;

    @FXML
    private ListView companiesList;

    @FXML
    private Button menuBtn;

    @FXML
    private Button usersBtn;

    @FXML
    private Button addCompanyBtn;

    @FXML
    private Button removeCompanyBtn;

    @FXML
    private Button updateCompanyBtn;

    @FXML
    private Button resetCompanyBtn;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox responsiblePersonSelect;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
//        mainMenuController.setAccountingSystem(accountingSystem);
        mainMenuController.loadSystemInfo();

        ViewService.openView((Stage) menuBtn.getScene().getWindow(), root);
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
    public void loadCompanies() {
        companiesList.getItems().clear();
        for (Company company : accountingSystem.getCompanies()) {
            companiesList.getItems().add(company.getEmail());
        }
        updateWindow();
    }

    @FXML
    public void loadCompany() {
        if (companiesList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String selectedCompany = companiesList.getSelectionModel().getSelectedItem().toString();

        populateResponsiblePeopleSelect();

        for (Company company : accountingSystem.getCompanies()) {
            if (selectedCompany.equals(company.getEmail())) {
                emailField.setText(company.getEmail());
                passwordField.setText(company.getPassword());
                nameField.setText(company.getName());
                responsiblePersonSelect.getSelectionModel().select(company.getResponsiblePerson().getEmail());
                updateWindow();
                return;
            }
        }
    }

    @Override
    public void updateWindow() {
        Stage stage = (Stage) companiesList.getScene().getWindow();
        stage.show();
    }

    private void populateResponsiblePeopleSelect() {
        responsiblePersonSelect.getItems().clear();

        for (Person person : accountingSystem.getPeople()) {
            responsiblePersonSelect.getItems().add(person.getEmail());
        }
    }

    @FXML
    public void addCompany() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCompany.fxml"));
        Parent root = loader.load();

        AddCompanyController addCompanyController = loader.getController();
        addCompanyController.setCompaniesController(this);
        addCompanyController.populateResponsiblePeopleSelect();

        ViewService.newWindow(root, "Add company");
    }

    @FXML
    public void removeCompany() {
        String selectedCompany = companiesList.getSelectionModel().getSelectedItem().toString();

        for (Company company : accountingSystem.getCompanies()) {
            if (selectedCompany.equals(company.getEmail())) {
                accountingSystem.getCompanies().remove(company);
                loadCompanies();
                return;
            }
        }
    }

    @FXML
    public void updateCompany() {
        String selectedCompany = companiesList.getSelectionModel().getSelectedItem().toString();

        Person responsiblePerson = PeopleService.getPerson(responsiblePersonSelect.getSelectionModel().getSelectedItem().toString(), accountingSystem.getPeople());

        for (Company company : accountingSystem.getCompanies()) {
            if (selectedCompany.equals(company.getEmail())) {
                accountingSystem.getCompanies().remove(company);
                accountingSystem.getCompanies().add(new Company(emailField.getText(), passwordField.getText(), nameField.getText(), responsiblePerson));
                loadCompanies();
                return;
            }
        }
    }

    //region importAndExport

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

    //endregion

}
