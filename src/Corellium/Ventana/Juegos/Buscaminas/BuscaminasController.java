package Corellium.Ventana.Juegos.Buscaminas;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class BuscaminasController {
    @FXML
    BorderPane borderPane;
    private PanelBuscaminas panelBuscaminas = new PanelBuscaminas();

    @FXML
    void initialize() {
        borderPane.setCenter(panelBuscaminas.llenarPanel());
    }

}
