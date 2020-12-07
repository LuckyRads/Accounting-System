package accountingsystem.controller;

import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.AlertService;
import accountingsystem.service.ViewService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class PeopleController implements Controller {

    @FXML
    private ListView peopleList;

    @FXML
    private Button menuBtn;

    @FXML
    private Button usersBtn;

    @FXML
    private Button addPersonBtn;

    @FXML
    private Button removePersonBtn;

    @FXML
    private Button updatePersonBtn;

    @FXML
    private Button resetPersonBtn;

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
    public void loadPeople() {
        peopleList.getItems().clear();

        for (Person person : personUtil.getAllPeople()) {
            peopleList.getItems().add(person);
        }
        updateWindow();
    }

    @FXML
    public void loadPerson() {
        if (peopleList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        Person person = (Person) peopleList.getSelectionModel().getSelectedItem();
        emailField.setText(person.getEmail());
        passwordField.setText(person.getPassword());
        nameField.setText(person.getName());
        surnameField.setText(person.getSurname());
        phoneNumberField.setText(person.getPhoneNumber());
        updateWindow();
    }

    @Override
    public void updateWindow() {
        Stage stage = (Stage) peopleList.getScene().getWindow();
        stage.show();
    }

    @FXML
    public void addPerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddPerson.fxml"));
        Parent root = loader.load();

        AddPersonController addPersonController = loader.getController();
        addPersonController.setPeopleController(this);

        ViewService.newWindow(root, "Add person");
    }

    @FXML
    public void removePerson() throws Exception {
        if (peopleList.getSelectionModel().getSelectedItem() == null) {
            AlertService.showError("Please select a person to remove.");
            return;
        }
        Person person = (Person) peopleList.getSelectionModel().getSelectedItem();
        personUtil.destroy(person);
        loadPeople();
    }

    @FXML
    public void updatePerson() {
        if (peopleList.getSelectionModel().getSelectedItem() == null) {
            AlertService.showError("Please select a person to update.");
            return;
        }
        Person person = (Person) peopleList.getSelectionModel().getSelectedItem();
        person.setEmail(emailField.getText());
        person.setPassword(passwordField.getText());
        person.setName(nameField.getText());
        person.setSurname(surnameField.getText());
        person.setPhoneNumber(phoneNumberField.getText());
        personUtil.edit(person);
    }

    //region importAndExport

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
