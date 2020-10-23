package main.java.accountingsystem.controller;

import com.sun.glass.ui.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    public void loadPeople() {
        for (Person person : accountingSystem.getPeople()) {
            peopleList.getItems().add(person.getEmail());
        }
        Stage stage = (Stage) peopleList.getScene().getWindow();
        stage.show();
    }

    @FXML
    public void openMenu() {
        //
    }

    public void addPerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/AddPerson.fxml"));
        Parent root = loader.load();

        AddPersonController addPersonController = loader.getController();
        addPersonController.setAccountingSystem(accountingSystem);

        ViewService.newWindow(root, "Add person");
//        ViewService.openView((Stage) addPersonBtn.getScene().getWindow(), root);
    }

}
