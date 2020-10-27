package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.accountingsystem.model.Category;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.model.Transaction;

import java.io.IOException;
import java.util.ArrayList;

public class AddCategoryController implements WindowController {

    private CategoriesController categoriesController;

    private Category parentCategory = null;

    @FXML
    private Button addCategoryBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

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

    //    public void populateResponsiblePeopleSelect() {
//        responsiblePersonSelect.getItems().clear();
//
//        for (Person person : companiesController.getAccountingSystem().getPeople()) {
//            responsiblePersonSelect.getItems().add(person.getEmail());
//        }
//    }

    @Override
    public void closeWindow() {
        categoriesController.loadCategories();
        Stage stage = (Stage) addCategoryBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addCategory() throws IOException {
//        Person responsiblePerson = PeopleService.getPerson(responsiblePersonSelect.getSelectionModel().getSelectedItem().toString(), companiesController.getAccountingSystem().getPeople());
        if (parentCategory != null) {
            Category subCategory = new Category(nameField.getText(), descriptionField.getText(),
                    new ArrayList<Transaction>(), new ArrayList<Category>(),
                    new ArrayList<Person>(), parentCategory);

            for (Category category : categoriesController.getAccountingSystem().getCategories()) {
                addSubCategory(subCategory, category);
            }

        } else {
            categoriesController.getAccountingSystem().getCategories().add(
                    new Category(nameField.getText(), descriptionField.getText(),
                            new ArrayList<Transaction>(), new ArrayList<Category>(), new ArrayList<Person>(), parentCategory));
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

}
