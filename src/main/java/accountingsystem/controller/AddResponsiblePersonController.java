package accountingsystem.controller;

import accountingsystem.hibernate.model.Person;
import accountingsystem.service.AlertService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class AddResponsiblePersonController implements WindowController {

    private AddCategoryController addCategoryController = null;

    private CategoriesController categoriesController = null;

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

    public CategoriesController getCategoriesController() {
        return categoriesController;
    }

    public void setCategoriesController(CategoriesController categoriesController) {
        this.categoriesController = categoriesController;
    }

    @Override
    public void closeWindow() {
        Stage stage = (Stage) addResponsiblePersonBtn.getScene().getWindow();
        stage.close();
    }

    public void populateAvailablePeopleList() {
        availablePeopleList.getItems().clear();

        if (addCategoryController != null) {
            addCategoryController.getCategoriesController().personUtil.getAllPeople().forEach(person -> availablePeopleList.getItems().add(person));
        } else {
            categoriesController.personUtil.getAllPeople().forEach(person -> availablePeopleList.getItems().add(person));
        }
    }

    @FXML
    public void addResponsiblePerson() {
        if (availablePeopleList.getSelectionModel().getSelectedItem() != null) {
            if (addCategoryController != null) {
                addCategoryController.addResponsiblePerson((Person) availablePeopleList.getSelectionModel().getSelectedItem());
            } else {
                categoriesController.addResponsiblePerson((Person) availablePeopleList.getSelectionModel().getSelectedItem());
            }
            closeWindow();
        } else {
            AlertService.showError("Please select a person.");
        }
    }

}
