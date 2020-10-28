package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.java.accountingsystem.model.Person;

public class AddResponsiblePersonController implements WindowController {

    private AddCategoryController addCategoryController;

    @FXML
    private ListView availablePeopleList;

    @FXML
    private Button addResponsiblePersonBtn;

    public AddCategoryController getAddCategoryController() {
        return addCategoryController;
    }

    public void setAddCategoryController(AddCategoryController addCategoryController) {
        this.addCategoryController = addCategoryController;
    }

    @Override
    public void closeWindow() {
        Stage stage = (Stage) addResponsiblePersonBtn.getScene().getWindow();
        stage.close();
    }

    public void populateAvailablePeopleList() {
        availablePeopleList.getItems().clear();

        addCategoryController.getCategoriesController().getAccountingSystem().
                getPeople().forEach(person -> availablePeopleList.getItems().add(person));
    }

    @FXML
    public void addResponsiblePerson() {
        if (availablePeopleList.getSelectionModel().getSelectedItem() != null) {
            addCategoryController.addResponsiblePerson((Person) availablePeopleList.getSelectionModel().getSelectedItem());
            closeWindow();
        }
    }

}
