package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.service.ViewService;

import java.io.IOException;

public class PeopleController {

    private AccountingSystem accountingSystem;

    @FXML
    private ListView peopleList;

    @FXML
    private Button addPersonBtn;

    @FXML
    private Button removePersonBtn;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    public void loadPeople() {
        peopleList.getItems().clear();
        System.out.println("clera");
        for (Person person : accountingSystem.getPeople()) {
            peopleList.getItems().add(person.getEmail());
        }
        Stage stage = (Stage) peopleList.getScene().getWindow();
        stage.show();
    }

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) addPersonBtn.getScene().getWindow(), root);
    }

    public void addPerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/AddPerson.fxml"));
        Parent root = loader.load();

        AddPersonController addPersonController = loader.getController();
        addPersonController.setPeopleController(this);

        ViewService.newWindow(root, "Add person");
    }

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

}
