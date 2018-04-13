package com.klugesoftware.farmamanager.db;

import com.klugesoftware.farmamanager.model.Importazioni;
import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.IOFunctions.ImportazioneVenditeFromDBF;
import com.klugesoftware.farmamanager.utility.DateUtility;

/**
 * 
 * @author marcoscagliosi
 * Questa classe espone i metodi per le operazioni sulla Tabella Importazioni che contiene
 * i log delle importazioni avvenute in precedenza 
 */
public class ImportazioniDAOManager {

	public static Importazioni insert(Importazioni importazione){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ImportazioniDAO importazioniDAO = daoFactory.getImportazioniDAO();
		return importazioniDAO.create(importazione);
	}

	public static Importazioni findUltimoInsert(){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ImportazioniDAO importazioniDAO = daoFactory.getImportazioniDAO();
		return importazioniDAO.findUltimoRecordInserito();
	}
	
	/**
	 * insert di un record nella Tabella Importazione che contiene il maxNumreg e l'ultima dataMovimento
	 */
	public static void insertLogImportazione(){
		Vendite vendita = VenditeDAOManager.lookupVenditaByMaxId();
		ResiVendite reso = ResiDAOManager.lookupByMaxId();
		Importazioni importazione = new Importazioni();
		if (vendita.getNumreg().intValue() >= reso.getNumreg()){
			importazione.setUltimoNumRegImportato(vendita.getNumreg());
			importazione.setDataUltimoMovImportato(vendita.getDataVendita());
		}else{
			importazione.setUltimoNumRegImportato(reso.getNumreg());
			importazione.setDataUltimoMovImportato(reso.getDataReso());
		}
		importazione.setDataImportazione(DateUtility.getDataOdierna());
		importazione = insert(importazione);
	}

}
