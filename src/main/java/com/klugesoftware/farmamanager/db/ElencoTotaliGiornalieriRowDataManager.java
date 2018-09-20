package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;
import java.util.Date;
import com.klugesoftware.farmamanager.DTO.ElencoTotaliGiornalieriRowData;

public class ElencoTotaliGiornalieriRowDataManager {

	public static ArrayList<ElencoTotaliGiornalieriRowData> lookUpElencoTotaliGiornalieriBetweenDate(Date dateFrom, Date dateTo){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoTotaliGiornalieriRowDataDAO elencoDAO = daoFactory.getElencoTotaliGiornalieriRowDataDAO();
		return elencoDAO.elencoBetweenDate(dateFrom, dateTo);
	}

}
