package Corellium.Ventana.Escritorio;

import Corellium.Ventana.Ventana;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BarraDeTareasController {

    @FXML
    GridPane parent;

    public static boolean ventanaExterna = false;
    private boolean doc = false;

    @FXML
    public void initialize() {
    }

    @FXML
    void instanciarPrograma(@NotNull ActionEvent event) throws IOException {
        // Toma el
        BorderPane escritorio = (BorderPane) parent.getScene().getRoot();
        Button button = (Button) event.getSource();
        String programa = button.getId();
        ventanaExterna = false;
        switch (programa) {
            case "exploradorDeArchivos":
                Ventana.instanciar("/Corellium/Ventana/ExploradorArchivos/exploradorDeArchivos.fxml",
                        (BorderPane) parent.getParent(), this.getClass());
                break;
            case "web":
                Ventana.instanciar("/Corellium/Ventana/Web/web.fxml",
                        (BorderPane) parent.getParent(), this.getClass());
                break;
            case "pingPong":
                ventanaExterna = true;
                Ventana.loadFXML("/Corellium/Ventana/Juegos/Pingpong/pingPong.fxml", this.getClass());
//                Ventana.instanciar("/Corellium/Ventana/Juegos/Pingpong/pingPong.fxml",
//                        (BorderPane) parent.getParent(), this.getClass());
                break;
            case "calculadora":
                ventanaExterna = true;
                Ventana.loadFXML("/Corellium/Ventana/Calculadora/calculadora.fxml", this.getClass());
                break;
            case "papelera":
                Ventana.instanciar("/Corellium/Ventana/Papelera/papelera.fxml",
                        (BorderPane) parent.getParent(), this.getClass());
                break;
            case "blocNotas":
                doc = true;
                Desktop.getDesktop().open(new File("src/Corellium/Ventana/Escritorio/" +
                        "Default/Sin título.txt"));
                break;
            case "word":
                doc = true;
                Desktop.getDesktop().open(new File("src/Corellium/Ventana/Escritorio/" +
                        "Default/Nuevo Documento de Microsoft Word.docx"));
                break;
            case "excel":
                doc = true;
                Desktop.getDesktop().open(new File("src/Corellium/Ventana/Escritorio/" +
                        "Default/Nuevo Hoja de cálculo de Microsoft Excel.xlsx"));
                break;
            case "powerPoint":
                doc = true;
                Desktop.getDesktop().open(new File("src/Corellium/Ventana/Escritorio/" +
                        "Default/Nuevo Presentación de Microsoft PowerPoint.pptx"));
                break;
        }

        if (!ventanaExterna && !doc) escritorio.setBottom(null);

    }
}
