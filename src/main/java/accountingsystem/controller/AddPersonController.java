package accountingsystem.controller;

import accountingsystem.hibernate.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPersonController implements WindowController {

    private PeopleController peopleController;

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

    public PeopleController getPeopleController() {
        return peopleController;
    }

    public void setPeopleController(PeopleController peopleController) {
        this.peopleController = peopleController;
    }

    @Override
    public void closeWindow() {
        peopleController.loadPeople();
        Stage stage = (Stage) addPersonBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addPerson() throws IOException {
        peopleController.personUtil.create(new Person(emailField.getText(), passwordField.getText(), nameField.getText(),
                surnameField.getText(), phoneNumberField.getText()));
        closeWindow();
    }

}
