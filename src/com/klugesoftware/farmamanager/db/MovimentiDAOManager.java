package com.klugesoftware.farmamanager.db;

public class MovimentiDAOManager {

    /**
     * truncate tables
     */
    public static void truncateMovimenti(){

        DAOFactory daoFactory  = DAOFactory.getInstance();
        MovimentiDAO movimentiDAO = new MovimentiDAO(daoFactory);
        movimentiDAO.svuotaTabelle();

    }

}
