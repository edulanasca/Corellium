package Corellium.Ventana.Escritorio;

import Corellium.Ventana.FondoDinamico;
import Corellium.Ventana.Ventana;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


public class EscritorioController {

    @FXML
    BorderPane borderPane;

    @FXML
    Menu bateria;

    private static Parent escritorio;
    Parent barraDeTareas = Ventana.cargar("barraDeTareas.fxml", this.getClass());

    public void initialize() {
        borderPane.setBottom(barraDeTareas);
        FondoDinamico.setFondoDinamico(borderPane, "Corellium/img/Escritorio/Wallpaper.jpg");
    }

    public static void cargarEscritorio(Class clase) {
        if(escritorio == null) {
            try {
                escritorio = FXMLLoader.load(clase.getResource("/Corellium/Ventana/Escritorio/escritorio.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(escritorio);
                stage.setScene(scene);
                stage.setTitle("Corellium v0.1");
                stage.setMaximized(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
