package Corellium.Ventana.Sesion;

import Corellium.Ventana.FondoDinamico;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

public class SesionController {

    @FXML
    StackPane background;

    public Menu sistema;
    public Menu bateria;
    public Menu usuario;
    public Menu info;

    public void initialize() {
        FondoDinamico.blurEffect(background);
        FondoDinamico.setFondoDinamico(background, "Corellium/img/Escritorio/Wallpaper.jpg");
    }

    @FXML
    void bateriaAction() {
        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        ObservableList<MenuItem> list = bateria.getItems();
        list.get(0).setText(batteryStatus.getACLineStatusString());
        list.get(1).setText(batteryStatus.getBatteryLifePercent());
    }

    @FXML
    void setUsuario() {

    }

    @FXML
    void setInfo() {

    }

}
