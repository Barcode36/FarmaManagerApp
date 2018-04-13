package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.VenditeLibere;

public class VenditeLibereDAO {
	
	private static final String SQL_INSERT = "INSERT INTO VenditeLibere (idVenditaLibera,numreg,"
			+ "idVendita,posizioneInVendita,valoreVenditaLibera,totaleIva,totalePezziVenduti,"
			+ "totaleScontoProdotto,totaleVenditaScontata,codiceFiscale,dataVendita) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
	
	private static final String SQL_FIND_BY_ID = "SELECT * FROM VenditeLibere WHERE idVenditaLibera = ?";
	
	private static final String SQL_FIND_BY_ID_VENDITA = "SELECT * FROM VenditeLibere WHERE idVendita = ?";
	
	private static final String SQL_FIND_BY_RANGE_DATA = "SELECT * FROM VenditeLibere WHERE dataVendita BETWEEN ? AND ? ";
	
	private DAOFactory daoFactory;
	
	VenditeLibereDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	public VenditeLibere lookupById(int idVenditaLibera){
		return find(SQL_FIND_BY_ID, idVenditaLibera);
	}
	
	public ArrayList<VenditeLibere> lookupElencoVenditeLibereByIdVenditaGenerale(int idVendita){
		return findList(SQL_FIND_BY_ID_VENDITA, idVendita);
	}
	
	public ArrayList<VenditeLibere> elencoByIntervalloDate(Date from, Date to){
		return findList(SQL_FIND_BY_RANGE_DATA, from,to);
	}
	
	public VenditeLibere createVenditaLibera(VenditeLibere venditaLibera){
		
		if (venditaLibera.getIdVenditaLibera() != null){
			throw new IllegalArgumentException("VenditaLibera già presente!");
		}
		
		Object[] values = {
			venditaLibera.getIdVenditaLibera(),
			venditaLibera.getNumreg(),
			venditaLibera.getVendita().getIdVendita(),
			venditaLibera.getPosizioneInVendita(),
			venditaLibera.getValoreVenditaLibera(),
			venditaLibera.getTotaleIva(),
			venditaLibera.getTotalePezziVenduti(),
			venditaLibera.getTotaleScontoProdotto(),
			venditaLibera.getTotaleVenditaScontata(),
			venditaLibera.getCodiceFiscale(),
			venditaLibera.getDataVendita()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di una nuova VenditaLibera non è andata a buon fine: non è stato creato nessun record nel database!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				venditaLibera.setIdVenditaLibera(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di una nuova VenditaLibera non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return venditaLibera;
	}

	private VenditeLibere find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		VenditeLibere vendita = new VenditeLibere();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				vendita = DAOUtil.mapVenditaLibera(resultSet);
			}
			else 
				throw new SQLException("Non è stata trovato nessun record.");
			}catch(SQLException ex){
				ex.printStackTrace();
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return vendita;
	}
	
	private ArrayList<VenditeLibere> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<VenditeLibere> elenco = new ArrayList<VenditeLibere>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				elenco.add(DAOUtil.mapVenditaLibera(resultSet));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

}
