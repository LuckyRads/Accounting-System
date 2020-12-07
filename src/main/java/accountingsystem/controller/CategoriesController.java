package accountingsystem.controller;

import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.model.Transaction;
import accountingsystem.hibernate.util.CategoryUtil;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.hibernate.util.TransactionUtil;
import accountingsystem.model.TransactionType;
import accountingsystem.service.AlertService;
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
import java.util.List;

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

    @FXML
    private TextField companyBalance;

    @FXML
    private TextField categoryBalance;

    @FXML
    private TextField fromField;

    @FXML
    private TextField toField;

    @FXML
    private ComboBox transactionFilterSelect;

    @FXML
    private Button filterByAmountBtn;

    @FXML
    private Button resetFiltersBtn;

    private double categoryCalculatedBalance;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    CategoryUtil categoryUtil = new CategoryUtil(entityManagerFactory);
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);
    TransactionUtil transactionUtil = new TransactionUtil(entityManagerFactory);

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
        categoryUtil.getRootCategories().forEach(category -> addTreeItems(category, categoryList.getRoot()));
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

    public void populateFilterByAmountList() {
        transactionFilterSelect.getItems().clear();

        transactionFilterSelect.getItems().add(TransactionType.EXPENSE);
        transactionFilterSelect.getItems().add(TransactionType.INCOME);
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
        populateFilterByAmountList();
        fillDescriptionField();
        calculateBalances();
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
        if (getSelectedCategory() == null) {
            AlertService.showError("Please select a category to remove.");
            return;
        }

        Category selectedCategory = getSelectedCategory();
        categoryUtil.destroy(selectedCategory);

        loadCategories();
    }

    @FXML
    public void updateCategory() {
        Category selectedCategory = getSelectedCategory();

        if (selectedCategory == null) {
            AlertService.showError("Please select a category to update.");
            return;
        }

        selectedCategory.setDescription(descriptionField.getText());
        categoryUtil.edit(selectedCategory);
        updateWindow();
    }

    //endregion

    //region transactions

    @FXML
    public void addTransaction() throws IOException {
        Category selectedCategory = getSelectedCategory();
        if (selectedCategory == null) {
            AlertService.showError("Please select a category.");
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
    public void removeTransaction() throws Exception {
        if (transactionTable.getSelectionModel().getSelectedItem() == null) {
            AlertService.showError("Please select a transaction to remove.");
            return;
        }
        transactionUtil.destroy((Transaction) transactionTable.getSelectionModel().getSelectedItem());
        updateWindow();
    }

    @FXML
    public void editTransaction() throws IOException {
        Transaction selectedTransaction = (Transaction) transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction == null || transactionTable.getSelectionModel().getSelectedItem() == null) {
            AlertService.showError("Please select a transaction to edit.");
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

    //region balanceCalculations

    private void calculateBalances() {
        companyBalance.setText(Double.toString(calculateCompanyBalance()));
        if (getSelectedCategory() != null) {
            categoryBalance.setText(Double.toString(calculateCategoryBalance()));
        } else {
            categoryBalance.setText("");
        }
    }

    private double calculateCompanyBalance() {
        double balance = 0;
        List<Transaction> transactions = transactionUtil.getAllTransactions();

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionType().equals(TransactionType.EXPENSE)) {
                balance -= transaction.getAmount();
            } else {
                balance += transaction.getAmount();
            }
        }

        return Math.floor(balance * 100) / 100;
    }

    private double calculateCategoryBalance() {
        categoryCalculatedBalance = 0;
        Category selectedCategory = getSelectedCategory();

        calculateSubcategoryBalance(selectedCategory);

        return Math.floor(categoryCalculatedBalance * 100) / 100;
    }

    private void calculateSubcategoryBalance(Category category) {
        for (Transaction transaction : category.getTransactions()) {
            if (transaction.getTransactionType().equals(TransactionType.EXPENSE)) {
                categoryCalculatedBalance -= transaction.getAmount();
            } else {
                categoryCalculatedBalance += transaction.getAmount();
            }
        }

        for (Category subcategory : category.getSubCategories()) {
            calculateSubcategoryBalance(subcategory);
        }
    }

    //endregion

    //region filters

    @FXML
    public void filterByAmount() {
        if (transactionFilterSelect.getSelectionModel().getSelectedItem() == null || fromField.getText().isEmpty() ||
                toField.getText().isEmpty()) {
            AlertService.showError("Please select filtering type and filtering values.");
            return;
        }

        transactionTable.getItems().clear();

        if (getSelectedCategory() != null) {
            final ObservableList<Transaction> data = FXCollections.observableArrayList();

            transactionNameCol.setCellValueFactory(new PropertyValueFactory("name"));
            transactionTypeCol.setCellValueFactory(new PropertyValueFactory("transactionType"));
            senderCol.setCellValueFactory(new PropertyValueFactory("sender"));
            receiverCol.setCellValueFactory(new PropertyValueFactory("receiver"));
            amountCol.setCellValueFactory(new PropertyValueFactory("amount"));
            dateCol.setCellValueFactory(new PropertyValueFactory("date"));

            if (transactionFilterSelect.getSelectionModel().getSelectedItem().equals(TransactionType.EXPENSE)) {
                getSelectedCategory().getTransactions().forEach(transaction -> {
                    if (transaction.getTransactionType().equals(TransactionType.EXPENSE) &&
                            transaction.getAmount() >= Double.parseDouble(fromField.getText()) &&
                            transaction.getAmount() <= Double.parseDouble(toField.getText())) {
                        data.add(transaction);
                    }
                });
            } else {
                getSelectedCategory().getTransactions().forEach(transaction -> {
                    if (transaction.getTransactionType().equals(TransactionType.INCOME) &&
                            transaction.getAmount() >= Double.parseDouble(fromField.getText()) &&
                            transaction.getAmount() <= Double.parseDouble(toField.getText())) {
                        data.add(transaction);
                    }
                });
            }

            transactionTable.setItems(data);
        }
    }

    @FXML
    public void resetFilters() {
        populateTransactionTable();
    }

    //endregion

    //region responsiblePeople

    @FXML
    public void addResponsiblePerson() throws IOException {
        if (getSelectedCategory() == null) {
            AlertService.showError("Please select a category.");
            return;
        }

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
            AlertService.showError("Please select a category.");
            return;
        }

        selectedCategory.getResponsiblePeople().add(person);
        categoryUtil.edit(selectedCategory);
        updateWindow();
    }

    @FXML
    public void removeResponsiblePerson() {
        if (getSelectedCategory() == null || responsiblePeopleList.getSelectionModel().getSelectedItem() == null) {
            AlertService.showError("Please select a person to remove.");
            return;
        }

        Category selectedCategory = categoryUtil.getCategory(getSelectedCategory().getName());
        Person responsiblePerson = personUtil.
                getPerson(responsiblePeopleList.getSelectionModel().getSelectedItem().toString());

        categoryUtil.removeResponsiblePerson(selectedCategory.getId(), responsiblePerson.getId());
        updateWindow();
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

    public Category getSelectedCategory() {
        if (categoryList.getSelectionModel().getSelectedItem() != null) {
            return categoryUtil.getCategory(parseSelectedItem());
        }
        return null;
    }

    private String parseSelectedItem() {
        return categoryList.getSelectionModel().getSelectedItem().toString().substring(18).split("]")[0].trim();
    }

}
