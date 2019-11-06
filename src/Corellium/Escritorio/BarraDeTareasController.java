package Corellium.Escritorio;

import Corellium.Ventana.Ventana;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class BarraDeTareasController {

    @FXML
    GridPane parent;

    @FXML
    public void initialize() {
    }

    @FXML
    void instanciarPrograma(ActionEvent event) {
        // Toma el
       Button button = (Button)event.getSource();
       String programa = button.getId();

        switch(programa) {
            case "exploradorDeArchivos":
                Ventana.instanciar("/Corellium/Ventana/ExploradorArchivos/exploradorDeArchivos.fxml",
                        (BorderPane)parent.getParent(),this.getClass()); break;
            case "web":
                Ventana.instanciar("/Corellium/Ventana/Web/web.fxml",
                        (BorderPane)parent.getParent(),this.getClass()); break;
        }
    }
}
