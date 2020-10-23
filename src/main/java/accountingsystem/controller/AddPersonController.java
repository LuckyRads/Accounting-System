package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.service.ViewService;

import java.io.IOException;

public class AddPersonController {

    private AccountingSystem accountingSystem;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button addPersonBtn;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    public void addPerson() throws IOException {
        accountingSystem.getPeople().add(new Person(emailField.getText(), passwordField.getText(), nameField.getText(),
                surnameField.getText(),phoneNumberField.getText()));
        openPeople();

    }

    public void openPeople() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/People.fxml"));
        Parent root = loader.load();

        PeopleController peopleController = loader.getController();
        peopleController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) addPersonBtn.getScene().getWindow(), root);
        peopleController.loadPeople();
    }
}
