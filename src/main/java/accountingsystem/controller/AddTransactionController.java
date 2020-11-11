package accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import accountingsystem.model.Category;
import accountingsystem.model.Transaction;
import accountingsystem.model.TransactionType;

import java.util.Date;

public class AddTransactionController implements WindowController {

    private CategoriesController categoriesController;

    private Category category;

    @FXML
    private Button addTransactionBtn;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox transactionTypeList;

    @FXML
    private TextField senderField;

    @FXML
    private TextField receiverField;

    @FXML
    private TextField amountField;

    public CategoriesController getCategoriesController() {
        return categoriesController;
    }

    public void setCategoriesController(CategoriesController categoriesController) {
        this.categoriesController = categoriesController;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void closeWindow() {
        categoriesController.loadCategories();
        Stage stage = (Stage) addTransactionBtn.getScene().getWindow();
        stage.close();
    }

    public void populateTransactionTypeList() {
        transactionTypeList.getItems().clear();

        transactionTypeList.getItems().add(TransactionType.EXPENSE);
        transactionTypeList.getItems().add(TransactionType.INCOME);
    }

    @FXML
    public void addTransaction() {
        for (Category category : categoriesController.getAccountingSystem().getCategories()) {
            addTransactionToCategory(this.category, category);
        }

        closeWindow();
    }

    private void addTransactionToCategory(Category subCategory, Category rootCategory) {
        if (subCategory.getName().equals(rootCategory.getName())) {

            Transaction transaction = new Transaction(nameField.getText(),
                    (TransactionType) transactionTypeList.getSelectionModel().getSelectedItem(),
                    senderField.getText(), receiverField.getText(), Double.parseDouble(amountField.getText()),
                    new Date());

            rootCategory.getTransactions().add(transaction);
            return;
        }

        for (Category category : rootCategory.getSubCategories()) {
            addTransactionToCategory(subCategory, category);
        }
    }

}