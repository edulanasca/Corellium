package Corellium.Ventana.ExploradorArchivos.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class CrearIconos {

    private static String rutaIcono = "/Corellium/img/ExploradorArchivos/TipoArchivo/";
    private static String rutaIconoNuevoArchivo = "/Corellium/img/ExploradorArchivos/TipoArchivo/NuevoArchivo/";

    public static void iconoNuevoArchivo(ListView<Tipo> listView) {
        // Iconos para la lista de archivos que se pueden crear en el explorador de Archivos
        ObservableList<Tipo> items = FXCollections.observableArrayList(Tipo.values());
        listView.setItems(items);
        listView.setCellFactory(param -> new ListCell<>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(Tipo name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(name.equals(Tipo.CARPETA)) {
                        setText("Carpeta");
                        imageView.setImage(new Image(rutaIconoNuevoArchivo + "icons8-carpeta-25.png"));
                    } else if(name.equals(Tipo.TEXTO)) {
                        setText("Documento de texto");
                        imageView.setImage(new Image((rutaIconoNuevoArchivo + "notes-25.png")));
                    } else if(name.equals(Tipo.WORD)) {
                        setText("Documento de Microsoft Word");
                        imageView.setImage(new Image(rutaIconoNuevoArchivo + "icons8-word-25.png"));
                    } else if(name.equals(Tipo.POWERPOINT)) {
                        setText("Presentación de Microsoft PowerPoint");
                        imageView.setImage(new Image(rutaIconoNuevoArchivo + "icons8-powerpoint-25.png"));
                    } else if(name.equals(Tipo.EXCEL)){
                        setText("Hoja de cálculo de Microsoft Excel");
                        imageView.setImage(new Image(rutaIconoNuevoArchivo + "icons8-excel-25.png"));
                    } else if(name.equals(Tipo.RAR)) {
                        setText("Archivo WinRAR");
                        imageView.setImage(new Image(rutaIcono + "icons8-winrar-90.png"));
                    } else if(name.equals(Tipo.ZIP)) {
                        setText("Archivo WinRAR ZIP");
                        imageView.setImage(new Image(rutaIcono + "icons8-winrar-90.png"));
                    }
                    imageView.setFitHeight(25);
                    imageView.setFitWidth(25);
                    setGraphic(imageView);
                }
            }
        });
    }

    static String IconoArchivo(String nombreArchivo) {
        // Iconos de los archivos que se muestran mientras se navega por el explorador de Archivos
        String[] listaN = nombreArchivo.split("\\.");

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
