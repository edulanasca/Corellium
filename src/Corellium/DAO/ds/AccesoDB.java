package Corellium.DAO.ds;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoDB {

    public Connection getConnection() {
        Connection cn = null;

        try{
            File fileDB = new File("pXrck2W.db");
            Class.forName("org.sqlite.JDBC").getConstructor().newInstance();
            cn = DriverManager.getConnection("jdbc:sqlite:" + fileDB.getAbsolutePath());
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | SQLException |
                NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return cn;
    }

    public AccesoDB() { }
}
