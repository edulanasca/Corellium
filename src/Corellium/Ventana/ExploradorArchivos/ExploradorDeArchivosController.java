package Corellium.Ventana.ExploradorArchivos;

import Corellium.modelo.CopiarArchivo;
import Corellium.modelo.CrearArchivo;
import Corellium.modelo.IconoTipoArchivo;
import Corellium.Ventana.Ventana;
import Corellium.Ventana.VentanaAlerta;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TreeView.EditEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.awt.Desktop;

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

    private final TreeItem<File> nodoRaiz = new TreeItem<>();

    private ArrayList<String> historialRuta = new ArrayList<>();
    private int indice = -1;

    private File copiaOrigen;
    private String nombreArchivo;
    private boolean copiar = false;

    private Optional<ButtonType> opcion;

    public void initialize() {
        ventanaSup.getChildren().add(0, Ventana.cargar("/Corellium/Ventana/barraDeTitulo.fxml", this.getClass()));
        busqueda.textProperty().addListener(this::busquedaArchivo);
        nodoRaiz();
    }

    private void nodoRaiz() {
        //Especificamos la direccion que queremos cargar
//        File froot = new File("Ruta principal");
//        for (File listRoot : froot.listFiles()) { <- Reemplazar
        for (File listRoot : File.listRoots()) {
            TreeItem<File> f = new TreeItem<>(listRoot);
            nodoRaiz.getChildren().add(f);
        }
        nodoRaiz.setExpanded(true);
        tree.setRoot(nodoRaiz);
        tree.setEditable(true);
    }

    @FXML
    private void retroceder(ActionEvent event) {
        indice--;
        String retrocede;

        if (indice == 0) {
            retroceder.setDisable(true);
        }

        retrocede = historialRuta.get(indice);
        rutaActual.setText(retrocede);
        avanzar.setDisable(false);
        crearIconoArchivos(new File(retrocede));
    }

    @FXML
    private void avanzar(ActionEvent event) {
        indice++;
        String avanza = historialRuta.get(indice);
        rutaActual.setText(avanza);
        if (indice + 1 == historialRuta.size()) {
            avanzar.setDisable(true);
        }
        retroceder.setDisable(false);
        crearIconoArchivos(new File(avanza));
    }

    @FXML
    private void copiarBtn(ActionEvent event) {
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
    void pegarBtn(ActionEvent event) {
        File copiaDestino = new File(rutaActual.getText() + nombreArchivo);
        opcion = VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Pegar Archivo",
                "¿Desea pegar este archivo aquí?", this.getClass());

        if ((opcion.isPresent() && opcion.get() == ButtonType.OK) &&
                CopiarArchivo.getInstance().copiar(copiaOrigen.getAbsolutePath(),
                copiaDestino.getAbsolutePath())) {

            File actualizar = new File(rutaActual.getText());
            crearIconoArchivos(actualizar);
            VentanaAlerta.displayAlert(Alert.AlertType.INFORMATION, "Exito",
                    "El archivo ha sido pegado.", this.getClass());
            copiar = false;
        } else {

            VentanaAlerta.displayAlert(Alert.AlertType.ERROR, "Error",
                    "Hubo un error al pegar el archivo.", this.getClass());
        }
    }

    @FXML
    void eliminarBtn(ActionEvent event) {
        File borrarArchivo = new File(rutaActual.getText() + nombreArchivo);
        opcion = VentanaAlerta.displayAlert(Alert.AlertType.CONFIRMATION, "Eliminar Archivo",
                "¿Desea eliminar este archivo?", this.getClass());
        if ((opcion.isPresent() && opcion.get() == ButtonType.OK)
                && Desktop.getDesktop().moveToTrash(borrarArchivo)) {

            VentanaAlerta.displayAlert(Alert.AlertType.INFORMATION, "Exito",
                    "Archivo eliminado.", this.getClass());
            File cortar = new File(rutaActual.getText());
            crearIconoArchivos(cortar);

        } else {

            VentanaAlerta.displayAlert(Alert.AlertType.ERROR, "Error",
                    "Hubo un error al eliminar el archivo.", this.getClass());
        }
    }

    @FXML
    void newArchivo(ActionEvent event){
        // Boton para crear un nuevo archivo
        Ventana.loadFXML("/Corellium/Ventana/ExploradorArchivos/nuevoArchivo.fxml", this.getClass());
        CrearArchivo.rutaActual = rutaActual.getText();
    }

    private void busquedaArchivo(ObservableValue<? extends String> observable,
                                 String oldValue, String newValue) {
        flowPane.getChildren().removeAll(flowPane.getChildren());
        File f = new File(rutaActual.getText());
        String[] ficheros = f.list();
        if (ficheros != null) {
            for (String name : ficheros) {
                if (name.matches(".*(" + busqueda.getText() + ").*")) {
                    crearIconos(name);
                }
            }
        }
    }

    @FXML
    void editTree(EditEvent event) {
        TreeItem<File> nodoSeleccionado = tree.getEditingItem();
        crearIconoArchivos(nodoSeleccionado.getValue());
        rutaActual.setText(nodoSeleccionado.getValue().getAbsolutePath() + "\\");
        historialRuta.add(nodoSeleccionado.getValue().getAbsolutePath() + "\\");
        indice++;
        crearDirectorioArchivos(nodoSeleccionado);
    }

    private void crearIconoArchivos(File f) {
        String[] ficheros = f.list();   // Se enlista todos los nombres de los archivos de la carpeta
        if (ficheros != null) {
            flowPane.getChildren().removeAll(flowPane.getChildren());
            for (String name : ficheros) {
                crearIconos(name);
            }
        }
    }

    private void crearIconos(String name) {
        VBox vBox = new VBox();
        vBox.setId("contenedor");
        ImageView imgFile = new ImageView(IconoTipoArchivo.IconoArchivo(name));
        imgFile.setFitHeight(90);
        imgFile.setFitWidth(90);
        Label nameFile = new Label(name);
//                nameFile.setId("name");
        vBox.getChildren().addAll(imgFile, nameFile);
        ToggleButton newFile = new ToggleButton("", vBox);
        newFile.setId("archivoBoton");
        newFile.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1 && !event.isConsumed()) {
                event.consume();
                if (!copiar) {
                    /* Previene que nombreArchivo cambie de valor
                               una vez el usuario ha copiado algun archivo
                     */
                    nombreArchivo = name;
                }
            } else if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                File newSubFile = new File(rutaActual.getText() + name);
                if (!newSubFile.isDirectory()) {
                    try {
                        File objFile = new File(rutaActual.getText() + name);
                        Desktop.getDesktop().open(objFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    rutaActual.setText(rutaActual.getText() + name + "\\");
                    historialRuta.add(rutaActual.getText());
                    indice++;

                    retroceder.setDisable(false);

                    crearIconoArchivos(newSubFile);
                }
            }

        });
        flowPane.getChildren().add(newFile);
    }

    private void crearDirectorioArchivos(TreeItem<File> NodoPadre) {
        if (NodoPadre != nodoRaiz) {
            File fraiz = new File(NodoPadre.getValue().getAbsolutePath());
            if (fraiz.isDirectory()) {
                if (NodoPadre.getChildren().size() > 0) {
                    NodoPadre.getChildren().removeAll(NodoPadre.getChildren());
                    for (File listaArchivos : Objects.requireNonNull(fraiz.listFiles())) {
                        TreeItem<File> hijo = new TreeItem<>(listaArchivos.getAbsoluteFile());
                        hijo.getChildren().removeAll(hijo.getChildren());
                    }
                }

                if (fraiz.listFiles() != null) {
                    for (File list : Objects.requireNonNull(fraiz.listFiles())) {
                        TreeItem<File> hijo = new TreeItem<>(list.getAbsoluteFile());
                        NodoPadre.getChildren().add(hijo);
                        File fhijo = new File(hijo.getValue().getAbsolutePath());
                        if (fhijo.getName().matches("(.*)\\.([a-z1-9]*)$")) {
                            NodoPadre.getChildren().remove(hijo);
                        }
                    }
                }
            }
        }
    }
}
