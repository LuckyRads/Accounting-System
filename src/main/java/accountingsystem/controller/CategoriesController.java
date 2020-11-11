package accountingsystem.controller;

import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.model.Transaction;
import accountingsystem.hibernate.util.CategoryUtil;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.model.TransactionType;
import accountingsystem.service.ViewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Date;

public class CategoriesController implements Controller {

    @FXML
    private Button menuBtn;

    @FXML
    private TreeView categoryList;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TableView transactionTable;
    @FXML
    private TableColumn<Transaction, String> transactionNameCol;
    @FXML
    private TableColumn<Transaction, TransactionType> transactionTypeCol;
    @FXML
    private TableColumn<Transaction, String> senderCol;
    @FXML
    private TableColumn<Transaction, String> receiverCol;
    @FXML
    private TableColumn<Transaction, Double> amountCol;
    @FXML
    private TableColumn<Transaction, Date> dateCol;

    @FXML
    private ListView responsiblePeopleList;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    CategoryUtil categoryUtil = new CategoryUtil(entityManagerFactory);
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

    //endregion

    @FXML
    public void loadCategories() {
        categoryList.setRoot(new TreeItem<String>("Categories"));
        categoryUtil.getAllCategories().forEach(category -> addTreeItems(category, categoryList.getRoot()));
        categoryList.setShowRoot(false);

        updateWindow();
    }

    public void populateResponsiblePeopleList() {
        responsiblePeopleList.getItems().clear();

        if (getSelectedCategory() != null) {
            getSelectedCategory().getResponsiblePeople().forEach(person -> responsiblePeopleList.getItems().add(person));
        }
    }

    public void populateTransactionTable() {
        transactionTable.getItems().clear();

        if (getSelectedCategory() != null) {
            final ObservableList<Transaction> data = FXCollections.observableArrayList();

            transactionNameCol.setCellValueFactory(new PropertyValueFactory("name"));
            transactionTypeCol.setCellValueFactory(new PropertyValueFactory("transactionType"));
            senderCol.setCellValueFactory(new PropertyValueFactory("sender"));
            receiverCol.setCellValueFactory(new PropertyValueFactory("receiver"));
            amountCol.setCellValueFactory(new PropertyValueFactory("amount"));
            dateCol.setCellValueFactory(new PropertyValueFactory("date"));

            getSelectedCategory().getTransactions().forEach(transaction -> data.add(transaction));

            transactionTable.setItems(data);
        }
    }

    private void fillDescriptionField() {
        descriptionField.clear();
        if (getSelectedCategory() != null) {
            descriptionField.setText(getSelectedCategory().getDescription());
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
        populateTransactionTable();
        fillDescriptionField();
        Stage stage = (Stage) menuBtn.getScene().getWindow();
        stage.show();
    }

    //region categories

    @FXML
    public void addCategory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCategory.fxml"));
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
    public void removeCategory() throws Exception {
        Category selectedCategory = categoryUtil.getCategory(parseSelectedItem());

        Category categoryToRemove = null;
        for (Category category : categoryUtil.getAllCategories()) {
            if (selectedCategory.getName().equals(category.getName())) {
                categoryToRemove = category;
            } else {
                removeSubCategory(selectedCategory, category);
            }
        }
        if (categoryToRemove != null) {
            categoryUtil.destroy(categoryToRemove);
        }
        loadCategories();
    }

    private void removeSubCategory(Category subCategory, Category rootCategory) {
        if (subCategory.getParentCategory() != null && subCategory.getParentCategory().getName().equals(rootCategory.getName())) {
            rootCategory.getSubCategories().remove(subCategory);
            categoryUtil.edit(rootCategory);
            return;
        }

        for (Category category : rootCategory.getSubCategories()) {
            removeSubCategory(subCategory, category);
        }

        updateWindow();
    }

    @FXML
    public void updateCategory() {
        getSelectedCategory().setDescription(descriptionField.getText());
        categoryUtil.edit(getSelectedCategory());
        updateWindow();
    }

    //endregion

    //region transactions

    @FXML
    public void addTransaction() throws IOException {
        Category selectedCategory = getSelectedCategory();
        if (selectedCategory == null) {
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddTransaction.fxml"));
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
        if (selectedCategory == null || transactionTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        for (Category category : categoryUtil.getAllCategories()) {
            removeTransaction(selectedCategory, category);
        }
    }

    private void removeTransaction(Category subCategory, Category rootCategory) {
        if (subCategory.getName().equals(rootCategory.getName())) {
            rootCategory.getTransactions().remove(transactionTable.getSelectionModel().getSelectedItem());
            categoryUtil.edit(rootCategory);
            updateWindow();
            return;
        }

        for (Category category : rootCategory.getSubCategories()) {
            removeTransaction(subCategory, category);
        }
    }

    @FXML
    public void editTransaction() throws IOException {
        Transaction selectedTransaction = (Transaction) transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction == null) {
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditTransaction.fxml"));
        Parent root = loader.load();

        EditTransactionController editTransactionController = loader.getController();
        editTransactionController.setCategoriesController(this);
        editTransactionController.setTransaction(selectedTransaction);
        editTransactionController.populateTransactionTypeList();
        editTransactionController.loadTransaction();

        ViewService.newWindow(root, "Edit transaction");
    }

    //endregion

    //region responsiblePeople

    @FXML
    public void addResponsiblePerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddResponsiblePerson.fxml"));
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

        for (Category category : categoryUtil.getAllCategories()) {
            addResponsiblePerson(selectedCategory, category, person);
        }
    }

    private void addResponsiblePerson(Category subCategory, Category rootCategory, Person person) {
        if (subCategory.getName().equals(rootCategory.getName())) {
            rootCategory.getResponsiblePeople().add(person);
            categoryUtil.edit(rootCategory);
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

        for (Category category : categoryUtil.getAllCategories()) {
            removeResponsiblePerson(selectedCategory, category);
        }
    }

    private void removeResponsiblePerson(Category subCategory, Category rootCategory) {
        if (subCategory.getName().equals(rootCategory.getName())) {
            rootCategory.getResponsiblePeople().remove(responsiblePeopleList.getSelectionModel().getSelectedItem());
            categoryUtil.edit(rootCategory);
            updateWindow();
            return;
        }

        for (Category category : rootCategory.getSubCategories()) {
            removeResponsiblePerson(subCategory, category);
        }
    }

    //endregion

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

    private Category getSelectedCategory() {
        if (categoryList.getSelectionModel().getSelectedItem() != null) {
            return categoryUtil.getCategory(parseSelectedItem());
        }
        return null;
    }

    private String parseSelectedItem() {
        return categoryList.getSelectionModel().getSelectedItem().toString().substring(18).split("]")[0].trim();
    }

}
