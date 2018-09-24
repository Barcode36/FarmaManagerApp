package com.klugesoftware.farmamanager.db;

import com.klugesoftware.farmamanager.model.Importazioni;
import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.IOFunctions.ImportazioneVenditeFromDBF;
import com.klugesoftware.farmamanager.utility.DateUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author marcoscagliosi
 * Questa classe espone i metodi per le operazioni sulla Tabella Importazioni che contiene
 * i log delle importazioni avvenute in precedenza 
 */
public class ImportazioniDAOManager {

	private static final Logger logger = LogManager.getLogger(ImportazioniDAOManager.class.getName());

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

	public static void svuotaTabella(){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ImportazioniDAO importazioniDAO = daoFactory.getImportazioniDAO();
		importazioniDAO.deleteTable();
	}

	/**
	 * insert di un record nella Tabella Importazione che contiene il maxNumreg e l'ultima dataMovimento
	 */
	public static void insertLogImportazione(String note){
		Vendite vendita = VenditeDAOManager.lookupVenditaByMaxId();
		ResiVendite reso = ResiDAOManager.lookupByMaxId();
		Importazioni importazione = new Importazioni();

		//escludo il caso iin cui la tabella mpvimenti sia vuota
		if(vendita.getIdVendita() == null && reso.getIdResoVendita() == null){
			ImportazioniDAOManager.svuotaTabella();
		}else {

			if (vendita.getNumreg().intValue() >= reso.getNumreg()) {
				importazione.setUltimoNumRegImportato(vendita.getNumreg());
				importazione.setDataUltimoMovImportato(vendita.getDataVendita());
			} else {
				importazione.setUltimoNumRegImportato(reso.getNumreg());
				importazione.setDataUltimoMovImportato(reso.getDataReso());
			}
			importazione.setDataImportazione(DateUtility.getDataOdierna());
			importazione.setNote(note);
			importazione = insert(importazione);
			logger.info("Importazione terminata. Ultimi NUMREG e DATA: "+importazione.getUltimoNumRegImportato()+" "+importazione.getDataUltimoMovImportato());
		}
	}

}
