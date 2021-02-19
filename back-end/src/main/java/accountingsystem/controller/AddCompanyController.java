package accountingsystem.controller;

import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import accountingsystem.service.AlertService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCompanyController implements WindowController {

    private CompaniesController companiesController;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox responsiblePersonSelect;

    @FXML
    private Button addCompanyBtn;

    public CompaniesController getCompaniesController() {
        return companiesController;
    }

    public void setCompaniesController(CompaniesController companiesController) {
        this.companiesController = companiesController;
    }

    public void populateResponsiblePeopleSelect() {
        responsiblePersonSelect.getItems().clear();

        for (Person person : companiesController.personUtil.getAllPeople()) {
            responsiblePersonSelect.getItems().add(person);
        }
    }

    @Override
    public void closeWindow() {
        companiesController.loadCompanies();
        Stage stage = (Stage) addCompanyBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addCompany() throws IOException {
        if (emailField.getText().isEmpty() || passwordField.getText().isEmpty() || nameField.getText().isEmpty() ||
                responsiblePersonSelect.getItems() == null) {
            AlertService.showError("Please fill out all fields correctly.");
            return;
        }

        companiesController.companyUtil.create(new Company(emailField.getText(), passwordField.getText(), nameField.getText(),
                (Person) responsiblePersonSelect.getSelectionModel().getSelectedItem()));
        closeWindow();
    }

}
