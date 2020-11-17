package accountingsystem.controller;

import accountingsystem.hibernate.model.AccountingSystem;
import accountingsystem.hibernate.model.Category;
import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.AccountingSystemUtil;
import accountingsystem.hibernate.util.CategoryUtil;
import accountingsystem.hibernate.util.CompanyUtil;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.AlertService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ImportController implements WindowController {

    private Controller controller;

    @FXML
    private Button importBtn;

    @FXML
    private ComboBox dataTypeSelect;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    AccountingSystemUtil accountingSystemUtil = new AccountingSystemUtil(entityManagerFactory);
    CategoryUtil categoryUtil = new CategoryUtil(entityManagerFactory);
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);
    CompanyUtil companyUtil = new CompanyUtil(entityManagerFactory);

    @Override
    public void closeWindow() {
        Stage stage = (Stage) importBtn.getScene().getWindow();
        stage.close();
        controller.updateWindow();
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
            AlertService.showError("Something went wrong while importing data.");
            return;
        }
    }

    private void importAllData(Object object) throws Exception {
        if (object instanceof AccountingSystem) {
            AccountingSystem accountingSystemObject = (AccountingSystem) object;

            AccountingSystem accountingSystem = accountingSystemUtil.getAccountingSystem();
            accountingSystem.setCompany(accountingSystemObject.getCompany());
            accountingSystem.setDateCreated(accountingSystemObject.getDateCreated());
            accountingSystem.setVersion(accountingSystemObject.getVersion());

            accountingSystemUtil.edit(accountingSystem);
        }
        if (object instanceof Person) {
            Person personToAdd = (Person) object;
            for (Person person : personUtil.getAllPeople()) {
                if (personToAdd.getEmail().equals(person.getEmail())) {
                    personUtil.destroy(person);
                    break;
                }
            }
            personUtil.create(personToAdd);
        }
        if (object instanceof Company) {
            Company companyToAdd = (Company) object;
            for (Company company : companyUtil.getAllCompanies()) {
                if (companyToAdd.getEmail().equals(company.getEmail())) {
                    companyUtil.destroy(company);
                    break;
                }
            }
            companyUtil.create(companyToAdd);
        }
        if (object instanceof Category) {
            Category categoryToAdd = (Category) object;
            for (Category category : categoryUtil.getAllCategories()) {
                if (categoryToAdd.getName().equals(category.getName())) {
                    categoryUtil.destroy(category);
                    break;
                }
            }
            categoryUtil.create(categoryToAdd);
        }
    }

    private void importSystemData(Object object) {
        if (object instanceof AccountingSystem) {
            AccountingSystem accountingSystemObject = (AccountingSystem) object;

            AccountingSystem accountingSystem = accountingSystemUtil.getAccountingSystem();
            accountingSystem.setCompany(accountingSystemObject.getCompany());
            accountingSystem.setDateCreated(accountingSystemObject.getDateCreated());
            accountingSystem.setVersion(accountingSystemObject.getVersion());

            accountingSystemUtil.edit(accountingSystem);
        }
    }

    private void importPeopleData(Object object) throws Exception {
        if (object instanceof Person) {
            Person personToAdd = (Person) object;
            for (Person person : personUtil.getAllPeople()) {
                if (personToAdd.getEmail().equals(person.getEmail())) {
                    personUtil.destroy(person);
                    break;
                }
            }
            personUtil.create(personToAdd);
        }
    }

    private void importCompanyData(Object object) throws Exception {
        if (object instanceof Company) {
            Company companyToAdd = (Company) object;
            for (Company company : companyUtil.getAllCompanies()) {
                if (companyToAdd.getEmail().equals(company.getEmail())) {
                    companyUtil.destroy(company);
                    break;
                }
            }
            companyUtil.create(companyToAdd);
        }
    }

    private void importCategoryData(Object object) throws Exception {
        if (object instanceof Category) {
            Category categoryToAdd = (Category) object;
            for (Category category : categoryUtil.getAllCategories()) {
                if (categoryToAdd.getName().equals(category.getName())) {
                    categoryUtil.destroy(category);
                    break;
                }
            }
            categoryUtil.create(categoryToAdd);
        }
    }

}
