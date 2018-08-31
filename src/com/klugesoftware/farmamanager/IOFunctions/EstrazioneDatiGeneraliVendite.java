package com.klugesoftware.farmamanager.IOFunctions;

import java.io.FileInputStream;


import java.io.InputStream;
import java.util.*;
import javax.swing.JOptionPane;

import com.klugesoftware.farmamanager.DTO.ConfrontoTotaliVenditeRowData;
import com.klugesoftware.farmamanager.db.ResiDAOManager;
import com.klugesoftware.farmamanager.db.VenditeDAOManager;
import com.klugesoftware.farmamanager.db.VenditeLibereDAOMAnager;
import com.klugesoftware.farmamanager.model.VenditeLibere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EstrazioneDatiGeneraliVendite {

	private final Logger logger = LogManager.getLogger(EstrazioneDatiGeneraliVendite.class.getName());
	private final String PROPERTIES_FILE_NAME = "./resources/config/config.properties";
	private final Properties propsFarmaManager = new Properties();
	private Map<String, String> props;
	public EstrazioneDatiGeneraliVendite(){
		try{
			InputStream fileProps = new FileInputStream(PROPERTIES_FILE_NAME);
			propsFarmaManager.load(fileProps);
		}catch(Exception ex){
			logger.error(ex);
		}
	}

	public TotaliGeneraliVenditaEstratti estraiDatiTotali(Date from, Date to){
		TotaliGeneraliVenditaEstratti datiVenditaEstratti = null;;
		try{
			datiVenditaEstratti= new TotaliGeneraliVenditaEstratti();
			datiVenditaEstratti = VenditeDAOManager.totaliDatiVendita(from, to);
			
			//controllo che i datiEstratti non siano null. Se risultano nulli allora non ci sono movimenti nella data indicata, quindi avviso e termino
			if (datiVenditaEstratti != null){
				TotaliGeneraliResiVenditaEstratti resiEstratti = new TotaliGeneraliResiVenditaEstratti();
				resiEstratti = ResiDAOManager.estrazioneTotaliResiVendita(from, to);
				datiVenditaEstratti.sottraiResi(resiEstratti);               
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Si è verificato un errore ! ", "Error ", JOptionPane.ERROR_MESSAGE);
			logger.error(ex);
		}finally{
			return datiVenditaEstratti; 
		}
		
	}
		
	
	public List<VenditeLibere> estraiVenditeLibere(Date from, Date to){
		List<VenditeLibere> elenco = null;
		try{
			elenco = VenditeLibereDAOMAnager.lookupElencoVenditeLibereBetweenDate(from, to);
			if(elenco == null){
				JOptionPane.showMessageDialog(null, "Non risultano prodotti venduti nell'intervallo di date", "Elenco Prodotti Venduti", JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Il database è irrangiungibile ! ", "Error database connection", JOptionPane.ERROR_MESSAGE);
			logger.error(ex);
		}finally{
			return elenco;
		}		
	}

	public static void main(String[] args) {
	

	}

}
