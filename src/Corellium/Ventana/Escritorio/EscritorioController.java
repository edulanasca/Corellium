package Corellium.Ventana.Escritorio;

import Corellium.Ventana.FondoDinamico;
import Corellium.Ventana.Sesion.Kernel32;
import Corellium.Ventana.Sesion.SesionController;
import Corellium.Ventana.Ventana;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


public class EscritorioController {

    @FXML
    BorderPane borderPane;

    public Menu sistema;
    public Menu bateria;
    public Menu usuario;

    private static Parent escritorio;
    Parent barraDeTareas = Ventana.cargar("barraDeTareas.fxml", this.getClass());

    public void initialize() {
        usuario.setText(SesionController.usuarioActual);
        borderPane.setBottom(barraDeTareas);
        FondoDinamico.setFondoDinamico(borderPane, "Corellium/img/Escritorio/Wallpaper.jpg");
    }

    public static void cargarEscritorio(Class clase) {
        if(escritorio == null) {
            try {
                escritorio = FXMLLoader.load(clase.getResource("/Corellium/Ventana/Escritorio/escritorio.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(escritorio);
                stage.setScene(scene);
                stage.setTitle("Corellium v0.1");
                stage.setMaximized(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void bateriaAction() {
        // Muestra el si la bateria está cargando y el porcentaje de ella
        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        ObservableList<MenuItem> list = bateria.getItems();
        list.get(0).setText(batteryStatus.getACLineStatusString());
        list.get(1).setText(batteryStatus.getBatteryLifePercent());
    }

    @FXML
    void cambiarUsuario() {
        Stage stage = (Stage)borderPane.getScene().getWindow();
        stage.close();
        SesionController.cargarSesion(this.getClass());
    }

    @FXML
    void apagar() {
        Stage stage = (Stage)borderPane.getScene().getWindow();
        stage.close();
    }

}
