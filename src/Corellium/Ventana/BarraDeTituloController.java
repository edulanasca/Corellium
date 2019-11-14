package Corellium.Ventana;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BarraDeTituloController {

    @FXML
    HBox hBox;

//    BorderPane escritorio;
    Parent barraDeTareas = Ventana.cargar("/Corellium/Ventana/Escritorio/barraDeTareas.fxml", this.getClass());


    @FXML
    void cerrarVentana(MouseEvent event) {
        // Cierra la ventana obteniendo la escena actual
        BorderPane escritorio = (BorderPane)hBox.getScene().getRoot();
        escritorio.setCenter(null);
        escritorio.setBottom(barraDeTareas);
    }

    @FXML
    void minimizarVentana(MouseEvent event) {
        // Sujeto a cambios ...
        BorderPane escritorio = (BorderPane)hBox.getScene().getRoot();
        escritorio.setBottom(barraDeTareas);
    }

    @FXML
    void maximizarVentana(MouseEvent event) {
        BorderPane escritorio = (BorderPane)hBox.getScene().getRoot();
        // Esconde la barra de tareas del escritorio maximizando la ventana
        escritorio.setBottom(null);
    }
}
