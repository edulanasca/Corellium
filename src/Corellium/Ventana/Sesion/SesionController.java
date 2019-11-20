package Corellium.Ventana.Sesion;

import Corellium.DAO.DAOFactory;
import Corellium.DAO.design.IUsuarioDAO;
import Corellium.Ventana.FondoDinamico;
import Corellium.Ventana.Sesion.Usuario.IniciarSesionController;
import Corellium.Ventana.Ventana;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class SesionController {

    @FXML
    StackPane scene;
    @FXML
    StackPane background;
    @FXML
    GridPane sesionCentro;

    public Menu sistema;
    public Menu bateria;
    public Menu usuario;
    public Menu info;

    private static Parent sesion;
    // Carga la instancia única de la ventana InicioUsuario
    private AnchorPane inicioUsuario = (AnchorPane) IniciarSesionController.cargarInicioUsuario(this.getClass());
    public static GridPane stSesionCentro; // Para cargar las ventanas en el centro del grid
    private Label horaActual; // Label para la hora de inicioUsuario
    public static Timer timerHora;
    public static String usuarioActual; // Nombre del usuario del que iniciará sesión

    public void initialize() {
        FondoDinamico.blurEffect(background);
        FondoDinamico.setFondoDinamico(background, "Corellium/img/Escritorio/Wallpaper.jpg");
        sesionCentro.add(inicioUsuario,1,1);
        horaActual = (Label)inicioUsuario.getChildren().get(0);
        stSesionCentro = sesionCentro;
        updateHora();
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
    void setUsuario() {
        // Muestra todos los usuarios registrados en MenuItems
        usuario.getItems().clear();
        IUsuarioDAO usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
        Label labelUsuario = (Label)inicioUsuario.getChildren().get(1);
        usuarioDAO.getUsuarios().forEach(each -> {
            MenuItem item = new MenuItem(each.getNombre());
            item.setOnAction(event -> {
                labelUsuario.setText("Bienvenido " + each.getNombre());
                usuarioActual = each.getNombre();
            });
            usuario.getItems().add(item);
        });

        MenuItem agregarUsuario = new MenuItem("Agregar usuario");
        agregarUsuario.setOnAction(event -> {
            Parent p = Ventana.cargar("/Corellium/Ventana/Sesion/Usuario/crearUsuario.fxml",this.getClass());
            sesionCentro.getChildren().clear();
            sesionCentro.add(p,1,1);

        });
        usuario.getItems().addAll(new SeparatorMenuItem(), agregarUsuario);
    }

    @FXML
    void setInfo() {

    }

    @FXML
    void apagar() {
        Stage stage = (Stage)scene.getScene().getWindow();
        stage.close();
    }

    private void updateHora() {
        timerHora = new Timer();
        timerHora.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> horaActual.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
            }
        }, 0, 1000);
    }

    public static void cargarSesion(Class clase) {
        if(sesion == null) {
            try {
                sesion = FXMLLoader.load(clase.getResource("/Corellium/Ventana/Sesion/sesion.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(sesion);
                stage.setScene(scene);
                stage.setTitle("Corellium v0.1");
                stage.setMaximized(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
