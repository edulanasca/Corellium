package Corellium.Ventana.Juegos.Pingpong;

import Corellium.Ventana.BarraDeTituloController;
import Corellium.Ventana.Ventana;
import Corellium.Ventana.VentanaAlerta;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

public class PingpongController {

    @FXML
    BorderPane borderPane;
    @FXML
    Canvas canvas;

    @FXML
    void initialize() {
        borderPane.setTop(Ventana.barraTitulo(this.getClass()));
        Control control = new Control();
        control.start(canvas);
    }

}
