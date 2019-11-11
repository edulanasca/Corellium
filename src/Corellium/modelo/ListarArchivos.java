package Corellium.modelo;

import Corellium.Ventana.ExploradorArchivos.ExploradorDeArchivosController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class ListarArchivos {

    // Todos los atributos se obtienen de ExploradorDeArchivosController
    public static Pane pane;
    public static TextField rutaActual;
    public static Button retroceder;

    public static void crearIconoArchivos(File f) {
        String[] ficheros = f.list();   // Se enlista todos los nombres de los archivos de la carpeta
        if(ficheros != null) {
            pane.getChildren().removeAll(pane.getChildren());
            for(String name: ficheros) {
                crearIconos(name);
            }
        }
    }

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
                    rutaActual.setText(rutaActual.getText() + name + "\\");
                    ExploradorDeArchivosController.historialRuta.add(rutaActual.getText());
                    ExploradorDeArchivosController.indice++;

                    retroceder.setDisable(false);

                    crearIconoArchivos(newSubFile);
                }
            }

            newFile.setOnKeyPressed(keyEvent -> {
                if(keyEvent.getCode().equals(KeyCode.DELETE)) {
                    File borrarArchivo = new File(rutaActual.getText() + ExploradorDeArchivosController.nombreArchivo);
                    Desktop.getDesktop().moveToTrash(borrarArchivo);
                    ListarArchivos.crearIconoArchivos(new File(rutaActual.getText()));
                }
            });
        });
        pane.getChildren().add(newFile);
    }
}
