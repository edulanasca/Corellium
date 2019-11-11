package Corellium.modelo;

import Corellium.Ventana.ExploradorArchivos.ExploradorDeArchivosController;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CrearArchivo {

    public static ListView<Tipo> listaArchivos;

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

    public static void crearArchivo(String rutaActual, String nombreArchivo, Tipo tipoArchivo) {
        // Crear un archivo del tipo Tipo especificado, el nombreArchivo lo obtiene de NuevoArchivoController
        // y la rutaActual de ExploradorDeArchivosController
        String ruta = rutaActual + nombreArchivo;
        File file = new File(ruta);

        if(tipoArchivo.equals(Tipo.CARPETA)) {
            file = new File(ruta);
            if(file.mkdir()) {
                System.out.println("Carpetita");
            }
        } else if(tipoArchivo.equals(Tipo.TEXTO)) {
            file = new File(ruta + ".txt");
        } else if(tipoArchivo.equals(Tipo.WORD)) {
            file = new File(ruta + ".docx");
        } else if(tipoArchivo.equals(Tipo.POWERPOINT)) {
            file = new File(ruta + ".pptx");
        }

        try {
            if(file.createNewFile()) {
                System.out.println("Creado");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
