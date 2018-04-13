package com.klugesoftware.farmamanager.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;


public abstract class DAOFactory {
	
	public static final String PROPERTIES_FILE_NAME = "./resources/config/config.properties";
	
	private static final Properties propDb = new Properties();
	
	static {
		try {
			InputStream propertiesFile = new FileInputStream(PROPERTIES_FILE_NAME);	
		
			propDb.load(propertiesFile);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	private static final String url = propDb.getProperty("dbUrl");
	
	private static final String driver = propDb.getProperty("dbDriver");
	
	private static final String username = propDb.getProperty("dbUser");
	
	private static final String password = propDb.getProperty("dbPwd");
	
	public static DAOFactory getInstance(){
		
		/*if (name == null){
			try {
				throw new Exception();
			} catch (Exception e) {

				System.out.println("database name is null");
				e.printStackTrace();
			}
		}*/
		
		DAOFactory instance;
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
		instance = new DriverManagerDAOFactory(url,username,password);
		
		return instance;
	}	

	abstract Connection getConnetcion() throws SQLException;

	public ProdottiVenditaLiberaDAO getProdottoVenditaLiberaDAO(){
		return new ProdottiVenditaLiberaDAO(this);
	}
	
	public VenditeLibereDAO getVenditaLiberaDAO(){
		return new VenditeLibereDAO(this);
	}
	
	public VenditeDAO getVenditaDAO(){
		return new VenditeDAO(this);
	}
	
	public VenditeSSNDAO getVenditASSNDAO(){
		return new VenditeSSNDAO(this);
	}
	
	public ProdottiVenditaSSNDAO getProdottoVenditaSSNDAO(){
		return new ProdottiVenditaSSNDAO(this);
	}
	
	public ResiProdottiVenditaLiberaDAO getResoProdottoVenditaLiberaDAO(){
		return new ResiProdottiVenditaLiberaDAO(this);
	}
	
	public ResiVenditeLibereDAO getResoVenditaLiberaDAO(){
		return new ResiVenditeLibereDAO(this);
	}
	
	public ResiVenditeDAO getResiVenditeDAO(){
		return new ResiVenditeDAO(this);
	}
	
	public ResiVenditeSSNDAO getResoVenditaSSNDAO(){
		return new ResiVenditeSSNDAO(this);
	}
	
	public ResiProdottiVendutiSSNDAO getResoProdottoVenditaSSNDAO(){
		return new ResiProdottiVendutiSSNDAO(this);
	}
	
	public ElencoProdottiLiberaVenditaRowDataDAO getElencoProdottiLiberaVenditaDAO(){
		return new ElencoProdottiLiberaVenditaRowDataDAO(this);
	}
	
	public ElencoMinsanLiberaVenditaRowDataDAO getElencoMinsanLiberaVenditaDAO() {
		return new ElencoMinsanLiberaVenditaRowDataDAO(this);
	}
	
	public ElencoTotaliGiornalieriRowDataDAO getElencoTotaliGiornalieriRowDataDAO() {
		return new ElencoTotaliGiornalieriRowDataDAO(this);
	}
	
	public TotaliGeneraliVenditaEstrattiDAO getTotaliGeneraliVenditaEstrattiDAO(){
		return new TotaliGeneraliVenditaEstrattiDAO(this);
	}

	public TotaliGeneraliVenditaEstrattiGiornalieriDAO getTotaliGeneraliVenditaEstrattiGiornalieriDAO(){
		return new TotaliGeneraliVenditaEstrattiGiornalieriDAO(this);
	}
	
	public ImportazioniDAO getImportazioniDAO(){
		return new ImportazioniDAO(this);
	}
	
	public GiacenzeDAO getGiacenzeDAO(){
		return new GiacenzeDAO(this);
	}
}
