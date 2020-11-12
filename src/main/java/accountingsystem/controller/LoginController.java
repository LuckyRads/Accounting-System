package accountingsystem.controller;

import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.CompanyUtil;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.ViewService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerPersonBtn;

    @FXML
    private Button registerCompanyBtn;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    CompanyUtil companyUtil = new CompanyUtil(entityManagerFactory);
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);

    @FXML
    public void login() throws IOException {
        try {
            if (validateUser()) openMainMenu();
            else throw new Exception();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong credentials!");
            alert.showAndWait();
        }
    }

    @FXML
    public void registerPerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterPerson.fxml"));
        Parent root = loader.load();

        RegisterPersonController registerPersonController = loader.getController();

        ViewService.openView((Stage) registerPersonBtn.getScene().getWindow(), root);
    }

    @FXML
    public void registerCompany() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterCompany.fxml"));
        Parent root = loader.load();

        RegisterCompanyController registerCompanyController = loader.getController();
        registerCompanyController.populateResponsiblePeopleSelect();

        ViewService.openView((Stage) registerCompanyBtn.getScene().getWindow(), root);
    }

    private void openMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.loadSystemInfo();

        ViewService.openView((Stage) loginBtn.getScene().getWindow(), root);
    }

    private boolean validateUser() {
        for (Person person : personUtil.getAllPeople()) {
            if (person.getEmail().equals(emailField.getText()) && person.getPassword().equals(passwordField.getText())) {
                return true;
            }
        }
        for (Company company : companyUtil.getAllCompanies()) {
            if (company.getEmail().equals(emailField.getText()) && company.getPassword().equals(passwordField.getText())) {
                return true;
            }
        }
        return false;
    }

}
