package Corellium.Ventana;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class Ventana {

    private static double xOffset = 0;
    private static double yOffset = 0;
    private static Parent barraTitulo;

    private Ventana() { }


    public static Parent cargar(String fxml, Class clase) {
        try {
            return FXMLLoader.load(clase.getResource(fxml));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void instanciar(String fxml, BorderPane borderPane, Class clase) {
        // Carga una ventana en el centro del borderPane
        try {
            if(borderPane.getCenter() == null){
                borderPane.setCenter(FXMLLoader.load(clase.getResource(fxml)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static Parent loadFXML(String FXML, @NotNull Class clase) {
        // Carga una ventana externa a la actual
        try {
            FXMLLoader fxmlloader = new FXMLLoader(clase.getResource(FXML));
            Parent addUserParent = fxmlloader.load();
            final Stage stage = new Stage();
            Scene scene = new Scene(addUserParent);
            stage.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(true);
            stage.initStyle(StageStyle.UNDECORATED);
            scene.setOnMousePressed((MouseEvent event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            scene.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            stage.show();
            return addUserParent;
        } catch (IOException ex) {
            ex.getStackTrace();
            return null;
        }
    }

    /**
     * YA NO HAY TIME SO PONDRÉ ESTAS FUNCIONES CHAFAS AQUI xdxd (Eduardo del futuro please save us)
     * @param event controlador del evento
     */
    public static void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public static void onMouseDragged(Stage stage, MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public static void minimizar(Stage stage, double height, double width) {
        stage.setHeight(height);
        stage.setWidth(width);
    }

    public static void maximizar(Stage stage) {
        stage.setMaximized(true);
    }

    public static void cerrar(Stage stage) {
        stage.close();
    }
    /**
     * AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
     */

    public static Parent barraTitulo(Class clase) {
        if(barraTitulo == null) {
            try {
                barraTitulo = FXMLLoader.load(clase.getResource("/Corellium/Ventana/barraDeTitulo.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return barraTitulo;
    }
}
