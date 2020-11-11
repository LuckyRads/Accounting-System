package accountingsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import accountingsystem.model.AccountingSystem;
import accountingsystem.model.Company;
import accountingsystem.model.Person;
import accountingsystem.service.ViewService;

import java.io.IOException;

public class UsersController implements Controller {

    private AccountingSystem accountingSystem;

    @FXML
    private Button menuBtn;

    @FXML
    private Button peopleBtn;

    @FXML
    private Button companiesBtn;

    @FXML
    private ListView peopleList;

    @FXML
    private ListView companiesList;

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    @Override
    public void updateWindow() {
        loadPeople();
        loadCompanies();
        Stage stage = (Stage) menuBtn.getScene().getWindow();
        stage.show();
    }

    private void loadPeople() {
        peopleList.getItems().clear();

        for (Person person : accountingSystem.getPeople()) {
            peopleList.getItems().add(person.getEmail());
        }
    }

    private void loadCompanies() {
        companiesList.getItems().clear();
        for (Company company : accountingSystem.getCompanies()) {
            companiesList.getItems().add(company.getEmail());
        }
    }

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setAccountingSystem(accountingSystem);
        mainMenuController.loadSystemInfo();

        ViewService.openView((Stage) menuBtn.getScene().getWindow(), root);
    }

    @FXML
    public void openPeople() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/People.fxml"));
        Parent root = loader.load();

        PeopleController peopleController = loader.getController();
        peopleController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) peopleBtn.getScene().getWindow(), root);
        peopleController.loadPeople();
    }

    @FXML
    public void openCompanies() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Companies.fxml"));
        Parent root = loader.load();

        CompaniesController companiesController = loader.getController();
        companiesController.setAccountingSystem(accountingSystem);

        ViewService.openView((Stage) companiesBtn.getScene().getWindow(), root);
        companiesController.loadCompanies();
    }

    //region importAndExport

    @FXML
    public void openExport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Export.fxml"));
        Parent root = loader.load();

        ExportController exportController = loader.getController();
        exportController.setAccountingSystem(accountingSystem);
        exportController.populateDataTypes();

        ViewService.newWindow(root, "Export");
    }

    @FXML
    public void openImport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Import.fxml"));
        Parent root = loader.load();

        ImportController importController = loader.getController();
        importController.setAccountingSystem(accountingSystem);
        importController.populateDataTypes();
        importController.setController(this);

        ViewService.newWindow(root, "Import");
    }

    //endregion

}
