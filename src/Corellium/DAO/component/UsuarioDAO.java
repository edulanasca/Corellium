package Corellium.DAO.component;

import Corellium.DAO.design.IUsuarioDAO;
import Corellium.DAO.ds.AccesoDB;
import Corellium.DAO.to.UsuarioTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO implements IUsuarioDAO {

    private AccesoDB db;

    public UsuarioDAO() {
        db = new AccesoDB();
    }

    @Override
    public ArrayList<UsuarioTO> getUsuarios() {
        ArrayList<UsuarioTO> lista = new ArrayList<>();
        String sentencia = "SELECT * FROM users";
        Connection connection = db.getConnection();

        if (connection != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(sentencia);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    UsuarioTO usuario = new UsuarioTO();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setPwd(rs.getString("pwd"));
                    lista.add(usuario);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return lista;
    }

    @Override
    public String insertUsuario(UsuarioTO usuario) {
        String rpta = "Usuario agregado";
        Connection connection = db.getConnection();
        String sentencia = "INSERT INTO users (nombre, pwd) VALUES (?, ?)";

        if (connection != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(sentencia);
                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getPwd());

                if (ps.executeUpdate() == 0) rpta = null;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    rpta = null;
                }
            }
        }
        return rpta;
    }

    @Override
    public String updateUsuario(UsuarioTO usuario) {
        String rpta = "Datos modificados";
        Connection connection = db.getConnection();
        String sentencia = "UPDATE users SET nombre='" + usuario.getNombre()
                + "', pwd='" + usuario.getPwd() + "' WHERE id=" + usuario.getId();

        if (connection != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(sentencia);
                if (ps.executeUpdate() == 0) rpta = null;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    rpta = null;
                }
            }
        }

        return rpta;
    }

    @Override
    public String deleteUsuario(int id) {
        String rpta = "Usuario eliminado";
        Connection connection = db.getConnection();
        String sentencia = "DELETE FROM users WHERE id=?";

        if (connection != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(sentencia);
                ps.setInt(1, id);
                if (ps.executeUpdate() == 0) rpta = null;
            } catch (SQLException e) {
                e.printStackTrace();
                rpta = null;
            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    rpta = null;
                }
            }
        }
        return rpta;
    }

    @Override
    public UsuarioTO getUsuarioByName(String name) {
        UsuarioTO usuario = new UsuarioTO();
        Connection connection = db.getConnection();
        String sentencia = "SELECT * FROM users WHERE users.nombre='" + name + "'";

        if (connection != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(sentencia);
                ResultSet rs = ps.executeQuery();

                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPwd(rs.getString("pwd"));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }
}
