package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstrattiGiornalieri;
import com.klugesoftware.farmamanager.DTO.ElencoTotaliGiornalieriRowData;

public class ElencoTotaliGiornalieriRowDataDAO {
	
	private static final String SQL_FIND_BETWEEN_DATE = "SELECT "
			+ "data,"
			+ "totaleVenditeLorde,"
			+ "totaleProfitti,"
			+ "totaleVenditeLordeLibere,"
			+ "totaleProfittiLibere,"
			+ "totaleVenditeLordeSSN,"
			+ "totaleProfittiSSN "
			+ "FROM TotaliGeneraliGiornalieri WHERE data BETWEEN ? AND ?  ";
	
	private final Logger logger = LogManager.getLogger(ElencoTotaliGiornalieriRowDataDAO.class.getName());
	
	private DAOFactory daoFactory;

	
	public ElencoTotaliGiornalieriRowDataDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public ArrayList<ElencoTotaliGiornalieriRowData> elencoBetweenDate(Date dateFrom, Date dateTo){
		return findList(SQL_FIND_BETWEEN_DATE, dateFrom,dateTo);
	}
	
	private ArrayList<ElencoTotaliGiornalieriRowData> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoTotaliGiornalieriRowData> elenco = new ArrayList<ElencoTotaliGiornalieriRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoTotaliGiornalieriRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}


}
