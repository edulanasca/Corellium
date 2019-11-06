package Corellium.utilidades;

public class IconoTipoArchivo {

    public static String IconoArchivo(String nombreArchivo) {
        String[] listaN = nombreArchivo.split("\\.");
        String rutaIcono = "/Corellium/img/ExploradorArchivos/TipoArchivo/";

        if (listaN.length > 0 && nombreArchivo.matches("(.*)\\.([a-z1-9]*)$")) {
            switch (listaN[listaN.length - 1]) {
                case "txt":
                    return rutaIcono + "icons8-txt-90.png";
                case "jpg":
                case "jpeg":
                case "png":
                    return rutaIcono + "icons8-fotos-de-ios-90.png";
                case "mp4":
                    return rutaIcono + "icons8-video-file-90.png";
                case "doc":
                case "docx":
                    return rutaIcono + "icons8-word-90.png";
                case "ppt":
                case "pptx":
                    return rutaIcono + "icons8-powerpoint-90.png";
                case "xls":
                case "xlsx":
                    return rutaIcono + "icons8-xls-90.png";
                case "pdf":
                    return rutaIcono + "icons8-pdf-90.png";
                case "rar":
                case "zip":
                    return rutaIcono + "icons8-winrar-90.png";
                case "java":
                    return rutaIcono + "icons8-texto-sublime-90.png";
                case "jar":
                    return rutaIcono + "icons8-java-96.png";
                case "cpp":
                    return rutaIcono + "icons8-c++-96.png";
                case "py":
                    return rutaIcono + "icons8-python-96.png";
                default:
                    return rutaIcono + "icons8-archivo-90.png";
            }
        } else {
            return rutaIcono + "icons8-carpeta-90.png";
        }
    }
}
