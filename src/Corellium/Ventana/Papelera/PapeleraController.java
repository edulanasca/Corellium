package Corellium.Ventana.Papelera;

import Corellium.Ventana.ExploradorArchivos.modelo.CopiarArchivo;
import Corellium.Ventana.ExploradorArchivos.modelo.ListarArchivos;
import Corellium.Ventana.Ventana;
import Corellium.Ventana.VentanaAlerta;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PapeleraController {

    @FXML
    private VBox ventanaSup;
    @FXML
    private TextField busqueda;
    @FXML
    private FlowPane listadoArchivos;

    /**
     * RUTA, ruta de la papelera
     * ficherosBorrados, lista de los ficheros borrados
     * indexSelected, indice del fichero seleccionado dentro de listadoArchivo
     */
    private final String RUTA = "src/Corellium/Papelera";
    public static List<ArchivoBorrado> ficherosBorrados = new ArrayList<>();
    private int indexSelected = 0;

    public void initialize() {
        if(ListarArchivos.rutaActual != null){
            ListarArchivos.rutaActual.clear();
        }

        ventanaSup.getChildren().add(0, Ventana.barraTitulo(this.getClass()));
        ListarArchivos.pane = listadoArchivos;
        ListarArchivos.crearIconoArchivos(new File(RUTA));
        busqueda.textProperty().addListener(this::busquedaArchivo);
    }

    @FXML
    void restaurar() {
        VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Eliminar fichero",
                "¿Desea restaurar este fichero?", this.getClass()).
                filter(response -> response == ButtonType.OK).ifPresent(response -> {
            seleccionado();
            ArchivoBorrado borrado = ficherosBorrados.get(indexSelected);
            CopiarArchivo.getInstance().copiar(RUTA + "/" + borrado.getNombreArchivo(), borrado.getRutaOriginal());
            File restaurado = new File(RUTA + "/" + borrado.getNombreArchivo());
            if (restaurado.delete()) VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Restaurar",
                    "Archivo restaurado", this.getClass());
            ficherosBorrados.remove(indexSelected);
            ListarArchivos.crearIconoArchivos(new File(RUTA));
        });
    }

    @FXML
    void restaurarTodos() {

        VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Eliminar fichero",
                "¿Desea restaurar todos los  ficheros?", this.getClass()).
                filter(response -> response == ButtonType.OK).ifPresent(response -> {
            for (int i = 0; i < listadoArchivos.getChildren().size(); i++) {
                ArchivoBorrado borrado = ficherosBorrados.get(i);
                CopiarArchivo.getInstance().copiar(RUTA + "/" + borrado.getNombreArchivo(), borrado.getRutaOriginal());
                File f = new File(RUTA + "/" + borrado.getNombreArchivo());
                if (!f.delete()) {
                    VentanaAlerta.displayAlert(Alert.AlertType.ERROR, "Error",
                            "Hubo un error al borrar el fichero.", this.getClass());
                    break;
                }
            }
            List<ArchivoBorrado> lista = ficherosBorrados;
            ficherosBorrados.removeAll(lista);
            ListarArchivos.crearIconoArchivos(new File(RUTA));
        });

        VentanaAlerta.displayAlert(Alert.AlertType.INFORMATION, "Ficheros restaurados",
                "Todos los ficheros han sido restaurados", this.getClass());
    }

    @FXML
    void eliminadoPermanente() {
        VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Eliminar fichero",
                "¿Desea eliminar este fichero de manera permanente?", this.getClass()).
                filter(response -> response == ButtonType.OK).ifPresent(response -> {
            seleccionado();
            ArchivoBorrado borrado = ficherosBorrados.get(indexSelected);
            File f = new File(RUTA + "/" + borrado.getNombreArchivo());
            if (f.delete()) VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Eliminar Fichero",
                    "Fichero eliminado permanentemente", this.getClass());
        });
    }

    @FXML
    void vaciarPapelera() {
        VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Vaciar papelera",
                "¿Desea vaciar la papelera?", this.getClass()).filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    for (int i = 0; i < listadoArchivos.getChildren().size(); i++) {
                        ArchivoBorrado borrado = ficherosBorrados.get(i);
                        File f = new File(RUTA + "/" + borrado.getNombreArchivo());
                        if (!f.delete()) {
                            VentanaAlerta.displayAlert(Alert.AlertType.ERROR, "Error",
                                    "Hubo un error al borrar el fichero.", this.getClass());
                            break;
                        }
                    }
                    List<ArchivoBorrado> lista = ficherosBorrados;
                    ficherosBorrados.removeAll(lista);
                    ListarArchivos.crearIconoArchivos(new File(RUTA));
                    VentanaAlerta.displayAlert(Alert.AlertType.INFORMATION, "Ficheros restaurados",
                            "La papelera fue vaciada", this.getClass());
                });
    }

    private void seleccionado() {
        for (int n = 0; n < listadoArchivos.getChildren().size(); n++) {
            ToggleButton button = (ToggleButton) listadoArchivos.getChildren().get(n);
            if (button.isSelected()) {
                indexSelected = n;
                break;
            }
        }
    }

    private void busquedaArchivo(ObservableValue<? extends String> observable,
                                 String oldValue, String newValue) {
        listadoArchivos.getChildren().removeAll(listadoArchivos.getChildren());
        File f = new File(RUTA);
        String[] ficheros = f.list();
        if (ficheros != null) {
            for (String name : ficheros) {
                if (name.matches(".*(" + busqueda.getText() + ").*")) {
                    ListarArchivos.crearIconos(name);
                }
            }
        }
    }

}
