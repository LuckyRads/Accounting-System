package accountingsystem.service;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewService {

    public static void openView(Stage stage, Parent root) throws IOException {
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void newWindow(Parent root, String title) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

}
