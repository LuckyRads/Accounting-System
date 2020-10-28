package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.model.Category;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.service.CategoryService;
import main.java.accountingsystem.service.ViewService;

import java.io.IOException;

public class CategoriesController implements Controller {

    private AccountingSystem accountingSystem;

    @FXML
    private Button menuBtn;

    @FXML
    private TreeView categoryList;

    @FXML
    private ListView transactionList;

    @FXML
    private ListView responsiblePeopleList;

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

        ViewService.openView((Stage) menuBtn.getScene().getWindow(), root);
    }

    @FXML
    public void loadCategories() {
        categoryList.setRoot(new TreeItem<String>("Categories"));
        accountingSystem.getCategories().forEach(category -> addTreeItems(category, categoryList.getRoot()));
        categoryList.setShowRoot(false);

        updateWindow();
    }

    public void populateResponsiblePeopleList() {
        responsiblePeopleList.getItems().clear();

        if (getSelectedCategory() != null) {
            getSelectedCategory().getResponsiblePeople().forEach(person -> responsiblePeopleList.getItems().add(person));
        }
    }

    public void populateTransactionList() {
        transactionList.getItems().clear();

        if (getSelectedCategory() != null) {
            getSelectedCategory().getTransactions().forEach(transaction -> transactionList.getItems().add(transaction));
        }
    }

    private void addTreeItems(Category category, TreeItem parentItem) {
        TreeItem<Category> categoryTreeItem = new TreeItem<Category>(category);
        parentItem.getChildren().add(categoryTreeItem);
        category.getSubCategories().forEach(subCategory -> addTreeItems(subCategory, categoryTreeItem));
    }

    @Override
    public void updateWindow() {
        populateResponsiblePeopleList();
        populateTransactionList();
        Stage stage = (Stage) menuBtn.getScene().getWindow();
        stage.show();
    }

    @FXML
    public void addCategory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/AddCategory.fxml"));
        Parent root = loader.load();

        AddCategoryController addCategoryController = loader.getController();
        addCategoryController.setCategoriesController(this);

        Category selectedCategory = getSelectedCategory();
        if (selectedCategory != null) {
            addCategoryController.setParentCategory(selectedCategory);
        }

        ViewService.newWindow(root, "Add category");
    }

    @FXML
    public void removeCategory() throws IOException {
        Category selectedCategory = CategoryService.getCategory(parseSelectedItem(), accountingSystem.getCategories());

        Category categoryToRemove = null;
        for (Category category : accountingSystem.getCategories()) {
            if (selectedCategory.getName().equals(category.getName())) {
                categoryToRemove = category;
            } else {
                removeSubCategory(selectedCategory, category);
            }
        }
        if (categoryToRemove != null) {
            accountingSystem.getCategories().remove(categoryToRemove);
        }
        loadCategories();
    }

    private void removeSubCategory(Category subCategory, Category rootCategory) {
        if (subCategory.getParentCategory() != null && subCategory.getParentCategory().getName().equals(rootCategory.getName())) {
            rootCategory.getSubCategories().remove(subCategory);
            return;
        }

        for (Category category : rootCategory.getSubCategories()) {
            removeSubCategory(subCategory, category);
        }

        updateWindow();
    }

    @FXML
    public void addTransaction() throws IOException {
        Category selectedCategory = getSelectedCategory();
        if (selectedCategory == null) {
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/AddTransaction.fxml"));
        Parent root = loader.load();

        AddTransactionController addTransactionController = loader.getController();
        addTransactionController.setCategoriesController(this);
        addTransactionController.setCategory(selectedCategory);
        addTransactionController.populateTransactionTypeList();

        ViewService.newWindow(root, "Add transaction");
    }

    @FXML
    public void removeTransaction() {
        Category selectedCategory = getSelectedCategory();
        if (selectedCategory == null || transactionList.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        for (Category category : accountingSystem.getCategories()) {
            removeTransaction(selectedCategory, category);
        }
    }

    private void removeTransaction(Category subCategory, Category rootCategory) {
        if (subCategory.getName().equals(rootCategory.getName())) {
            rootCategory.getTransactions().remove(transactionList.getSelectionModel().getSelectedItem());
            return;
        }

        for (Category category : rootCategory.getSubCategories()) {
            removeTransaction(subCategory, category);
        }

        updateWindow();
    }

    @FXML
    public void addResponsiblePerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/AddResponsiblePerson.fxml"));
        Parent root = loader.load();

        AddResponsiblePersonController addResponsiblePersonController = loader.getController();
        addResponsiblePersonController.setCategoriesController(this);

        addResponsiblePersonController.populateAvailablePeopleList();
        ViewService.newWindow(root, "Add responsible person");
    }

    public void addResponsiblePerson(Person person) {
        Category selectedCategory = getSelectedCategory();
        if (selectedCategory == null) {
            return;
        }

        for (Category category : this.accountingSystem.getCategories()) {
            addResponsiblePerson(selectedCategory, category, person);
        }
    }

    private void addResponsiblePerson(Category subCategory, Category rootCategory, Person person) {
        if (subCategory.getName().equals(rootCategory.getName())) {
            rootCategory.getResponsiblePeople().add(person);
            return;
        }

        for (Category category : rootCategory.getSubCategories()) {
            addResponsiblePerson(subCategory, category, person);
        }

        updateWindow();
    }

    @FXML
    public void removeResponsiblePerson() {
        Category selectedCategory = getSelectedCategory();
        if (selectedCategory == null) {
            return;
        }

        for (Category category : this.accountingSystem.getCategories()) {
            removeResponsiblePerson(selectedCategory, category);
        }
    }

    private void removeResponsiblePerson(Category subCategory, Category rootCategory) {
        if (subCategory.getName().equals(rootCategory.getName())) {
            rootCategory.getResponsiblePeople().remove(responsiblePeopleList.getSelectionModel().getSelectedItem());
            return;
        }

        for (Category category : rootCategory.getSubCategories()) {
            removeResponsiblePerson(subCategory, category);
        }

        updateWindow();
    }

    private Category getSelectedCategory() {
        if (categoryList.getSelectionModel().getSelectedItem() != null) {
            return CategoryService.getCategory(parseSelectedItem(), accountingSystem.getCategories());
        }
        return null;
    }

    private String parseSelectedItem() {
        return categoryList.getSelectionModel().getSelectedItem().toString().substring(18).split("]")[0].trim();
    }

}
