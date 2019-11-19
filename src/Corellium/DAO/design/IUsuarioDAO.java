package Corellium.DAO.design;

import Corellium.DAO.to.UsuarioTO;

import java.util.ArrayList;

public interface IUsuarioDAO {
    ArrayList<UsuarioTO> getUsuarios();
    String insertUsuario(UsuarioTO usuario);
    String updateUsuario(UsuarioTO usuario);
    String deleteUsuario(int id);
    UsuarioTO getUsuarioByName(String name);
}
