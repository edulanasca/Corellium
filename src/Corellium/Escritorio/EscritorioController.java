package Corellium.Escritorio;

import Corellium.Ventana.Ventana;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class EscritorioController {

    @FXML
    BorderPane borderPane;

    Parent barraDeTareas = Ventana.cargar("barraDeTareas.fxml", this.getClass());

    @FXML
    public void initialize() {
        borderPane.setBottom(barraDeTareas);
        setBackground();
    }

    @FXML
    void setBackground() {
        Image image = new Image("Corellium/img/Escritorio/Wallpaper.jpg");
        BackgroundImage bgImage = new BackgroundImage(
                image,                                                 // image
                BackgroundRepeat.NO_REPEAT,                            // repeatX
                BackgroundRepeat.NO_REPEAT,                            // repeatY
                BackgroundPosition.CENTER,                             // position
                new BackgroundSize(-1, -1, false, false, false, true)  // size
        );
        borderPane.setBackground(new Background(bgImage));
    }


}
