package Corellium.Ventana.ExploradorArchivos;

import Corellium.Ventana.Papelera.ArchivoBorrado;
import Corellium.Ventana.Papelera.PapeleraController;
import Corellium.Ventana.ExploradorArchivos.modelo.CopiarArchivo;
import Corellium.Ventana.ExploradorArchivos.modelo.CrearArchivo;
import Corellium.Ventana.ExploradorArchivos.modelo.ListarArchivos;
import Corellium.Ventana.Ventana;
import Corellium.Ventana.VentanaAlerta;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class ExploradorDeArchivosController {

    @FXML
    BorderPane borderPane;
    @FXML
    private VBox ventanaSup;
    @FXML
    private TreeView<File> tree;
    @FXML
    private TextField rutaActual;
    @FXML
    private Button retroceder;
    @FXML
    private Button avanzar;
    @FXML
    private TextField busqueda;
    @FXML
    private FlowPane flowPane;

    private TreeItem<File> nodoRaiz = new TreeItem<>();

    public static ArrayList<String> historialRuta = new ArrayList<>();
    public static int indice = -1;

    private File copiaOrigen;
    public static String nombreArchivo;
    public static boolean copiar = false;

    private Optional<ButtonType> opcion;

    public void initialize() {
        ventanaSup.getChildren().add(0, Ventana.barraTitulo(this.getClass()));
        busqueda.textProperty().addListener(this::busquedaArchivo);
        nodoRaiz();
        rutaActual.setText("D:\\");
        ListarArchivos.pane = flowPane;
        ListarArchivos.rutaActual = rutaActual;
        ListarArchivos.retroceder = retroceder;
        ListarArchivos.crearIconoArchivos(new File("D:\\"));
    }

    private void nodoRaiz() {
        //Especificamos la direccion que queremos cargar
//        File froot = new File("Ruta principal");
//        for (File listRoot : froot.listFiles()) { <- Reemplazar
        for (File listRoot : File.listRoots()) {
            TreeItem<File> f = new TreeItem<>(listRoot);
            nodoRaiz.getChildren().add(f);
        }
        File papelera = new File("src/Corellium/Papelera");
        nodoRaiz.getChildren().add(new TreeItem<>(papelera.getAbsoluteFile()));
        nodoRaiz.setExpanded(true);
        tree.setRoot(nodoRaiz);
        tree.setEditable(true);
    }

    @FXML
    private void retroceder() {
        // ActionEvent: retroceder en el historial
        indice--;
        String retrocede;

        if (indice == 0) {
            retroceder.setDisable(true);
        }

        retrocede = historialRuta.get(indice);
        rutaActual.setText(retrocede);
        avanzar.setDisable(false);
        ListarArchivos.crearIconoArchivos(new File(retrocede));
    }

    @FXML
    private void avanzar() {
        // ActionEvent: avanzar en el historial
        indice++;
        String avanza = historialRuta.get(indice);
        rutaActual.setText(avanza);
        if (indice + 1 == historialRuta.size()) {
            avanzar.setDisable(true);
        }
        retroceder.setDisable(false);
        ListarArchivos.crearIconoArchivos(new File(avanza));
    }

    @FXML
    private void copiarBtn() {
        // ActionEvent: copiar archivo
        copiaOrigen = new File(rutaActual.getText() + nombreArchivo);

        if (copiaOrigen.exists()) {
            opcion = VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Copiar Archivo",
                    "¿Desea copiar este archivo?", this.getClass());

            if (opcion.isPresent() && opcion.get() == ButtonType.OK) {
                copiar = true;
                VentanaAlerta.displayAlert(Alert.AlertType.INFORMATION, "Exito",
                        "El archivo ha sido copiado.", this.getClass());
            }
        } else {
            VentanaAlerta.displayAlert(Alert.AlertType.ERROR, "Error",
                    "Seleccione un archivo.", this.getClass());
        }
    }

    @FXML
    void pegarBtn() {
        // ActionEvent: pegar archivo
        File copiaDestino = new File(rutaActual.getText() + nombreArchivo);
        opcion = VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Pegar Archivo",
                "¿Desea pegar este archivo aquí?", this.getClass());

        if ((opcion.isPresent() && opcion.get() == ButtonType.OK) &&
                CopiarArchivo.getInstance().copiar(copiaOrigen.getAbsolutePath(), copiaDestino.getAbsolutePath())) {

            ListarArchivos.crearIconoArchivos(new File(rutaActual.getText()));
            VentanaAlerta.displayAlert(Alert.AlertType.INFORMATION, "Exito",
                    "El archivo ha sido pegado.", this.getClass());
            copiar = false;
        } else {
            VentanaAlerta.displayAlert(Alert.AlertType.ERROR, "Error",
                    "Hubo un error al pegar el archivo.", this.getClass());
        }
    }

    @FXML
    void eliminarBtn() {
        // ActionEvent: eliminar archivo
        copiaOrigen = new File(rutaActual.getText() + nombreArchivo);
        File papelera = new File("src/Corellium/Papelera");
        opcion = VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Eliminar Archivo",
                "¿Desea eliminar este archivo?", this.getClass());
        if ((opcion.isPresent() && opcion.get() == ButtonType.OK)) {
            String origen = copiaOrigen.getAbsolutePath();
            CopiarArchivo.getInstance().copiar(origen,papelera.getAbsolutePath()+ "/" + nombreArchivo);
            PapeleraController.ficherosBorrados.add(new ArchivoBorrado(origen, nombreArchivo));
            if(copiaOrigen.delete()) {
                ListarArchivos.crearIconoArchivos(new File(rutaActual.getText()));
                VentanaAlerta.displayAlert(Alert.AlertType.INFORMATION, "Exito",
                        "Archivo eliminado.", this.getClass());
            }
        } else {
            VentanaAlerta.displayAlert(Alert.AlertType.ERROR, "Error",
                    "Hubo un error al eliminar el archivo.", this.getClass());
        }
    }

    @FXML
    void newArchivo(){
        // ActionEvent: Crear un nuevo archivo
        CrearArchivo.rutaActual = rutaActual.getText();
        Ventana.loadFXML("/Corellium/Ventana/ExploradorArchivos/nuevoArchivo.fxml", this.getClass());
    }

    private void busquedaArchivo(ObservableValue<? extends String> observable,
                                 String oldValue, String newValue) {
        flowPane.getChildren().removeAll(flowPane.getChildren());
        File f = new File(rutaActual.getText());
        String[] ficheros = f.list();
        if (ficheros != null) {
            for (String name : ficheros) {
                if (name.matches(".*(" + busqueda.getText() + ").*")) {
                    ListarArchivos.crearIconos(name);
                }
            }
        }
    }

    @FXML
    void editTree() {
        // Enlista todos los archivos del sistema y los actualiza conforme se hagan modificaciones en estos
        TreeItem<File> nodoSeleccionado = tree.getEditingItem();
        ListarArchivos.crearIconoArchivos(nodoSeleccionado.getValue());
        rutaActual.setText(nodoSeleccionado.getValue().getAbsolutePath() + "\\");
        historialRuta.add(nodoSeleccionado.getValue().getAbsolutePath() + "\\");
        indice++;
        CrearArchivo.crearDirectorioArchivos(nodoSeleccionado, nodoRaiz);
    }
}
