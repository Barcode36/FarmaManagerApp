package com.klugesoftware.farmamanager.db;

import com.klugesoftware.farmamanager.utility.DateUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class MovimentiDAO {

    private static final String SQL_TRUNCATE_ACQUISTI = "TRUNCATE TABLE Acquisti";
    private static final String SQL_TRUNCATE_DETTAGLIO_ACQUISTI = "TRUNCATE TABLE DettaglioAcquisti";
    private static final String SQL_TRUNCATE_GIACENZE = "TRUNCATE TABLE Giacenze";
    private static final String SQL_TRUNCATE_IMPORTAZIONI = "TRUNCATE TABLE Importazioni";
    private static final String SQL_TRUNCATE_PRODOTTI_VENDITA_LIBERA = "TRUNCATE TABLE ProdottiVenditaLibera";
    private static final String SQL_TRUNCATE_PRODOTTI_VENDITA_SSN = "TRUNCATE TABLE ProdottiVenditaSSN";
    private static final String SQL_TRUNCATE_PRODOTTI_RESI_VENDITA_LIBERA = "TRUNCATE TABLE ResiProdottiVenditaLibera";
    private static final String SQL_TRUNCATE_PRODOTTI_RESI_VENDITA_SSN = "TRUNCATE TABLE ResiProdottiVenditaSSN";
    private static final String SQL_TRUNCATE_RESI_VENDITE = "TRUNCATE TABLE ResiVendite";
    private static final String SQL_TRUNCATE_RESI_VENDITE_LIBERE = "TRUNCATE TABLE ResiVenditeLibere";
    private static final String SQL_TRUNCATE_RESI_VENDITE_SSN = "TRUNCATE TABLE ResiVenditeSSN";
    private static final String SQL_TRUNCATE_TOTALI_GIORNALIERI = "TRUNCATE TABLE TotaliGeneraliGiornalieri";
    private static final String SQL_TRUNCATE_TOTALI_MENSILI = "TRUNCATE TABLE TotaliGeneraliMensili";
    private static final String SQL_TRUNCATE_VENDITE = "TRUNCATE TABLE Vendite";
    private static final String SQL_TRUNCATE_VENDITE_LIBERE = "TRUNCATE TABLE VenditeLibere";
    private static final String SQL_TRUNCATE_VENDITE_SSN = "TRUNCATE TABLE VenditeSSN";


    private DAOFactory daoFactory;
    private final Logger logger = LogManager.getLogger(VenditeDAO.class.getName());

    public MovimentiDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    /**
     * truncate tables
     */
    public void svuotaTabelle(){
        ArrayList<String> sqlScript = new ArrayList<String>();
        sqlScript.add(SQL_TRUNCATE_IMPORTAZIONI);
        sqlScript.add(SQL_TRUNCATE_PRODOTTI_RESI_VENDITA_LIBERA);
        sqlScript.add(SQL_TRUNCATE_PRODOTTI_RESI_VENDITA_SSN);
        sqlScript.add(SQL_TRUNCATE_PRODOTTI_VENDITA_LIBERA);
        sqlScript.add(SQL_TRUNCATE_PRODOTTI_VENDITA_SSN);
        sqlScript.add(SQL_TRUNCATE_RESI_VENDITE);
        sqlScript.add(SQL_TRUNCATE_RESI_VENDITE_LIBERE);
        sqlScript.add(SQL_TRUNCATE_RESI_VENDITE_SSN);
        sqlScript.add(SQL_TRUNCATE_TOTALI_GIORNALIERI);
        sqlScript.add(SQL_TRUNCATE_TOTALI_MENSILI);
        sqlScript.add(SQL_TRUNCATE_VENDITE);
        sqlScript.add(SQL_TRUNCATE_VENDITE_LIBERE);
        sqlScript.add(SQL_TRUNCATE_VENDITE_SSN);


        Iterator<String> iter = sqlScript.iterator();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int affestedRows = 0;
        try{
            conn = daoFactory.getConnetcion();
            conn.setAutoCommit(false);

            while (iter.hasNext()){
                String sqlTemp = iter.next();
                preparedStatement = conn.prepareStatement(sqlTemp);
                affestedRows = preparedStatement.executeUpdate();
            }

            conn.commit();
        }catch(SQLException ex){
            logger.error(ex);
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error(ex);
            }
        }finally{
            DAOUtil.close(conn, preparedStatement);
        }
    }


}
