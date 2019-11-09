package Corellium.modelo;

import Corellium.Ventana.ExploradorArchivos.ExploradorDeArchivosController;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.Objects;

public class CrearArchivo {

    public static String rutaActual;
    public static String nombreArchivo;

    public static void crearDirectorioArchivos(TreeItem<File> NodoPadre) {
        if (NodoPadre != ExploradorDeArchivosController.nodoRaiz) {
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

    public static void crearArchivo(String rutaActual, String nombreArchivo, Tipo tipoArchivo) {
        File file = new File(rutaActual + nombreArchivo);
        if(tipoArchivo.equals(Tipo.CARPETA)) {
            if(file.mkdir()){
                System.out.println("It works");
            }
        }
    }
}
