package accountingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        initializeApplication(primaryStage, loader);
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

}
