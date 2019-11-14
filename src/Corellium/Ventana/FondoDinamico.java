package Corellium.Ventana;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class FondoDinamico {

    public static void setFondoDinamico(Pane pane, String url) {
        Image image = new Image(url);
        BackgroundImage bgImage = new BackgroundImage(
                image,                                                 // image
                BackgroundRepeat.NO_REPEAT,                            // repeatX
                BackgroundRepeat.NO_REPEAT,                            // repeatY
                BackgroundPosition.CENTER,                             // position
                new BackgroundSize(-1, -1, false, false, false, true)  // size
        );
        pane.setBackground(new Background(bgImage));
    }
}
