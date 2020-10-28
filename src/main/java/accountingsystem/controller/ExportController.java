package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.model.Category;
import main.java.accountingsystem.model.Company;
import main.java.accountingsystem.model.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ExportController implements WindowController {

    private AccountingSystem accountingSystem;

    @FXML
    private Button exportBtn;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @Override
    public void closeWindow() {

    }

    @FXML
    public void exportData() {
        String file = "output.ser";

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(accountingSystem);

            for (Person person : accountingSystem.getPeople()) {
                objectOutputStream.writeObject(person);
            }

            for (Company company : accountingSystem.getCompanies()) {
                objectOutputStream.writeObject(company);
            }

            for (Category category : accountingSystem.getCategories()) {
                objectOutputStream.writeObject(category);
            }

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error! Something went wrong while exporting data." +
                    "Please check if the destination path is correct");
            return;
        }
    }

}
