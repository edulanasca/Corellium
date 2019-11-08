package Corellium.Ventana.ExploradorArchivos;

import Corellium.modelo.IconoTipoArchivo;
import Corellium.modelo.Tipo;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NuevoArchivoController {

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
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            Stage stage = (Stage) nombreArchivo.getScene().getWindow();
            stage.close();
        }
    }

}
