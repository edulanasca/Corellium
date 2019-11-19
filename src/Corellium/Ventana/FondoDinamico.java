package Corellium.Ventana;

import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
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

    public static void blurEffect(Node node){
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(7.0);
        node.setEffect(blur);
    }

}
