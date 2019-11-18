package Corellium.Ventana.Papelera;

import org.jetbrains.annotations.Contract;

public class ArchivoBorrado {

    private String rutaOriginal;
    private String nombreArchivo;

    @Contract(pure = true)
    public ArchivoBorrado(String rutaOriginal, String nombreArchivo) {
        this.rutaOriginal = rutaOriginal;
        this.nombreArchivo = nombreArchivo;
    }

    String getRutaOriginal() {
        return rutaOriginal;
    }

    public void setRutaOriginal(String rutaOriginal) {
        this.rutaOriginal = rutaOriginal;
    }

    String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
