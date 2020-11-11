package accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import accountingsystem.model.Transaction;
import accountingsystem.model.TransactionType;

import java.util.Date;

public class EditTransactionController implements WindowController {

    private CategoriesController categoriesController;

    private Transaction transaction;

    @FXML
    private Button editTransactionBtn;

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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void closeWindow() {
        categoriesController.updateWindow();
        Stage stage = (Stage) editTransactionBtn.getScene().getWindow();
        stage.close();
    }

    public void loadTransaction() {
        nameField.setText(transaction.getName());
        transactionTypeList.getSelectionModel().select(transaction.getTransactionType());
        senderField.setText(transaction.getSender());
        receiverField.setText(transaction.getReceiver());
        amountField.setText(String.valueOf(transaction.getAmount()));
        // TODO: Date field
    }

    public void populateTransactionTypeList() {
        transactionTypeList.getItems().clear();

        transactionTypeList.getItems().add(TransactionType.EXPENSE);
        transactionTypeList.getItems().add(TransactionType.INCOME);
    }

    @FXML
    public void editTransaction() {
        transaction.setName(nameField.getText());
        transaction.setTransactionType((TransactionType) transactionTypeList.getSelectionModel().getSelectedItem());
        transaction.setSender(senderField.getText());
        transaction.setReceiver(receiverField.getText());
        transaction.setAmount(Double.parseDouble(amountField.getText()));
        transaction.setDate(new Date()); // TODO: Fix date

        closeWindow();
    }

}