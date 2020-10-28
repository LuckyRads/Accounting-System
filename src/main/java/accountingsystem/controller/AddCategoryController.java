package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.accountingsystem.model.Category;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.model.Transaction;
import main.java.accountingsystem.service.ViewService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddCategoryController implements WindowController {

    private CategoriesController categoriesController;

    private Category parentCategory = null;

    @FXML
    private Button addCategoryBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ListView responsiblePeopleList;

    public CategoriesController getCategoriesController() {
        return categoriesController;
    }

    public void setCategoriesController(CategoriesController categoriesController) {
        this.categoriesController = categoriesController;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void updateWindow() {
        Stage stage = (Stage) addCategoryBtn.getScene().getWindow();
        stage.show();
    }

    @Override
    public void closeWindow() {
        categoriesController.loadCategories();
        Stage stage = (Stage) addCategoryBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addCategory() throws IOException {
        List<Person> responsiblePeople = new ArrayList<>();
        responsiblePeopleList.getItems().forEach(personItem -> responsiblePeople.add((Person) personItem));

        if (parentCategory != null) {
            Category subCategory = new Category(nameField.getText(), descriptionField.getText(),
                    new ArrayList<Transaction>(), new ArrayList<Category>(),
                    responsiblePeople, parentCategory);

            for (Category category : categoriesController.getAccountingSystem().getCategories()) {
                addSubCategory(subCategory, category);
            }
        } else {
            categoriesController.getAccountingSystem().getCategories().add(
                    new Category(nameField.getText(), descriptionField.getText(),
                            new ArrayList<Transaction>(), new ArrayList<Category>(), responsiblePeople, parentCategory));
        }

        closeWindow();
    }

    private void addSubCategory(Category subCategory, Category rootCategory) {
        if (subCategory.getParentCategory().getName().equals(rootCategory.getName())) {
            rootCategory.getSubCategories().add(subCategory);
            return;
        }

        for (Category category : rootCategory.getSubCategories()) {
            addSubCategory(subCategory, category);
        }
    }

    @FXML
    public void addResponsiblePerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/AddResponsiblePerson.fxml"));
        Parent root = loader.load();

        AddResponsiblePersonController addResponsiblePersonController = loader.getController();
        addResponsiblePersonController.setAddCategoryController(this);

        addResponsiblePersonController.populateAvailablePeopleList();
        ViewService.newWindow(root, "Add responsible person");
    }

    public void addResponsiblePerson(Person person) {
        responsiblePeopleList.getItems().add(person);
    }

    @FXML
    public void removeResponsiblePerson() {
        responsiblePeopleList.getItems().remove(responsiblePeopleList.getSelectionModel().getSelectedItem());
    }

}
