package Corellium.Ventana.Sesion.Usuario;

import Corellium.DAO.DAOFactory;
import Corellium.DAO.design.IUsuarioDAO;
import Corellium.DAO.to.UsuarioTO;
import Corellium.Ventana.Sesion.SesionController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CrearUsuarioController {

    @FXML
    AnchorPane crearUsuario;
    @FXML
    TextField nombre;
    @FXML
    PasswordField pwd;
    @FXML
    PasswordField confirmPwd;
    @FXML
    Label confirmacion;

    public void initialize() {

    }

    @FXML
    void crear() {
        IUsuarioDAO usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
        UsuarioTO usuarioTO = new UsuarioTO();

        if(nombre.getText().equals("") || pwd.getText().equals("") || confirmPwd.getText().equals("")){
            confirmacion.setText("Rellene todos los campos");
            confirmacion.setStyle("-fx-text-fill: #ba2231;");
        } else {
            if(pwd.getText().equals(confirmPwd.getText())) {
            usuarioTO.setNombre(nombre.getText());
            usuarioTO.setPwd(pwd.getText());
            usuarioDAO.insertUsuario(usuarioTO);
                confirmacion.setText("Usuario agregado");
                confirmacion.setStyle("-fx-text-fill: #0dba14;");
            } else if(!pwd.getText().equals(confirmPwd.getText())){
                confirmacion.setText("Las contraseñas no coinciden");
                confirmacion.setStyle("-fx-text-fill: #ba2231;");
            }
        }
    }

    @FXML
    void limpiarCampos() {
        nombre.clear();
        pwd.clear();
        confirmPwd.clear();
    }

    @FXML
    void cancelar() {
        SesionController.stSesionCentro.getChildren().clear();
        SesionController.stSesionCentro.add(IniciarSesionController.cargarInicioUsuario(this.getClass()),
                1,1);
    }
}
