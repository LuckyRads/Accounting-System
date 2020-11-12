package accountingsystem.controller;

import accountingsystem.service.ViewService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerPersonBtn;

    @FXML
    private Button registerCompanyBtn;

    @FXML
    public void login() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.loadSystemInfo();

        ViewService.openView((Stage) loginBtn.getScene().getWindow(), root);
    }

    @FXML
    public void registerPerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterPerson.fxml"));
        Parent root = loader.load();

        RegisterPersonController registerPersonController = loader.getController();

        ViewService.openView((Stage) registerPersonBtn.getScene().getWindow(), root);
    }

    @FXML
    public void registerCompany() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterCompany.fxml"));
        Parent root = loader.load();

        RegisterCompanyController registerCompanyController = loader.getController();
        registerCompanyController.populateResponsiblePeopleSelect();

        ViewService.openView((Stage) registerCompanyBtn.getScene().getWindow(), root);
    }

}
