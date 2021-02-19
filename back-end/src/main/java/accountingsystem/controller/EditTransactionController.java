package accountingsystem.controller;

import accountingsystem.hibernate.model.Transaction;
import accountingsystem.hibernate.util.TransactionUtil;
import accountingsystem.model.TransactionType;
import accountingsystem.service.AlertService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    @FXML
    private DatePicker datePicker;

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

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    TransactionUtil transactionUtil = new TransactionUtil(entityManagerFactory);

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
        datePicker.setValue(transaction.getDate());
    }

    public void populateTransactionTypeList() {
        transactionTypeList.getItems().clear();

        transactionTypeList.getItems().add(TransactionType.EXPENSE);
        transactionTypeList.getItems().add(TransactionType.INCOME);
    }

    @FXML
    public void editTransaction() throws Exception {
        if (nameField.getText().isEmpty() || transactionTypeList.getSelectionModel().getSelectedItem() == null ||
                senderField.getText().isEmpty() || receiverField.getText().isEmpty() || amountField.getText().isEmpty() ||
                datePicker.valueProperty().get() == null) {
            AlertService.showError("Please fill out all fields correctly!");
            return;
        }

        transaction.setName(nameField.getText());
        transaction.setTransactionType((TransactionType) transactionTypeList.getSelectionModel().getSelectedItem());
        transaction.setSender(senderField.getText());
        transaction.setReceiver(receiverField.getText());
        transaction.setAmount(Double.parseDouble(amountField.getText()));
        transaction.setDate(datePicker.valueProperty().get());

        transactionUtil.edit(transaction);

        closeWindow();
    }

}