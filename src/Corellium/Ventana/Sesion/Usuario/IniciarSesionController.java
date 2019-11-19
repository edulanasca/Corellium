package Corellium.Ventana.Sesion.Usuario;

import Corellium.DAO.DAOFactory;
import Corellium.DAO.design.IUsuarioDAO;
import Corellium.DAO.to.UsuarioTO;
import Corellium.Ventana.Escritorio.EscritorioController;
import Corellium.Ventana.Sesion.SesionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class IniciarSesionController {

    @FXML
    Label bienvenido;
    @FXML
    Label hora;
    @FXML
    PasswordField pwd;

    private static Parent inicioUsuario;

    public void initialize() {
        bienvenido.setText("Bienvenido S.O.");
    }

    @FXML
    void inicioSesion(){
        // Valida el usuario e inicia el escritorio
        IUsuarioDAO usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
        UsuarioTO usuario = usuarioDAO.getUsuarioByName(SesionController.usuarioActual);
        if(pwd.getText().equals(usuario.getPwd())) {
            Stage stage = (Stage)bienvenido.getScene().getWindow();
            stage.close();
            EscritorioController.cargarEscritorio(this.getClass());
        } else {
            pwd.clear();
        }
    }

    public static Parent cargarInicioUsuario(Class clase) {
        // Carga una única instancia de inicio usuario
        if(inicioUsuario == null) {
            try {
                inicioUsuario = FXMLLoader.load(clase.getResource("/Corellium/Ventana/Sesion/Usuario/iniciarSesion.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return inicioUsuario;
    }

}
