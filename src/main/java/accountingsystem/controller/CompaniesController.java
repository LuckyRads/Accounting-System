package accountingsystem.controller;

import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.CompanyUtil;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.AlertService;
import accountingsystem.service.ViewService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class CompaniesController implements Controller {

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

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    CompanyUtil companyUtil = new CompanyUtil(entityManagerFactory);
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);

    //region Menu links

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.loadSystemInfo();

        ViewService.openView((Stage) menuBtn.getScene().getWindow(), root);
    }

    @FXML
    public void openUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Users.fxml"));
        Parent root = loader.load();

        UsersController usersController = loader.getController();

        ViewService.openView((Stage) usersBtn.getScene().getWindow(), root);
        usersController.updateWindow();
    }

    //endregion

    @FXML
    public void loadCompanies() {
        companiesList.getItems().clear();
        for (Company company : companyUtil.getAllCompanies()) {
            companiesList.getItems().add(company);
        }
        updateWindow();
    }

    @FXML
    public void loadCompany() {
        if (companiesList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Company company = (Company) companiesList.getSelectionModel().getSelectedItem();

        populateResponsiblePeopleSelect();

        emailField.setText(company.getEmail());
        passwordField.setText(company.getPassword());
        nameField.setText(company.getName());
        responsiblePersonSelect.getSelectionModel().select(company.getResponsiblePerson());
        updateWindow();
    }

    @Override
    public void updateWindow() {
        Stage stage = (Stage) companiesList.getScene().getWindow();
        stage.show();
    }

    private void populateResponsiblePeopleSelect() {
        responsiblePersonSelect.getItems().clear();

        for (Person person : personUtil.getAllPeople()) {
            responsiblePersonSelect.getItems().add(person);
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
    public void removeCompany() throws Exception {
        if (companiesList.getSelectionModel().getSelectedItem() == null) {
            AlertService.showError("Please select a company to remove.");
            return;
        }
        Company company = (Company) companiesList.getSelectionModel().getSelectedItem();
        companyUtil.destroy(company);
        loadCompanies();
    }

    @FXML
    public void updateCompany() {
        if (companiesList.getSelectionModel().getSelectedItem() == null) {
            AlertService.showError("Please select a company to update.");
            return;
        }

        Company company = (Company) companiesList.getSelectionModel().getSelectedItem();

        Person responsiblePerson = (Person) responsiblePersonSelect.getSelectionModel().getSelectedItem();

        company.setEmail(emailField.getText());
        company.setPassword(passwordField.getText());
        company.setName(nameField.getText());
        company.setResponsiblePerson(responsiblePerson);

        companyUtil.edit(company);
    }

    //region Import and export

    @FXML
    public void openExport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Export.fxml"));
        Parent root = loader.load();

        ExportController exportController = loader.getController();
        exportController.populateDataTypes();

        ViewService.newWindow(root, "Export");
    }

    @FXML
    public void openImport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Import.fxml"));
        Parent root = loader.load();

        ImportController importController = loader.getController();
        importController.populateDataTypes();
        importController.setController(this);

        ViewService.newWindow(root, "Import");
    }

    //endregion

}
