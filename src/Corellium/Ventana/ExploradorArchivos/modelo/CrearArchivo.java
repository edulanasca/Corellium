package Corellium.Ventana.ExploradorArchivos.modelo;

import Corellium.Ventana.ExploradorArchivos.ExploradorDeArchivosController;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CrearArchivo {

    public static String rutaActual;

    public static void crearDirectorioArchivos(TreeItem<File> NodoPadre) {
        // Crea el arbol de archivos del sistema
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

    public static void crearArchivo(String nombreArchivo, Tipo tipoArchivo) throws IOException{
        // Crear un archivo del tipo Tipo especificado, el nombreArchivo lo obtiene de NuevoArchivoController
        // y la rutaActual de ExploradorDeArchivosController
        String ruta = rutaActual + nombreArchivo;
        File file = new File(ruta);
        boolean carpeta = false;

        if(tipoArchivo.equals(Tipo.CARPETA)) {
            file = new File(ruta);
            carpeta = true;
            if(!file.mkdir()) {  throw new IOException(); }
        } else if(tipoArchivo.equals(Tipo.TEXTO)) {
            file = new File(ruta + ".txt");
        } else if(tipoArchivo.equals(Tipo.WORD)) {
            file = new File(ruta + ".docx");
        } else if(tipoArchivo.equals(Tipo.POWERPOINT)) {
            file = new File(ruta + ".pptx");
        } else if(tipoArchivo.equals(Tipo.RAR)) {
            file = new File(ruta + ".rar");
        } else if(tipoArchivo.equals(Tipo.ZIP)) {
            file = new File(ruta + ".zip");
        }

        if(!carpeta) {
            if (!file.createNewFile()) {
                throw new IOException();
            }
        }
    }
}
