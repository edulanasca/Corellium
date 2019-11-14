package Corellium.Ventana.Escritorio;

import Corellium.Ventana.FondoDinamico;
import Corellium.Ventana.Ventana;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.*;


public class EscritorioController {

    @FXML
    BorderPane borderPane;

    Parent barraDeTareas = Ventana.cargar("barraDeTareas.fxml", this.getClass());

    @FXML
    public void initialize() {
        borderPane.setBottom(barraDeTareas);
        FondoDinamico.setFondoDinamico(borderPane, "Corellium/img/Escritorio/Wallpaper.jpg");
    }

}
