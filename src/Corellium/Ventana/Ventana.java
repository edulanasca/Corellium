package Corellium.Ventana;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Ventana {

    public static Parent cargar(String fxml, Class clase) {
        try {
            return FXMLLoader.load(clase.getResource(fxml));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void instanciar(String fxml, BorderPane borderPane, Class clase) {
        try {
            if(borderPane.getCenter() == null){
                borderPane.setCenter(FXMLLoader.load(clase.getResource(fxml)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
