package accountingsystem.controller;

import accountingsystem.hibernate.model.Company;
import accountingsystem.hibernate.model.Person;
import accountingsystem.hibernate.util.CompanyUtil;
import accountingsystem.hibernate.util.PersonUtil;
import accountingsystem.service.ViewService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class UsersController implements Controller {

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

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accountingsystem");
    PersonUtil personUtil = new PersonUtil(entityManagerFactory);
    CompanyUtil companyUtil = new CompanyUtil(entityManagerFactory);

    @Override
    public void updateWindow() {
        loadPeople();
        loadCompanies();
        Stage stage = (Stage) menuBtn.getScene().getWindow();
        stage.show();
    }

    private void loadPeople() {
        peopleList.getItems().clear();

        for (Person person : personUtil.getAllPeople()) {
            peopleList.getItems().add(person.getEmail());
        }
    }

    private void loadCompanies() {
        companiesList.getItems().clear();
        for (Company company : companyUtil.getAllCompanies()) {
            companiesList.getItems().add(company.getEmail());
        }
    }

    //region Menu links

    @FXML
    public void openMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.loadSystemInfo();

        ViewService.openView((Stage) menuBtn.getScene().getWindow(), root);
    }

    @FXML
    public void openPeople() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/People.fxml"));
        Parent root = loader.load();

        PeopleController peopleController = loader.getController();

        ViewService.openView((Stage) peopleBtn.getScene().getWindow(), root);
        peopleController.loadPeople();
    }

    @FXML
    public void openCompanies() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Companies.fxml"));
        Parent root = loader.load();

        CompaniesController companiesController = loader.getController();

        ViewService.openView((Stage) companiesBtn.getScene().getWindow(), root);
        companiesController.loadCompanies();
    }

    //endregion

    //region Import and export

    @FXML
    public void openExport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Export.fxml"));
        Parent root = loader.load();

        ExportController exportController = loader.getController();
        exportController.populateDataTypes();

        ViewService.newWindow(root, "Export");
    }

    @FXML
    public void openImport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Import.fxml"));
        Parent root = loader.load();

        ImportController importController = loader.getController();
        importController.populateDataTypes();
        importController.setController(this);

        ViewService.newWindow(root, "Import");
    }

    //endregion

}
