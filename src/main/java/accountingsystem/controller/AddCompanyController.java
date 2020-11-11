package accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import accountingsystem.model.Company;
import accountingsystem.model.Person;
import accountingsystem.service.PeopleService;

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

        for (Person person : companiesController.getAccountingSystem().getPeople()) {
            responsiblePersonSelect.getItems().add(person.getEmail());
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
        Person responsiblePerson = PeopleService.getPerson(responsiblePersonSelect.getSelectionModel().getSelectedItem().toString(), companiesController.getAccountingSystem().getPeople());
        companiesController.getAccountingSystem().getCompanies().add(new Company(emailField.getText(), passwordField.getText(), nameField.getText(),
                responsiblePerson));
        closeWindow();
    }

}
