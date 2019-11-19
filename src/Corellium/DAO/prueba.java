package Corellium.DAO;

import Corellium.DAO.component.UsuarioDAO;
import Corellium.DAO.design.IUsuarioDAO;
import Corellium.DAO.to.UsuarioTO;

import java.util.ArrayList;
import java.util.List;

public class prueba {
    public static void main(String[] args) {
        IUsuarioDAO usuarioDAO = DAOFactory.getInstance().getUsuarioDAO();
        UsuarioTO usuario = new UsuarioTO();
        usuario.setNombre("Edu");
        usuario.setPwd("123");
        String rpta = usuarioDAO.insertUsuario(usuario);
        System.out.println(rpta);
        ArrayList<UsuarioTO> usuarios = usuarioDAO.getUsuarios();
        System.out.println(usuarios);
    }
}
