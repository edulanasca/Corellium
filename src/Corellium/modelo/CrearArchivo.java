package Corellium.modelo;

import java.io.File;

public class CrearArchivo {

    public static String rutaActual;
    public static String nombreArchivo;

    public static void crearArchivo(String rutaActual, String nombreArchivo, Tipo tipoArchivo) {
        File file = new File(rutaActual + nombreArchivo);
        if(tipoArchivo.equals(Tipo.CARPETA)) {
            if(file.mkdir()){
                System.out.println("It works");
            }
        }
    }
}
