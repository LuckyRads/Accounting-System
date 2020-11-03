package main.java.accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.model.Category;
import main.java.accountingsystem.model.Company;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.service.CategoryService;
import main.java.accountingsystem.service.CompaniesService;
import main.java.accountingsystem.service.PeopleService;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ImportController implements WindowController {

    private AccountingSystem accountingSystem;

    @FXML
    private Button importBtn;

    @FXML
    private ComboBox dataTypeSelect;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @Override
    public void closeWindow() {
        Stage stage = (Stage) importBtn.getScene().getWindow();
        stage.close();
    }

    public void populateDataTypes() {
        dataTypeSelect.getItems().clear();

        dataTypeSelect.getItems().add("All data");
        dataTypeSelect.getItems().add("System data");
        dataTypeSelect.getItems().add("People data");
        dataTypeSelect.getItems().add("Company data");
        dataTypeSelect.getItems().add("Category data");

        dataTypeSelect.getSelectionModel().select("All data");
    }

    public void importData() {
        Stage stage = (Stage) importBtn.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Serializable files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);

        if (file == null) {
            return;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean allDataRead = false;
            while (!allDataRead) {
                try {
                    Object object = objectInputStream.readObject();

                    switch ((String) dataTypeSelect.getSelectionModel().getSelectedItem()) {
                        case "All data":
                            importAllData(object);
                            break;
                        case "System data":
                            importSystemData(object);
                            break;
                        case "People data":
                            importPeopleData(object);
                            break;
                        case "Company data":
                            importCompanyData(object);
                            break;
                        case "Category data":
                            importCategoryData(object);
                            break;
                    }
                } catch (EOFException e) {
                    allDataRead = true;
                }
            }

            fileInputStream.close();
            objectInputStream.close();
            closeWindow();
        } catch (Exception e) {
            System.out.println("Error! Something went wrong while importing data." +
                    "Please check if the destination path is correct");
            e.printStackTrace();
            return;
        }
    }

    private void importAllData(Object object) {
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
    }

    private void importSystemData(Object object) {
        if (object instanceof AccountingSystem) {
            AccountingSystem accountingSystemObject = (AccountingSystem) object;
            accountingSystem.setCompany(accountingSystemObject.getCompany());
            accountingSystem.setDateCreated(accountingSystemObject.getDateCreated());
            accountingSystem.setVersion(accountingSystemObject.getVersion());
        }
    }

    private void importPeopleData(Object object) {
        if (object instanceof Person) {
            PeopleService.addReplacePerson((Person) object,
                    accountingSystem.getPeople());
        }
    }

    private void importCompanyData(Object object) {
        if (object instanceof Company) {
            CompaniesService.addReplaceCompany((Company) object,
                    accountingSystem.getCompanies());
        }
    }

    private void importCategoryData(Object object) {
        if (object instanceof Category) {
            CategoryService.addReplaceCategory((Category) object,
                    accountingSystem.getCategories());
        }
    }

}
