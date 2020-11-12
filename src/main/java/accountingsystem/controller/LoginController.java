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
    private Button registerBtn;

    @FXML
    public void login() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.loadSystemInfo();

        ViewService.openView((Stage) loginBtn.getScene().getWindow(), root);
    }

    @FXML
    public void register() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Register.fxml"));
        Parent root = loader.load();

        RegisterController registerController = loader.getController();

        ViewService.openView((Stage) registerBtn.getScene().getWindow(), root);
    }

}
