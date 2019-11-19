package Corellium.DAO;

import Corellium.DAO.component.UsuarioDAO;
import Corellium.DAO.design.IUsuarioDAO;

public class DAOFactory {

    private static DAOFactory daoFactory = new DAOFactory();

    public DAOFactory() { }

    public static DAOFactory getInstance() { return daoFactory; }
    public IUsuarioDAO getUsuarioDAO() { return new UsuarioDAO(); }

}
