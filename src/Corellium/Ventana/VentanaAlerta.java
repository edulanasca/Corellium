package Corellium.Ventana;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class VentanaAlerta {
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static Optional<ButtonType> displayAlert(Alert.AlertType type, String title, String message, Class clase) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(clase.
                getResource("/Corellium/css/ventanaAlerta.css").toExternalForm());
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.getDialogPane().setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        alert.getDialogPane().setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.initStyle(StageStyle.UNDECORATED);
        return alert.showAndWait();
    }
}
