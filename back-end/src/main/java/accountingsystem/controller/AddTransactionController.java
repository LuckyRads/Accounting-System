package accountingsystem.controller;

import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Transaction;
import accountingsystem.model.TransactionType;
import accountingsystem.service.AlertService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    @FXML
    private DatePicker datePicker;

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
        if (nameField.getText().isEmpty() || transactionTypeList.getSelectionModel().getSelectedItem() == null ||
                senderField.getText().isEmpty() || receiverField.getText().isEmpty() || amountField.getText().isEmpty() ||
                datePicker.valueProperty().get() == null) {
            AlertService.showError("Please fill out all fields correctly.");
            return;
        }

        this.category.getTransactions().add(new Transaction(nameField.getText(),
                (TransactionType) transactionTypeList.getSelectionModel().getSelectedItem(),
                senderField.getText(), receiverField.getText(), Double.parseDouble(amountField.getText()),
                datePicker.valueProperty().get(), category));

        categoriesController.categoryUtil.edit(category);
        closeWindow();
    }

}