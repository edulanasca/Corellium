package Corellium.Ventana.ExploradorArchivos;

import Corellium.modelo.CrearArchivo;
import Corellium.modelo.IconoTipoArchivo;
import Corellium.modelo.Tipo;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NuevoArchivoController {

    @FXML
    VBox vBox;
    @FXML
    TextField nombreArchivo;
    @FXML
    ListView<Tipo> tipoArchivo;

    @FXML
    public void initialize() {
        IconoTipoArchivo.iconoNuevoArchivo(tipoArchivo);
    }

    @FXML
    private void crearArchivo(KeyEvent keyEvent) {
        // Cuando se crea un archivo ... y cierra ventana
        Stage stage = (Stage) vBox.getScene().getWindow();
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            CrearArchivo.nombreArchivo = nombreArchivo.getText();
            CrearArchivo.crearArchivo(CrearArchivo.rutaActual, CrearArchivo.nombreArchivo, Tipo.CARPETA);
            stage.close();
        } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            stage.close();
        }
    }

}
