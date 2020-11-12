package accountingsystem.controller;

import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.AccountingSystemUtil;
import accountingsystem.hibernate.util.CategoryUtil;
import accountingsystem.hibernate.util.CompanyUtil;
import accountingsystem.hibernate.util.PersonUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ExportController implements WindowController {

    @FXML
    private Button exportBtn;

    @FXML
    private ComboBox dataTypeSelect;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    AccountingSystemUtil accountingSystemUtil = new AccountingSystemUtil(entityManagerFactory);
    CategoryUtil categoryUtil = new CategoryUtil(entityManagerFactory);
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);
    CompanyUtil companyUtil = new CompanyUtil(entityManagerFactory);

    @Override
    public void closeWindow() {
        Stage stage = (Stage) exportBtn.getScene().getWindow();
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

    @FXML
    public void exportData() {
        Stage stage = (Stage) exportBtn.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Serializable files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file == null) {
            return;
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            switch ((String) dataTypeSelect.getSelectionModel().getSelectedItem()) {
                case "All data":
                    exportAllData(objectOutputStream);
                    break;
                case "System data":
                    exportSystemData(objectOutputStream);
                    break;
                case "People data":
                    exportPeopleData(objectOutputStream);
                    break;
                case "Company data":
                    exportCompanyData(objectOutputStream);
                    break;
                case "Category data":
                    exportCategoryData(objectOutputStream);
                    break;
            }

            fileOutputStream.close();
            objectOutputStream.close();
            closeWindow();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong while exporting data.");
            alert.showAndWait();
            return;
        }
    }

    private void exportAllData(ObjectOutputStream objectOutputStream) throws IOException {
        for (Person person : personUtil.getAllPeople()) {
            objectOutputStream.writeObject(person);
        }

        for (Company company : companyUtil.getAllCompanies()) {
            objectOutputStream.writeObject(company);
        }

        for (Category category : categoryUtil.getAllCategories()) {
            objectOutputStream.writeObject(category);
        }
    }

    private void exportSystemData(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(accountingSystemUtil.getAccountingSystem());
    }

    private void exportPeopleData(ObjectOutputStream objectOutputStream) throws IOException {
        for (Person person : personUtil.getAllPeople()) {
            objectOutputStream.writeObject(person);
        }
    }

    private void exportCompanyData(ObjectOutputStream objectOutputStream) throws IOException {
        for (Company company : companyUtil.getAllCompanies()) {
            objectOutputStream.writeObject(company);
        }
    }

    private void exportCategoryData(ObjectOutputStream objectOutputStream) throws IOException {
        for (Category category : categoryUtil.getAllCategories()) {
            objectOutputStream.writeObject(category);
        }
    }

}
