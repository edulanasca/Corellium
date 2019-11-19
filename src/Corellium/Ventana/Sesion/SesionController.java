package Corellium.Ventana.Sesion;

import Corellium.DAO.DAOFactory;
import Corellium.DAO.design.IUsuarioDAO;
import Corellium.Ventana.FondoDinamico;
import Corellium.Ventana.Sesion.Usuario.IniciarSesionController;
import Corellium.Ventana.Ventana;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.*;

public class SesionController {

    @FXML
    StackPane scene;
    @FXML
    StackPane background;
    @FXML
    BorderPane sesion;
    @FXML
    GridPane sesionCentro;

    public Menu sistema;
    public Menu bateria;
    public Menu usuario;
    public Menu info;

    // Carga la instancia única de la ventana InicioUsuario
    private AnchorPane inicioUsuario = (AnchorPane) IniciarSesionController.cargarInicioUsuario(this.getClass());
    public static GridPane stSesionCentro; // Para cargar las ventanas en el centro del grid
    public static String usuarioActual; // Nombre del usuario del que iniciará sesión

    public void initialize() {
        FondoDinamico.blurEffect(background);
        FondoDinamico.setFondoDinamico(background, "Corellium/img/Escritorio/Wallpaper.jpg");
        sesionCentro.add(inicioUsuario,1,1);
        stSesionCentro = sesionCentro;
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

}
