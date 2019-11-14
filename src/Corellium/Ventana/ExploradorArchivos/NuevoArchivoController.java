package Corellium.Ventana.ExploradorArchivos;

import Corellium.Ventana.VentanaAlerta;
import Corellium.Ventana.ExploradorArchivos.modelo.CrearArchivo;
import Corellium.Ventana.ExploradorArchivos.modelo.ListarArchivos;
import Corellium.Ventana.ExploradorArchivos.modelo.CrearIconos;
import Corellium.Ventana.ExploradorArchivos.modelo.Tipo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class NuevoArchivoController {

    @FXML
    VBox vBox;
    @FXML
    TextField nombreArchivo;
    @FXML
    ListView<Tipo> tipoArchivo;

    @FXML
    public void initialize() {
        CrearIconos.iconoNuevoArchivo(tipoArchivo);
    }

    @FXML
    private void crearArchivo(KeyEvent keyEvent) {
        // Cuando se crea un archivo ... y cierra ventana
        Stage stage = (Stage)vBox.getScene().getWindow();
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(tipoArchivo.getSelectionModel().isEmpty()) {
                // Si no seleccionas ningun elemento de la lista crea por defecto una carpeta con el nombre que se le haya asignado
                crear(nombreArchivo.getText(), Tipo.CARPETA);
            } else {
                crear(nombreArchivo.getText(), tipoArchivo.getSelectionModel().getSelectedItem());
            }

            ListarArchivos.crearIconoArchivos(new File(CrearArchivo.rutaActual));
            stage.close();
        } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            stage.close();
        }
    }

    @FXML
    private void crearArchivoDeLista(MouseEvent mouseEvent) {
        Stage stage = (Stage)vBox.getScene().getWindow();
        if (mouseEvent.getClickCount() == 2 && !mouseEvent.isConsumed()) {
            mouseEvent.consume();
            if (nombreArchivo.getText().equals("")) {
                VentanaAlerta.displayAlert(Alert.AlertType.ERROR, "Error al crear archivo",
                        "Ingrese un nombre para el archivo", this.getClass());
            } else {
                crear(nombreArchivo.getText(),
                        tipoArchivo.getSelectionModel().getSelectedItem());
                ListarArchivos.crearIconoArchivos(new File(CrearArchivo.rutaActual));
                stage.close();
            }
        }
    }

    private void crear(String nombre, Tipo tipo) {
        try {
            CrearArchivo.crearArchivo(nombre, tipo);
        } catch (IOException e) {
            VentanaAlerta.displayAlert(Alert.AlertType.ERROR,"Error al crear archivo",
                    "Los nombres de los archivos no pueden contener ninguno de los siguientes" +
                            " caracteres \\ / : * ? \" < > |", this.getClass());
        }
    }

}
