package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.model.Category;
import main.java.accountingsystem.model.Company;
import main.java.accountingsystem.model.Person;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ImportController implements WindowController {

    private AccountingSystem accountingSystem;

    @FXML
    private Button importBtn;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @Override
    public void closeWindow() {

    }

    public void importData() {
        String file = "output.ser";

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allDataRead = false;
            while (!allDataRead) {
                try {
                    Object object = objectInputStream.readObject();

                    if (object instanceof AccountingSystem) {
                        AccountingSystem accountingSystemObject = (AccountingSystem) object;
                        accountingSystem.setCompany(accountingSystemObject.getCompany());
                        accountingSystem.setDateCreated(accountingSystemObject.getDateCreated());
                        accountingSystem.setVersion(accountingSystemObject.getVersion());
                    }
                    if (object instanceof Person) {
                        Person personToAdd = (Person) object;
                        for (Person person : accountingSystem.getPeople()) {
                            if (personToAdd.getEmail().equals(person.getEmail())) {
                                accountingSystem.getPeople().remove(person);
                                break;
                            }
                        }
                        accountingSystem.getPeople().add(personToAdd);
                    }
                    if (object instanceof Company) {
                        Company companyToAdd = (Company) object;
                        for (Company company : accountingSystem.getCompanies()) {
                            if (companyToAdd.getEmail().equals(company.getEmail())) {
                                accountingSystem.getCompanies().remove(company);
                                break;
                            }
                        }
                        accountingSystem.getCompanies().add(companyToAdd);
                    }
                    if (object instanceof Category) {
                        Category categoryToAdd = (Category) object;
                        for (Category category : accountingSystem.getCategories()) {
                            if (categoryToAdd.getName().equals(category.getName())) {
                                accountingSystem.getCategories().remove(category);
                                break;
                            }
                        }
                        accountingSystem.getCategories().add(categoryToAdd);
                    }
                } catch (EOFException e) {
                    allDataRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            return;
        }
    }

}
