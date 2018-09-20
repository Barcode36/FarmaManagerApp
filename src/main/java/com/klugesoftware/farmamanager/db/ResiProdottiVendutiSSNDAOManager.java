package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaSSN;

public class ResiProdottiVendutiSSNDAOManager {
	
	public static ResiProdottiVenditaSSN create(ResiProdottiVenditaSSN reso){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVendutiSSNDAO resoProdottiVendutiSSNDAO = daoFactory.getResoProdottoVenditaSSNDAO();
		return resoProdottiVendutiSSNDAO.create(reso);
	}

	public static ResiProdottiVenditaSSN update(ResiProdottiVenditaSSN reso){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVendutiSSNDAO resoProdottiVendutiSSNDAO = daoFactory.getResoProdottoVenditaSSNDAO();
		return resoProdottiVendutiSSNDAO.update(reso);
	}

	public static ResiProdottiVenditaSSN findByIdResoProdottiVendutiSSN(int idResoProdottiVendutiSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVendutiSSNDAO resoProdottiVendutiSSNDAO = daoFactory.getResoProdottoVenditaSSNDAO();
		return resoProdottiVendutiSSNDAO.lookUpById(idResoProdottiVendutiSSN);
	}
	
	public static ArrayList<ResiProdottiVenditaSSN> findByIdResoVenditaSSN(int idResoVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVendutiSSNDAO resoProdottiVendutiSSNDAO = daoFactory.getResoProdottoVenditaSSNDAO();
		return resoProdottiVendutiSSNDAO.lookUpByIdResoVenditaSSN(idResoVenditaSSN);
	}
	
	public static boolean deleteByIdResoVenditaSSN(int idResoVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVendutiSSNDAO resoProdottiVendutiSSNDAO = daoFactory.getResoProdottoVenditaSSNDAO();
		return resoProdottiVendutiSSNDAO.deleteByIdResoVenditaSSN(idResoVenditaSSN);
	}
	
	public static boolean deleteByIdResoProdottoVenditaSSN(int idResoProdottoVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVendutiSSNDAO resoProdottiVendutiSSNDAO = daoFactory.getResoProdottoVenditaSSNDAO();
		return resoProdottiVendutiSSNDAO.deleteByIdResoProdottoVenditaSSN(idResoProdottoVenditaSSN);
	}
	
	public static boolean svuotaTabella(){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVendutiSSNDAO resoProdottiVendutiSSNDAO = daoFactory.getResoProdottoVenditaSSNDAO();
		return resoProdottiVendutiSSNDAO.svuotaTable();
	}

}
