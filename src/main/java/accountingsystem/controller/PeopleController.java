package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.service.ViewService;

import java.io.IOException;

public class PeopleController implements Controller {

    private AccountingSystem accountingSystem;

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

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setAccountingSystem(accountingSystem);
        mainMenuController.loadSystemInfo();

        ViewService.openView((Stage) menuBtn.getScene().getWindow(), root);
    }

    @FXML
    public void openUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Users.fxml"));
        Parent root = loader.load();

        UsersController usersController = loader.getController();
        usersController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) usersBtn.getScene().getWindow(), root);
    }

    @FXML
    public void loadPeople() {
        peopleList.getItems().clear();

        for (Person person : accountingSystem.getPeople()) {
            peopleList.getItems().add(person.getEmail());
        }
        updateWindow();
    }

    @FXML
    public void loadPerson() {
        if (peopleList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String selectedPerson = peopleList.getSelectionModel().getSelectedItem().toString();

        for (Person person : accountingSystem.getPeople()) {
            if (selectedPerson.equals(person.getEmail())) {
                emailField.setText(person.getEmail());
                passwordField.setText(person.getPassword());
                nameField.setText(person.getName());
                surnameField.setText(person.getSurname());
                phoneNumberField.setText(person.getPhoneNumber());
                updateWindow();
                return;
            }
        }
    }

    @Override
    public void updateWindow() {
        Stage stage = (Stage) peopleList.getScene().getWindow();
        stage.show();
    }

    @FXML
    public void addPerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/AddPerson.fxml"));
        Parent root = loader.load();

        AddPersonController addPersonController = loader.getController();
        addPersonController.setPeopleController(this);

        ViewService.newWindow(root, "Add person");
    }

    @FXML
    public void removePerson() {
        String selectedPerson = peopleList.getSelectionModel().getSelectedItem().toString();

        for (Person person : accountingSystem.getPeople()) {
            if (selectedPerson.equals(person.getEmail())) {
                accountingSystem.getPeople().remove(person);
                loadPeople();
                return;
            }
        }
    }

    @FXML
    public void updatePerson() {
        String selectedPerson = peopleList.getSelectionModel().getSelectedItem().toString();

        for (Person person : accountingSystem.getPeople()) {
            if (selectedPerson.equals(person.getEmail())) {
                accountingSystem.getPeople().remove(person);
                accountingSystem.getPeople().add(new Person(emailField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText()));
                loadPeople();
                return;
            }
        }
    }

    //region importAndExport

    @FXML
    public void openExport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Export.fxml"));
        Parent root = loader.load();

        ExportController exportController = loader.getController();
        exportController.setAccountingSystem(accountingSystem);
        exportController.populateDataTypes();

        ViewService.newWindow(root, "Export");
    }

    @FXML
    public void openImport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/Import.fxml"));
        Parent root = loader.load();

        ImportController importController = loader.getController();
        importController.setAccountingSystem(accountingSystem);
        importController.populateDataTypes();
        importController.setController(this);

        ViewService.newWindow(root, "Import");
    }

    //endregion

}
