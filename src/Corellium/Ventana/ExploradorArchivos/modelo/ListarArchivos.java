package Corellium.Ventana.ExploradorArchivos.modelo;

import Corellium.Ventana.ExploradorArchivos.ExploradorDeArchivosController;
import Corellium.Ventana.Papelera.ArchivoBorrado;
import Corellium.Ventana.Papelera.PapeleraController;
import Corellium.Ventana.VentanaAlerta;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class ListarArchivos {

    // Todos los atributos se obtienen de ExploradorDeArchivosController
    public static Pane pane;    // flowPane para mostrar los archivos
    public static TextField rutaActual;
    public static Button retroceder;

    /**
     * Enlista todos los archivos del directorio en un flowPane
     * @param f Directorio
     */
    public static void crearIconoArchivos(File f) {
        String[] ficheros = f.list();
        if(ficheros != null) {
            pane.getChildren().removeAll(pane.getChildren());
            for(String name: ficheros) {
                crearIconos(name);
            }
        }
    }

    /**
     * Crear los iconos de cada
     * @param name nombre del fichero del directorio
     */
    public static void crearIconos(String name) {
        VBox vBox = new VBox();
        vBox.setId("contenedor");
        ImageView imgFile = new ImageView(CrearIconos.IconoArchivo(name));
        imgFile.setFitHeight(90);
        imgFile.setFitWidth(90);
        Label nameFile = new Label(name);
        vBox.getChildren().addAll(imgFile, nameFile);
        ToggleButton newFile = new ToggleButton("", vBox);
        newFile.setId("archivoBoton");
        newFile.setOnMouseClicked(event -> {
            if(event.getClickCount() == 1 && !event.isConsumed()) {
                event.consume();
                // eliminado <- static
                if(!ExploradorDeArchivosController.copiar) {
                    ExploradorDeArchivosController.nombreArchivo = name;
                }
            } else if(event.getClickCount() == 2 && !event.isConsumed()) {
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
                    // Actualiza la ruta
                    rutaActual.setText(rutaActual.getText() + name + "\\");
                    ExploradorDeArchivosController.historialRuta.add(rutaActual.getText());
                    ExploradorDeArchivosController.indice++;

                    retroceder.setDisable(false);

                    crearIconoArchivos(newSubFile);
                }
            }

            newFile.setOnKeyPressed(keyEvent -> {
                if(keyEvent.getCode().equals(KeyCode.DELETE)) {
                    File papelera = new File("src/Corellium/Papelera");
                    if(rutaActual != null) {
                        File borrarArchivo = new File(rutaActual.getText() + ExploradorDeArchivosController.nombreArchivo);
                        if(!rutaActual.getText().equals("")){
                            String origen = borrarArchivo.getAbsolutePath();
                            CopiarArchivo.getInstance().copiar(origen, papelera.getAbsolutePath() + "/" + borrarArchivo.getName());
                            PapeleraController.ficherosBorrados.add(new ArchivoBorrado(origen, borrarArchivo.getName()));
                            if (borrarArchivo.delete()) ListarArchivos.crearIconoArchivos(new File(rutaActual.getText()));
                        } else {
                            File f = new File(papelera.getAbsolutePath() + "/" + name);
                            if (f.delete()) ListarArchivos.crearIconoArchivos(papelera);
                        }
                    } else {
                        File f = new File(papelera.getAbsolutePath() + "/" + name);
                        if (f.delete()) ListarArchivos.crearIconoArchivos(papelera);
                    }
                }
            });
        });
        pane.getChildren().add(newFile);
    }
}
