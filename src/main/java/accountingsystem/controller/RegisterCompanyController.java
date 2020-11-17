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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class RegisterCompanyController {

    @FXML
    private Button registerBtn;

    @FXML
    private Button loginBtn;

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

    @FXML
    public void openLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();

        ViewService.openView((Stage) loginBtn.getScene().getWindow(), root);
    }

    @FXML
    public void register() throws IOException {
        if (emailField.getText().isEmpty() || passwordField.getText().isEmpty() || nameField.getText().isEmpty() ||
                responsiblePersonSelect.getItems() == null) {
            AlertService.showError("Please fill out all fields correctly!");
            return;
        }

        Company company = new Company(emailField.getText(), passwordField.getText(),
                nameField.getText(), (Person) responsiblePersonSelect.getSelectionModel().getSelectedItem());

        companyUtil.create(company);
        openLogin();
    }

    public void populateResponsiblePeopleSelect() {
        responsiblePersonSelect.getItems().clear();

        for (Person person : personUtil.getAllPeople()) {
            responsiblePersonSelect.getItems().add(person);
        }
    }

}
