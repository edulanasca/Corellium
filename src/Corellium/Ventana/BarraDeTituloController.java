package Corellium.Ventana;

import Corellium.Ventana.Escritorio.BarraDeTareasController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BarraDeTituloController {

    @FXML
    HBox hBox;
    @FXML
    ImageView cerrar;
    @FXML
    ImageView minimizar;
    @FXML
    ImageView maximizar;

    private Parent barraDeTareas = Ventana.cargar("/Corellium/Ventana/Escritorio/barraDeTareas.fxml", this.getClass());
    private double posX;
    private double posY;

    public void initialize() {
        if (!BarraDeTareasController.ventanaExterna) {
            cerrar.setOnMouseClicked(this::cerrarVentana);
            minimizar.setOnMouseClicked(this::minimizarVentana);
            maximizar.setOnMouseClicked(this::maximizarVentana);
        } else {
            cerrar.setOnMouseClicked(this::cerrarVentanaExterna);
            minimizar.setOnMouseClicked(this::minimizarVentanaExterna);
            maximizar.setOnMouseClicked(this::maximizarVentanaExterna);
        }
    }

    private void cerrarVentana(MouseEvent event) {
        // Cierra la ventana obteniendo la escena actual
        BorderPane escritorio = (BorderPane) hBox.getScene().getRoot();
        escritorio.setCenter(null);
        escritorio.setBottom(barraDeTareas);
    }

    private void minimizarVentana(MouseEvent event) {
        // Sujeto a cambios ...
        BorderPane escritorio = (BorderPane) hBox.getScene().getRoot();
        escritorio.setBottom(barraDeTareas);
    }

    private void maximizarVentana(MouseEvent event) {
        BorderPane escritorio = (BorderPane) hBox.getScene().getRoot();
        // Esconde la barra de tareas del escritorio maximizando la ventana
        escritorio.setBottom(null);
    }

    private void cerrarVentanaExterna(MouseEvent event) {
        Stage stage = (Stage) hBox.getScene().getWindow();
        stage.close();
    }

    private void minimizarVentanaExterna(MouseEvent event) {
        Stage stage = (Stage) hBox.getScene().getWindow();
        stage.setMaximized(false);
        BorderPane root = (BorderPane) hBox.getScene().getRoot();
        stage.setHeight(root.getPrefHeight());
        stage.setWidth(root.getPrefWidth());
        stage.setX(posX);
        stage.setY(posY);

    }

    private void maximizarVentanaExterna(MouseEvent event) {
        Stage stage = (Stage) hBox.getScene().getWindow();
        posX = stage.getX();
        posY = stage.getY();
        stage.setMaximized(true);
    }
}
