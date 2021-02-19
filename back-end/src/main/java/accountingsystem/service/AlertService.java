package accountingsystem.service;

import javafx.scene.control.Alert;

public class AlertService {

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

}
