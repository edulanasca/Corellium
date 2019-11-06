package Corellium.utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopiarArchivo {

    private static CopiarArchivo copiarArchivo;

    public static CopiarArchivo getInstance() {
        if (copiarArchivo == null) {
            copiarArchivo = new CopiarArchivo();
        }
        return copiarArchivo;
    }

    public boolean copiar(String origen, String destino) {
        File archivoOrigen;
        File archivoDestino;
        FileInputStream in = null;
        FileOutputStream out = null;
        boolean b;

        try {
            archivoOrigen = new File(origen);
            archivoDestino = new File(destino);

            if (b = archivoOrigen.exists()) {
                if (b = archivoOrigen.canRead()) {
                    in = new FileInputStream(archivoOrigen);
                    out = new FileOutputStream(archivoDestino);

                    int c;
                    while((c = in.read()) != -1){
                        out.write(c);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            b = false;
        } finally {
            try{
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
            } catch(IOException ex) {
                ex.printStackTrace(System.out);
                b = false;
            }
        }
        return b;
    }
}
