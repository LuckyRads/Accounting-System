package main.java.accountingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.accountingsystem.model.AccountingSystem;
import main.java.accountingsystem.model.Company;
import main.java.accountingsystem.model.Person;
import main.java.accountingsystem.model.SubCategory;

import java.util.ArrayList;
import java.util.Calendar;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("Accounting System");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 5);

        AccountingSystem accountingSystem = new AccountingSystem(
                "VGTU",
                calendar.getTime(),
                "1.0.0",
                new ArrayList<SubCategory>(),
                new ArrayList<Person>(),
                new ArrayList<Company>()
        );

//        while (true) {
//            MenuService.showMenu(accountingSystem);
//        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
