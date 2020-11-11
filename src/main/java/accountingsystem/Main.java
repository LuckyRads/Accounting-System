package accountingsystem;

import accountingsystem.controller.LoginController;
import accountingsystem.model.AccountingSystem;
import accountingsystem.model.Category;
import accountingsystem.model.Company;
import accountingsystem.model.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        initializeApplication(primaryStage, loader);

        AccountingSystem accountingSystem = initializeAccountingSystem();

        initializeControllers(accountingSystem, loader);
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void initializeApplication(Stage primaryStage, FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        primaryStage.setTitle("Accounting System");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    private static AccountingSystem initializeAccountingSystem() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 5);

        return new AccountingSystem(
                "VGTU",
                calendar.getTime(),
                "1.0.0",
                new ArrayList<Category>(),
                new ArrayList<Person>(),
                new ArrayList<Company>()
        );
    }

    private static void initializeControllers(AccountingSystem accountingSystem, FXMLLoader loader) {
        LoginController loginController = loader.getController();
        loginController.setAccountingSystem(accountingSystem);
    }

}
