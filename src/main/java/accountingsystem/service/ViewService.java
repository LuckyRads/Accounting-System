package main.java.accountingsystem.service;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewService {

    public static void openView(Stage stage, Parent root) throws IOException {
        stage.setScene(new Scene(root));
        stage.show();
    }

}
