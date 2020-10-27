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

    private void addTreeItems(Category category, TreeItem parentItem) {
        TreeItem<Category> categoryTreeItem = new TreeItem<Category>(category);
        parentItem.getChildren().add(categoryTreeItem);
        category.getSubCategories().forEach(subCategory -> addTreeItems(subCategory, categoryTreeItem));
    }

    @Override
    public void updateWindow() {
        Stage stage = (Stage) menuBtn.getScene().getWindow();
        stage.show();
    }

    @FXML
    public void addCategory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/accountingsystem/view/AddCategory.fxml"));
        Parent root = loader.load();

        AddCategoryController addCategoryController = loader.getController();
        addCategoryController.setCategoriesController(this);
        if (categoryList.getSelectionModel().getSelectedItem() != null) {

            // TODO: Fix this mess
            String selectedCategoryName = categoryList.getSelectionModel().getSelectedItem().toString().substring(18).split("]")[0].trim();

            Category selectedCategory = CategoryService.getCategory(selectedCategoryName, accountingSystem.getCategories());
            addCategoryController.setParentCategory(selectedCategory);
        }

        ViewService.newWindow(root, "Add category");
    }

    @FXML
    public void removeCategory() throws IOException {
//        String selectedPerson = peopleList.getSelectionModel().getSelectedItem().toString();
//
//        for (Person person : accountingSystem.getPeople()) {
//            if (selectedPerson.equals(person.getEmail())) {
//                accountingSystem.getPeople().remove(person);
//                loadPeople();
//                return;
//            }
//        }
    }

}
