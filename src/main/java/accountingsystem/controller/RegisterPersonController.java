package accountingsystem.controller;

import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.ViewService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class RegisterPersonController {

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
    private TextField surnameField;

    @FXML
    private TextField phoneNumberField;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
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
        Person person = new Person(emailField.getText(), passwordField.getText(),
                nameField.getText(), surnameField.getText(), phoneNumberField.getText());

        personUtil.create(person);
        openLogin();
    }

}
