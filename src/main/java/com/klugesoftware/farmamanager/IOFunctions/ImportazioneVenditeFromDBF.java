package com.klugesoftware.farmamanager.IOFunctions;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.klugesoftware.farmamanager.db.*;
import com.klugesoftware.farmamanager.model.*;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.klugesoftware.farmamanager.utility.DateUtility;

public class ImportazioneVenditeFromDBF extends Task {

	private final Logger logger = LogManager.getLogger(ImportazioneVenditeFromDBF.class.getName());
	private final boolean DEBUG = false;
	private boolean importazioneIniziale = false;
	private BigDecimal percentualeCostoPresunto;
	private final String PROPERTIES_FILE_NAME = "./resources/config/config.properties";
	private final Properties propsFarmaManager = new Properties();
	private Connection connection;	
	private boolean cambioVendita = false;
	private String dateFrom;
	private String dateTo;
	private boolean readDateFormFile = false;
	private String dbfTabellaName;

	public ImportazioneVenditeFromDBF(String dateFrom,String dateTo, String movimentiFileName,boolean importazioneIniziale){
		this.dateFrom = DateUtility.converteGUIStringDDMMYYYYToSqlString(dateFrom);
		this.dateTo = DateUtility.converteGUIStringDDMMYYYYToSqlString(dateTo);
		dbfTabellaName = movimentiFileName;
		readDateFormFile = false;
		this.importazioneIniziale = importazioneIniziale;
		init();
	}
	
	private void init(){
		try{
            InputStream propertiesFile = new FileInputStream(PROPERTIES_FILE_NAME);
            propsFarmaManager.load(propertiesFile);
            Class.forName(propsFarmaManager.getProperty("dbfDriverName"));
            connection = DriverManager.getConnection(propsFarmaManager.getProperty("dbfUrlName"));
            percentualeCostoPresunto = new BigDecimal(propsFarmaManager.getProperty("percentualeCostoPresunto"));

        }catch(Exception ex) {
            logger.error(ex);
        }
	}
	
	@Override
	protected Object call() throws Exception{
		logger.info("importazione movimenti da "+dateFrom+" a "+dateTo);
		updateMessage("importazione movimenti da "+dateFrom+" a "+dateTo);
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from "+dbfTabellaName+"  where CLFR <> 'M'  AND STATO_VEN <> 'R' AND NUMCLMESE <> '0' AND ( (SEGNO = 'U'  AND DATAREG BETWEEN  '"+dateFrom+"' AND '"+dateTo+"') "
					+ "OR (SEGNO = 'E' AND RESO_FLG='R' AND DATAREG BETWEEN  '"+dateFrom+"' AND '"+dateTo+"') ) ");
			Vendite venditaGenerale = null;
			VenditeSSN venditaSSN = null;
			VenditeLibere venditaLibera = null;
			ResiVendite resoVendita = new ResiVendite();
			ResiVenditeSSN resoVenditaSSN = new ResiVenditeSSN();
			ResiVenditeLibere resoVenditaLibera  = new ResiVenditeLibere();
			int numReg = 0;
			int posizioneVenditaGenerale = 0;
			int posizioneProdottoInVendita = 0;
			String campoRic="";
			String campoRicReso="";
			boolean cambioReso = false;
			while (rs.next() && !isCancelled()){
				updateMessage("inserimento movimento num: "+rs.getInt("NUMREG"));
				// filtra solo i movimenti relativi alle vendite a Contanti o SSN: i RESI cioè il campo RESO_FLG = R vengono gestiti a parte
				String resoFlg = "";
				if (rs.getString("RESO_FLG") != null)
					resoFlg = rs.getString("RESO_FLG");
				/*
				 * gestione dei resi: scorre le righe di dbf e crea resiSSN e resi VenditeLibere
				 */
				if (resoFlg.equals("R")){
					if (resoVendita.getNumreg() != rs.getInt("NUMREG")){
						resoVendita = new ResiVendite();
						resoVendita.setNumreg(rs.getInt("NUMREG"));
						resoVendita.setDataReso(rs.getDate("DATAREG"));
					}
	
					switch(rs.getString("CHIUSURA")){
					case "1": //reso SSN
						if ( (resoVenditaSSN.getNumreg() != rs.getInt("NUMREG")) || (!(resoVenditaSSN.getCampoRicReso().equals(rs.getString("RIC")))) ){						
							resoVenditaSSN = new ResiVenditeSSN();
							resoVenditaSSN.setNumreg(rs.getInt("NUMREG"));
							resoVenditaSSN.setDataResoVenditaSSN(rs.getDate("DATAREG"));
							resoVenditaSSN.setResiVendite(resoVendita);
							resoVendita.addResiVenditeSsn(resoVenditaSSN);
						}	
							
						mappingResoVenditaSSN(resoVendita,resoVenditaSSN, rs);
						/*
						 * aggiorno ResoVenditaSSN:
						 * 	- se il campoRic è cambiato allora aggiorno il valore in ResoVenditaSSN e poi inserisco i valori nel DB
						 *  - se il campo RIC è lo stesso allora faccio update dei valori DB
						 *  - aggiorno il calcolo dell'eventuale quota ricetta
						 */
						if (resoVenditaSSN.getCampoRicReso().equals(rs.getString("RIC"))){
							updateResi(resoVendita);
						}else{
							resoVenditaSSN.setCampoRicReso(rs.getString("RIC"));
							resoVenditaSSN.setQuotaRicetta(rs.getBigDecimal("QUOTARIC"));
							resoVenditaSSN.setTotaleRicetta(resoVenditaSSN.getTotaleRicetta().add(resoVenditaSSN.getQuotaRicetta()));
							resoVenditaSSN.setEsenzione(rs.getString("K_ESENZION"));
							resoVenditaSSN.setCodiceFiscale(rs.getString("CODCF"));
							insertResi(resoVendita);
						}	
						
						break;
					case "C":
						if ( (resoVenditaLibera.getNumreg() != rs.getInt("NUMREG")) || (!(resoVenditaLibera.getCampoRicReso().equals(rs.getString("RIC"))))){
							resoVenditaLibera = new ResiVenditeLibere();
							resoVenditaLibera.setNumreg(rs.getInt("NUMREG"));
							resoVenditaLibera.setDataResoVendita(rs.getDate("DATAREG"));
							resoVenditaLibera.setResiVendite(resoVendita);
							resoVendita.addResiVenditeLibere(resoVenditaLibera);
						}
						mappingResoVenditaLibera(resoVendita,resoVenditaLibera,rs);
						/*
						 * aggiorno ResoVenditaLibera:
						 * 	- se il campoRic è cambiato allora aggiorno il valore in ResoVenditaLibera e poi inserisco i valori nel DB
						 *  - se il campo RIC è lo stesso allora faccio update dei valori DB
						 *  
						 */
						if(resoVenditaLibera.getCampoRicReso().equals(rs.getString("RIC"))){
							updateResi(resoVendita);
						}else{
							resoVenditaLibera.setDataResoVendita(rs.getDate("DATAREG"));
							resoVenditaLibera.setCodiceFiscale(rs.getString("CODCF"));
							resoVenditaLibera.setCampoRicReso(rs.getString("RIC"));
							insertResi(resoVendita);
						}
						break;
					}					
				}
				else{
					if( (rs.getString("CHIUSURA").equals("1") || rs.getString("CHIUSURA").equals("C")) && (!resoFlg.equals("R"))){
						if (numReg != rs.getInt("NUMREG")){
							// Nuova vendita generale  per ogni cambio NUMREG
							numReg = rs.getInt("NUMREG");
							if (venditaGenerale != null)
								insertMovimento(venditaGenerale);
							venditaGenerale = new Vendite();
							venditaGenerale.setScontoVendita(rs.getBigDecimal("SCONTOCL"));
							venditaGenerale.setNumreg(numReg);
							venditaGenerale.setDataVendita(rs.getDate("DATAREG"));
							campoRic="00";
							posizioneVenditaGenerale = 0;
						}
						switch(rs.getString("CHIUSURA")){
						case "1": // vendita SSN
							if (!(campoRic.equals(rs.getString("RIC")))){
								cambioVendita = true;
								venditaSSN = new VenditeSSN();
								venditaSSN.setVendita(venditaGenerale);
							    venditaGenerale.addVenditaSSN(venditaSSN);
								campoRic = rs.getString("RIC");
								posizioneVenditaGenerale++;
								posizioneProdottoInVendita = 1;
							}
							else
								posizioneProdottoInVendita++;
							venditaSSN.setPosizioneInVendita(posizioneVenditaGenerale);
							cambioVendita = mappingVenditaSsn(rs,venditaGenerale,venditaSSN,posizioneProdottoInVendita,cambioVendita);
							break;
						case "C": // vendita Libera
							if (!(campoRic.equals(rs.getString("RIC")))){
								cambioVendita = true;
								venditaLibera = new VenditeLibere();
								venditaLibera.setVendita(venditaGenerale);
								venditaGenerale.addVenditaLibera(venditaLibera);
								campoRic = rs.getString("RIC");
								posizioneVenditaGenerale++;
								posizioneProdottoInVendita = 1;
							}
							else
								posizioneProdottoInVendita++;
							venditaLibera.setPosizioneInVendita(posizioneVenditaGenerale);
							cambioVendita = mappingVenditaLibera(rs,venditaGenerale,venditaLibera,posizioneProdottoInVendita,cambioVendita);
							break;
						}
					}					
				}				
			}

			if(isCancelled()){
				updateMessage("importazione interrotta");
			}else {

				// insert dell'ultimo movimento
				if (venditaGenerale != null)
					insertMovimento(venditaGenerale);

				//calcola i TotaliGeneraliResiVendite e li
				//decurto dai TotaliGeneraliVendite del mese corrispondente
				List<ResiVendite> elencoResi = ResiDAOManager.elencoResiVenditeComposti(DateUtility.converteDBStringYYYMMDDToDate(dateFrom), DateUtility.converteDBStringYYYMMDDToDate(dateTo));
				if (elencoResi != null) {
					Iterator<ResiVendite> iterResiVendite = elencoResi.iterator();
					ResiVendite resoVenditaTemp;
					while (iterResiVendite.hasNext()) {
						resoVenditaTemp = iterResiVendite.next();
						TotaliGeneraliVenditaEstrattiDAOManager.aggiornaTotaliGenerali(resoVenditaTemp);
						TotaliGeneraliVenditaEstrattiGiornalieriDAOManager.aggiornaTotaliGenerali(resoVenditaTemp);
					}
				}
				//aggiorno i LogImportazioni in Tabella Importazioni
				aggiornaLogImportazioni();
				updateMessage("importazione terminata");
			}
		}catch(Exception ex){
			logger.error("doInBackground(): I can't do a query",ex);
		}finally{
			try{
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
 				if (connection != null) connection.close();
			}catch(Exception ex){
				logger.error("doInBackground(): "+ex.getMessage());
			}
		}
		
		return null;
	}
	

	// in questo metodo si fa il mapping fra il record presente in resultSet( da DBF) e l'oggetto ProdottiVenditaSSN  
	private boolean mappingVenditaSsn(ResultSet resultSet,Vendite venditaGenerale, VenditeSSN venditaSSN, int posizioneProdottoInVendita,boolean cambioVendita){
		try {
			if(DEBUG){
				System.out.println("DATAREG: "+resultSet.getDate("DATAREG"));
				System.out.println("NumReg:"+Integer.toString(resultSet.getInt("NUMREG")) +" vendita ssn: "+Integer.toString(resultSet.getInt("NUMRICETTA"))+" RIC:"+resultSet.getString("RIC"));
			}
			/*
			 *  mapping prodotto
			 */
			ProdottiVenditaSSN prodotto = new ProdottiVenditaSSN();
			prodotto.setNumreg(resultSet.getInt("NUMREG"));
			prodotto.setPosizioneInVendita(posizioneProdottoInVendita);
			prodotto.setMinsan(resultSet.getString("MINSAN10"));
			prodotto.setDescrizione(resultSet.getString("DESCRIZ"));
			prodotto.setQuantita(resultSet.getInt("QUANT"));
			prodotto.setDataVendita(resultSet.getDate("DATAREG"));
			prodotto.setPrezzoPraticato(resultSet.getBigDecimal("PREZZO"));
			prodotto.setPrezzoFustello(resultSet.getBigDecimal("PREZZO_F"));
			prodotto.setPrezzoRimborso(resultSet.getBigDecimal("PREZZOPR"));
			prodotto.setQuotaPezzo(resultSet.getBigDecimal("QUOTAPZ"));		
			prodotto.setQuotaAssistito(resultSet.getBigDecimal("QASS"));// si riferisce alla quantità totale
			
			
			prodotto.setCodiceAtcGmp(resultSet.getString("ATC"));
			prodotto.setDeGrassi(resultSet.getString("LIBERO_C10"));
			prodotto.setTipoProdotto(resultSet.getString("K_CODMERC"));
			prodotto.setTipoRicetta(resultSet.getString("K_TIPORIC"));
			prodotto.setAliquotaIva(resultSet.getInt("ALIVA"));
			//prodotto.setCostoCompresoIva(resultSet.getBigDecimal("COSTO"));
			
			valorizzaCosto(prodotto, resultSet.getBigDecimal("COSTO"),prodotto.getAliquotaIva(),prodotto.getMinsan(),prodotto.getPrezzoFustello());
			
			// calcolo costo netto iva
			if(prodotto.getCostoCompresoIva().doubleValue() > 0){
				double tempCostoNettoIva = prodotto.getCostoCompresoIva().doubleValue()/(1+((double)prodotto.getAliquotaIva())/100);
				prodotto.setCostoNettoIva(new BigDecimal(tempCostoNettoIva).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			}

			prodotto.setPercentualeScontoSSN(resultSet.getBigDecimal("SCONTOASL"));
			
			// controllo che il prodotto sia soggetto a sconto
			BigDecimal scontoSSN = new BigDecimal(0);
			if (prodotto.getPercentualeScontoSSN().compareTo(new BigDecimal(0)) > 0)
			{
				// calcolo scontoSSN: prezzoFustello/1,aliquotaIva * percentualeSconto ( calcolo dello scontoSSN su prezzo fustello deivato)			
				double tempScontoSSN = ((prodotto.getPrezzoFustello().doubleValue())/(1+((double)prodotto.getAliquotaIva())/100))*(prodotto.getPercentualeScontoSSN().doubleValue())/100;
				scontoSSN = new BigDecimal(tempScontoSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
				prodotto.setValoreScontoSSN(scontoSSN);
				
				// calcolo scontoSSNExtra del 2,25% - lo sconto è applicato  al prezzo di fustello, a tutti i prodotti a cui si applica lo sconto ssn( es. 3,75% etc..) 
				BigDecimal scontoExtraSSN = new BigDecimal(0);
				double tempScontoExtraSSN = ((prodotto.getPrezzoFustello().doubleValue())/(1+((double)prodotto.getAliquotaIva())/100))*(2.25)/100;
				scontoExtraSSN = new BigDecimal(tempScontoExtraSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
				prodotto.setValoreScontoSSNExtra(scontoExtraSSN);			
			}	
			// calcolo sconto Aifa 0,64%: viene calcolato come differenza fra PREZZO_F - PREZZO 
			// ATTENZIONE: il campo PREZZO contiene anche il valore dei Prezzi di Rimborso Regionali in caso di Integrative
			BigDecimal scontoAifa = new BigDecimal(0);
			scontoAifa = (prodotto.getPrezzoFustello()).subtract(prodotto.getPrezzoPraticato());
			if ( (scontoAifa.compareTo(new BigDecimal(0)) > 0) && ( !(resultSet.getString("K_ESENZION")).equals("ESA")) )
				prodotto.setValoreScontoAifa(scontoAifa);
			
			// calcolo del totaleScontoUnitario riferito al singoloprodotto dato dalla somma degli scontiSSN, sconto PayBack ed EstraSSN
			//double tempScontoTotaleUnitario = prodotto.getValoreScontoSSN().doubleValue()+prodotto.getValoreScontoAifa().doubleValue()+prodotto.getValoreScontoSSNExtra().doubleValue();
			//prodotto.setTotaleScontoUnitario(new BigDecimal(tempScontoTotaleUnitario).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			prodotto.setTotaleScontoUnitario(prodotto.getValoreScontoAifa().add(prodotto.getValoreScontoSSN().add(prodotto.getValoreScontoSSNExtra())).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			
			
			/*
			 * mapping venditaSSN
			 */
			prodotto.setVenditaSSN(venditaSSN);
			(venditaSSN).addProdottoSsnVenduto(prodotto);
			if (cambioVendita)
			{
				(venditaSSN).setEsenzione(resultSet.getString("K_ESENZION"));
				(venditaSSN).setDataVenditaSSN(resultSet.getDate("DATAREG"));
				(venditaSSN).setQuotaRicetta(resultSet.getBigDecimal("QUOTARIC"));
				venditaSSN.setCodiceFiscale(resultSet.getString("CODCF"));
				venditaSSN.setTotaleRicetta(venditaSSN.getTotaleRicetta().add(venditaSSN.getQuotaRicetta()));
				venditaSSN.setNumreg(resultSet.getInt("NUMREG"));
				venditaGenerale.setNumreg(resultSet.getInt("NUMREG"));
			}
			
			(venditaSSN).setTotaleRicetta(venditaSSN.getTotaleRicetta().add(prodotto.getQuotaAssistito()));
			venditaSSN.setQuotaAssistito(venditaSSN.getQuotaAssistito().add(prodotto.getQuotaAssistito().setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));
			
			// accumulo Totali sconti SSN relativi al prodotto
			BigDecimal tempTotSconto = (prodotto.getValoreScontoSSN().add(prodotto.getValoreScontoSSNExtra().add(prodotto.getValoreScontoAifa())));
			BigDecimal totSconto = tempTotSconto.multiply(new BigDecimal(prodotto.getQuantita()));
			venditaSSN.setTotaleScontoSSN(venditaSSN.getTotaleScontoSSN().add(totSconto));

			/*
			 *  calcolo prezzoVenditaNetto: (prezzo di Fustello - totaleScontoUnitario) al netto dell'iva.
			 *  Bisogna distinguere i prodotti per diabetici
			 */
			double tempPrezzoVenditaNetto = 0;
			if(venditaSSN.getEsenzione().equals("ESA")){
				tempPrezzoVenditaNetto = (prodotto.getPrezzoPraticato().doubleValue() - prodotto.getTotaleScontoUnitario().doubleValue())/(1+((double)prodotto.getAliquotaIva())/100);
			}else{
				tempPrezzoVenditaNetto = (prodotto.getPrezzoFustello().doubleValue() - prodotto.getTotaleScontoUnitario().doubleValue())/(1+((double)prodotto.getAliquotaIva())/100);
			}
			prodotto.setPrezzoVenditaNetto(new BigDecimal(tempPrezzoVenditaNetto).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));	
			
			// PREZZO_F deivato * QUANT oppure PREZZO * QUANT( nel caso di prodotti per Diabetici)
			if (venditaSSN.getEsenzione().equals("ESA"))
			{
					((venditaSSN)).setValoreVenditaSSN(venditaSSN.getValoreVenditaSSN().add(prodotto.getPrezzoPraticato().multiply(new BigDecimal(prodotto.getQuantita()))));			
					venditaGenerale.setTotaleVendita(venditaGenerale.getTotaleVendita().add(prodotto.getPrezzoPraticato().multiply(new BigDecimal(prodotto.getQuantita()))));
					double valoreVenditaScontata1 = (prodotto.getPrezzoPraticato().doubleValue() * prodotto.getQuantita()) - (prodotto.getTotaleScontoUnitario().doubleValue()*prodotto.getQuantita());
					venditaGenerale.setTotaleVenditaScontata(venditaGenerale.getTotaleVenditaScontata().add(new BigDecimal(valoreVenditaScontata1).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));					
			}
			else
			{
					((venditaSSN)).setValoreVenditaSSN(venditaSSN.getValoreVenditaSSN().add(prodotto.getPrezzoFustello().multiply(new BigDecimal(prodotto.getQuantita()))));
					venditaGenerale.setTotaleVendita(venditaGenerale.getTotaleVendita().add(prodotto.getPrezzoFustello().multiply(new BigDecimal(prodotto.getQuantita()))));
					double valoreVenditaScontata = (prodotto.getPrezzoFustello().doubleValue() * prodotto.getQuantita()) - (prodotto.getTotaleScontoUnitario().doubleValue()*prodotto.getQuantita());
					venditaGenerale.setTotaleVenditaScontata(venditaGenerale.getTotaleVenditaScontata().add(new BigDecimal(valoreVenditaScontata).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));
			}
			double divisoreTemp = ((double)prodotto.getAliquotaIva())/100;
			venditaSSN.setTotaleIva(venditaSSN.getTotaleIva().add(venditaSSN.getValoreVenditaSSN().multiply(new BigDecimal(divisoreTemp))));
			
			// aggiorno il totale dei pezzi venduti
			venditaGenerale.setTotalePezziVenduti(venditaGenerale.getTotalePezziVenduti()+prodotto.getQuantita());
			venditaSSN.setTotalePezziVenduti(venditaSSN.getTotalePezziVenduti() +  prodotto.getQuantita());
			
			
			/*
			 *  - calcolo del ProfittoUnitario: (prezzoVenditaNettoDa Sconti ed Iva) - Costo
			 *  - calcolo della  percentualeMargineUnitario: (ProfittoUnitario/PrezzoUnitarioVenditaNetto)*100
			 *  - calcolo della  percentualeRicaricoUnitario: (ProfitttoUnitario/CostoNetto)*100 
			 */

			prodotto.setProfittoUnitario(prodotto.getPrezzoVenditaNetto().subtract(prodotto.getCostoNettoIva()));
			setProfittoMargineRicarico(prodotto);
		} catch (SQLException e) {
			logger.error("mappingVenditeSSN(): I can't create record...",e);
		} finally{
			return false;
		}
	}
	
	// in questo metodo si fa il mapping fra il record presente in resultSet e l'oggetto ProdottiVenditaLibera
	private boolean mappingVenditaLibera(ResultSet resultSet, Vendite venditaGenerale, VenditeLibere venditaLibera,int posizioneProdottoInVendita,boolean cambioVendita){
		try {
			if(DEBUG){
				System.out.println("DATAREG: "+resultSet.getDate("DATAREG"));
				System.out.println("NumReg:"+Integer.toString(resultSet.getInt("NUMREG"))+" vendita libera: "+Integer.toString(resultSet.getInt("NUMRICETTA"))+" RIC:"+resultSet.getString("RIC"));
			}
			
			// mapping prodotto
			ProdottiVenditaLibera prodotto = new ProdottiVenditaLibera();
			prodotto.setPosizioneInVendita(posizioneProdottoInVendita);
			prodotto.setNumreg(resultSet.getInt("NUMREG"));
			prodotto.setMinsan(resultSet.getString("MINSAN10"));
			prodotto.setDescrizione(resultSet.getString("DESCRIZ"));
			prodotto.setPrezzoVendita(resultSet.getBigDecimal("PREZZO_F"));
			prodotto.setScontoProdotti(resultSet.getBigDecimal("SCONTO"));
			prodotto.setAliquotaIva(resultSet.getInt("ALIVA"));
			//prodotto.setCostoCompresoIva(resultSet.getBigDecimal("COSTO"));
			valorizzaCosto(prodotto, resultSet.getBigDecimal("COSTO"),prodotto.getAliquotaIva(),prodotto.getMinsan(),prodotto.getPrezzoVendita());

			// setting del Costo prodotto Deivato
			if(prodotto.getCostoCompresoIva().doubleValue() > 0){
				double costoProdottoNettoIva = prodotto.getCostoCompresoIva().doubleValue()/(1+((double)prodotto.getAliquotaIva())/100);
				prodotto.setCostoNettoIva(new BigDecimal(costoProdottoNettoIva).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			}
			
			prodotto.setQuantita(resultSet.getInt("QUANT"));
			prodotto.setDataVendita(resultSet.getDate("DATAREG"));
			prodotto.setDeGrassi(resultSet.getString("LIBERO_C10"));
			prodotto.setTipoProdotto(resultSet.getString("K_CODMERC"));
			prodotto.setTipoRicetta(resultSet.getString("K_TIPORIC"));
			prodotto.setVenditaLibera(venditaLibera);
			
			//TODO: e se è un prodotto per diabetici!!!! integrativa regionale?
			//se c'è differenza fra PREZZO_F e PREZZO valorizzo lo sconto PayBack
			BigDecimal prezzoNettoAifa = resultSet.getBigDecimal("PREZZO_E");
			BigDecimal scontoPayBack = new BigDecimal(0);
			// se il campo PREZZO_E = 0 allora nn è un prodotto con  sconto AIFA
			if (prezzoNettoAifa.doubleValue() > 0)
			{
				scontoPayBack = (prodotto.getPrezzoVendita()).subtract(prezzoNettoAifa);
				if (scontoPayBack.compareTo(new BigDecimal(0)) > 0)
					prodotto.setScontoPayBack(scontoPayBack);
			}	
			
			// calcolo scontoTotaleUnitario: la somma dello scontoprodotto/quantità + eventuale sconto payback + manca solo eventuale sconto vendita generale ripartito
			double scontoProdottoUnitario = (prodotto.getScontoProdotti().doubleValue()/prodotto.getQuantita())+prodotto.getScontoPayBack().doubleValue();
			prodotto.setTotaleScontoUnitario(new BigDecimal(scontoProdottoUnitario).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			
			// calcolo prezzoVenditaNetto: prezzo del singolo prodotto al netto degli sconto e dell'iva
			double prezzoVenditaNetto = (prodotto.getPrezzoVendita().doubleValue()-prodotto.getTotaleScontoUnitario().doubleValue())/(1+((double)prodotto.getAliquotaIva())/100);
			prodotto.setPrezzoVenditaNetto(new BigDecimal(prezzoVenditaNetto).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			
			/*
			 * mapping vendita libera e VenditaGenerale
			 */
			//calcola la somma del valore delle vendite libere: cioè la sommatoria del prezzo di fustello per la quantità		
			(venditaLibera).addProdottoVenduto(prodotto);		

			BigDecimal valoreVendita = prodotto.getPrezzoVendita().multiply(new BigDecimal(prodotto.getQuantita()));
			BigDecimal scontoProdotto = (prodotto.getScontoPayBack().multiply(new BigDecimal(prodotto.getQuantita()))).add(prodotto.getScontoProdotti());
			BigDecimal valoreVenditaScontata = valoreVendita.subtract(scontoProdotto);
			
			(venditaLibera).setValoreVenditaLibera((venditaLibera).getValoreVenditaLibera().add(valoreVendita));
			venditaGenerale.setTotaleVendita(venditaGenerale.getTotaleVendita().add(valoreVendita));
			
			double divisoreIvaVenditaLibera = ((double)prodotto.getAliquotaIva())/100 ;
			venditaLibera.setTotaleIva(venditaLibera.getTotaleIva().add(venditaLibera.getValoreVenditaLibera().multiply(new BigDecimal(divisoreIvaVenditaLibera))));
			
			// accumulo gli sconti sul prodotto( compreso sconto AIFA) e aggiorno il valore di VenditaScontata	 
			(venditaLibera).setTotaleScontoProdotto(venditaLibera.getTotaleScontoProdotto().add(scontoProdotto));
			(venditaLibera).setTotaleVenditaScontata(venditaLibera.getTotaleVenditaScontata().add(valoreVenditaScontata));
			venditaGenerale.setTotaleVenditaScontata(venditaGenerale.getTotaleVenditaScontata().add(valoreVenditaScontata));
			
			if (cambioVendita)
			{	
				cambioVendita = false;
				(venditaLibera).setDataVendita(resultSet.getDate("DATAREG"));
				(venditaLibera).setCodiceFiscale(resultSet.getString("CODCF"));
				venditaLibera.setNumreg(resultSet.getInt("NUMREG"));
			}
				
			// aggiorno il totale dei pezzi venduti sia in VenditaGenerale che in VenditaLibera
			venditaGenerale.setTotalePezziVenduti(venditaGenerale.getTotalePezziVenduti() + prodotto.getQuantita());
			venditaLibera.setTotalePezziVenduti(venditaLibera.getTotalePezziVenduti()+prodotto.getQuantita());
			
			/*
			 *  - calcolo del ProfittoUnitario: (prezzoVenditaNettoDa Sconti ed Iva) - Costo
			 *  - calcolo della  percentualeMargineUnitario: (ProfittoUnitario/PrezzoUnitarioVenditaNetto)*100
			 *  - calcolo della  percentualeRicaricoUnitario: (ProfitttoUnitario/CostoNetto)*100 
			 *  
			 *  *****Attenzione in questa fase manca l'eventuale sconto ripartito da Vendita Generale: viene ripartito successivamante in insertMovimento******
			 */
			prodotto.setProfittoUnitario(prodotto.getPrezzoVenditaNetto().subtract(prodotto.getCostoNettoIva()));
			setProfittoMargineRicarico(prodotto);
			/*
			if (prodotto.getCostoCompresoIva().doubleValue() > 0){
				if (prodotto.getProfittoUnitario().doubleValue() > 0){
					double tempMargine = (prodotto.getProfittoUnitario().doubleValue()/prodotto.getPrezzoVenditaNetto().doubleValue())*100;
					double tempRicarico = (prodotto.getProfittoUnitario().doubleValue()/prodotto.getCostoNettoIva().doubleValue())*100;
					prodotto.setPercentualeMargineUnitario(new BigDecimal(tempMargine).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
					prodotto.setPercentualeRicaricoUnitario(new BigDecimal(tempRicarico).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
				}
			}
			 */
		} catch (Exception e) {
			logger.error("mappingVenditaLibera(): I can't create record...",e);
		} finally{
			return false;
		}
	}

	/**
	 *
	 * @param prodotto
	 *
	 * 	Il metodo calcola il margine e ricarico nei vari casi con profittoUnitario,
	 * 	costoNetto o prezzo Vendita con valori <0 oppure =0 oppure >0
	 */
	private void setProfittoMargineRicarico(Object prodotto){
		ProdottiVenditaLibera prodottoVenditaLibera = null;
		ProdottiVenditaSSN prodottoVenditaSSN = null;
		double margineUnitario = 0;
		double ricaricoUnitario = 0;
		double profittoUnitario = 0;
		double prezzoVenditaNetto = 0;
		double costoNetto = 0;
		if (prodotto instanceof ProdottiVenditaLibera){
			prodottoVenditaLibera = (ProdottiVenditaLibera)prodotto;
			profittoUnitario = prodottoVenditaLibera.getProfittoUnitario().doubleValue();
			prezzoVenditaNetto = prodottoVenditaLibera.getPrezzoVenditaNetto().doubleValue();
			costoNetto = prodottoVenditaLibera.getCostoNettoIva().doubleValue();
		}else{
			if (prodotto instanceof ProdottiVenditaSSN){
				prodottoVenditaSSN = (ProdottiVenditaSSN) prodotto;
				profittoUnitario = prodottoVenditaSSN.getProfittoUnitario().doubleValue();
				prezzoVenditaNetto = prodottoVenditaSSN.getPrezzoVenditaNetto().doubleValue();
				costoNetto = prodottoVenditaSSN.getCostoNettoIva().doubleValue();
			}
		}

		if(profittoUnitario != 0){

			if(profittoUnitario > 0 && costoNetto == 0 && prezzoVenditaNetto > 0){
				margineUnitario = (profittoUnitario/Math.abs(prezzoVenditaNetto))*100;
				ricaricoUnitario = margineUnitario;
			}else {
				if (profittoUnitario < 0 && costoNetto > 0 && prezzoVenditaNetto == 0) {
					ricaricoUnitario = (profittoUnitario / Math.abs(costoNetto)) * 100;
					margineUnitario = ricaricoUnitario;
				}else{
					if(profittoUnitario < 0 && costoNetto == 0 && prezzoVenditaNetto < 0){
						margineUnitario = (profittoUnitario/Math.abs(prezzoVenditaNetto))*100;
						ricaricoUnitario = margineUnitario;
					}else {
						margineUnitario = (profittoUnitario/Math.abs(prezzoVenditaNetto))*100;
						ricaricoUnitario = (profittoUnitario/Math.abs(costoNetto))*100;
					}
				}
			}
		}
		if (prodotto instanceof ProdottiVenditaLibera){
			if(prodottoVenditaLibera != null) {
				prodottoVenditaLibera.setPercentualeRicaricoUnitario(new BigDecimal(ricaricoUnitario).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
				prodottoVenditaLibera.setPercentualeMargineUnitario(new BigDecimal(margineUnitario).setScale(CustomRoundingAndScaling.getScaleValue(),CustomRoundingAndScaling.getRoundingMode()));
			}
		}else {
			if (prodotto instanceof  ProdottiVenditaSSN){
				if(prodottoVenditaSSN != null){
					prodottoVenditaSSN.setPercentualeRicaricoUnitario(new BigDecimal(ricaricoUnitario).setScale(CustomRoundingAndScaling.getScaleValue(),CustomRoundingAndScaling.getRoundingMode()));
					prodottoVenditaSSN.setPercentualeMargineUnitario(new BigDecimal(margineUnitario).setScale(CustomRoundingAndScaling.getScaleValue(),CustomRoundingAndScaling.getRoundingMode()));
				}
			}
		}
	}
	
	private void mappingResoVenditaSSN(ResiVendite resoVendita,ResiVenditeSSN resoSSN, ResultSet rs){
		ResiProdottiVenditaSSN resoProdottoSSN = null;
		try{
			resoProdottoSSN = new ResiProdottiVenditaSSN();
			resoSSN.addResiProdottiVenditaSsn(resoProdottoSSN);
			resoProdottoSSN.setResiVenditeSsn(resoSSN);
			resoProdottoSSN.setNumreg(rs.getInt("NUMREG"));
			resoProdottoSSN.setMinsan(rs.getString("MINSAN10"));
			resoProdottoSSN.setDescrizione(rs.getString("DESCRIZ"));
			resoProdottoSSN.setQuantita(rs.getInt("QUANT"));
			resoProdottoSSN.setDataReso(rs.getDate("DATAREG"));
			resoProdottoSSN.setPrezzoFustello(rs.getBigDecimal("PREZZO_F"));
			resoProdottoSSN.setPrezzoPraticato(rs.getBigDecimal("PREZZO"));
			resoProdottoSSN.setPrezzoRimborso(rs.getBigDecimal("PREZZOPR"));
			resoProdottoSSN.setQuotaPezzo(rs.getBigDecimal("QUOTAPZ"));
			resoProdottoSSN.setQuotaAssistito(rs.getBigDecimal("QASS"));// si riferisce alla quantità totale
			resoProdottoSSN.setAliquotaIva(rs.getInt("ALIVA"));
			
			ProdottiVenditaSSN prodottoVenditaSSN = ProdottiVenditaSSNDAOManager.lookupProdottoPerReso(resoProdottoSSN.getMinsan(),resoProdottoSSN.getDataReso());
			if(prodottoVenditaSSN != null)
				resoProdottoSSN.setCostoCompresoIva(prodottoVenditaSSN.getCostoCompresoIva());
			else
				resoProdottoSSN.setCostoCompresoIva(rs.getBigDecimal("COSTO"));

			if (resoProdottoSSN.getCostoCompresoIva().doubleValue() > 0){
				double tempCostoDeivato = resoProdottoSSN.getCostoCompresoIva().doubleValue()/(1+((double)resoProdottoSSN.getAliquotaIva())/100);
				resoProdottoSSN.setCostoNettoIva(new BigDecimal(tempCostoDeivato).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			}
			
			//calcolo degli sconti ASL+Legge
			resoProdottoSSN.setPercentualeScontoSSN(rs.getBigDecimal("SCONTOASL"));
			if(resoProdottoSSN.getPercentualeScontoSSN().doubleValue() > 0){
				// calcolo scontoSSN: percentuale di sconto( fasce di sconto) sul prezzo di fustello deivato
				double tempPrezzoFustelloDeivato = resoProdottoSSN.getPrezzoFustello().doubleValue()/(1+((double)resoProdottoSSN.getAliquotaIva())/100);
				double tempValoreScontoSSN = (tempPrezzoFustelloDeivato * resoProdottoSSN.getPercentualeScontoSSN().doubleValue())/100;
				resoProdottoSSN.setValoreScontoSSN(new BigDecimal(tempValoreScontoSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
				
				//calcolo scontoSSNExtra: 2,25% sul prezzo di fustello deivato
				double tempValoreScontoSSNExtra = (tempPrezzoFustelloDeivato*(2.25))/100;
				resoProdottoSSN.setValoreScontoSSNExtra(new BigDecimal(tempValoreScontoSSNExtra).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			}
			
			//calcolo sconto 0,64% PayBack come differenza fra PREZZO_F-PREZZO
			double tempScontoPayBack = resoProdottoSSN.getPrezzoFustello().doubleValue() - resoProdottoSSN.getPrezzoPraticato().doubleValue();
			if ((tempScontoPayBack > 0) && (!(rs.getString("K_ESENZION").equals("ESA"))))
				resoProdottoSSN.setValoreScontoAifa(new BigDecimal(tempScontoPayBack).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			
			//calcolo dello scontoTotaleUnitario
			resoProdottoSSN.setTotaleScontoUnitario(resoProdottoSSN.getValoreScontoAifa().add(resoProdottoSSN.getValoreScontoSSN().add(resoProdottoSSN.getValoreScontoSSNExtra())).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			

			/*
			 * ResoVenditaSSN
			 */
			//aggiorno totali pezzi resi per il resoVenditaSSN
			resoSSN.setTotalePezziResi(resoSSN.getTotalePezziResi() + resoProdottoSSN.getQuantita());
			
			//aggirno totale ricetta
			resoSSN.setQuotaAssistito(resoSSN.getQuotaAssistito().add(resoProdottoSSN.getQuotaAssistito()));
			resoSSN.setTotaleRicetta(resoSSN.getTotaleRicetta().add(resoSSN.getQuotaAssistito()));
			
			//calcolo Totali Sconti SSN
			BigDecimal tempTotSconto = resoProdottoSSN.getValoreScontoAifa().add(resoProdottoSSN.getValoreScontoSSN().add(resoProdottoSSN.getValoreScontoSSNExtra()));
			BigDecimal totSconto = (tempTotSconto.multiply(new BigDecimal(resoProdottoSSN.getQuantita()))).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
			resoSSN.setTotaleScontoSSN(totSconto);
			
			/*
			 *  - calcolo prezzoVenditaNetto: (prezzo di Fustello - totaleScontoUnitario) al netto dell'iva.
			 *  - calcolo totale reso
			 *  
			 *    Bisogna distinguere i prodotti per diabetici
			 */
			double tempPrezzoVenditaNetto = 0;
			double tempPrezzoVendita = 0;
			if(rs.getString("K_ESENZION").equals("ESA")){
				tempPrezzoVendita = resoProdottoSSN.getPrezzoPraticato().doubleValue();
			}else{
				tempPrezzoVendita = resoProdottoSSN.getPrezzoFustello().doubleValue();
			}
			tempPrezzoVenditaNetto = (tempPrezzoVendita - resoProdottoSSN.getTotaleScontoUnitario().doubleValue())/(1+((double)resoProdottoSSN.getAliquotaIva())/100);
			resoProdottoSSN.setPrezzoVenditaNetto(new BigDecimal(tempPrezzoVenditaNetto).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));

			// calcolo del profitto unitario
			resoProdottoSSN.setProfittoUnitario(resoProdottoSSN.getPrezzoVenditaNetto().subtract(resoProdottoSSN.getCostoNettoIva()));

			//calcolo TotaleResoSSN
			double totaleReso = tempPrezzoVendita * ((double)resoProdottoSSN.getQuantita());
			resoSSN.setValoreVenditaSSN(resoSSN.getValoreVenditaSSN().add(new BigDecimal(totaleReso).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));
					
			//aggiorno i totali generale ResiVendite
			resoVendita.setTotalePezziResi(resoVendita.getTotalePezziResi() + resoProdottoSSN.getQuantita());
			resoVendita.setTotaleResoVendita(resoVendita.getTotaleResoVendita().add(new BigDecimal(totaleReso).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));
					
		}catch(SQLException ex){
			logger.error("mappingResoSSN(): I can't create record...",ex);
		}
	}
	
	private void mappingResoVenditaLibera(ResiVendite resoVendita, ResiVenditeLibere resoVenditaLibera, ResultSet rs){
		try{
			ResiProdottiVenditaLibera resoProdotto = new ResiProdottiVenditaLibera();
			resoProdotto.setResiVenditeLibere(resoVenditaLibera);
			resoVenditaLibera.addResiProdottiVenditaLibera(resoProdotto);
			resoProdotto.setNumreg(rs.getInt("NUMREG"));
			resoProdotto.setMinsan(rs.getString("MINSAN10"));
			resoProdotto.setDescrizione(rs.getString("DESCRIZ"));
			resoProdotto.setDataReso(rs.getDate("DATAREG"));
			resoProdotto.setPrezzoVendita(rs.getBigDecimal("PREZZO_F"));
			resoProdotto.setScontoProdotti(rs.getBigDecimal("SCONTO"));
			resoProdotto.setAliquotaIva(rs.getInt("ALIVA"));
			ProdottiVenditaLibera prodottoVenditaLibera = ProdottiVenditaLiberaDAOManager.findProdottoPerReso(resoProdotto.getMinsan(),resoProdotto.getDataReso());
			if(prodottoVenditaLibera != null)
				resoProdotto.setCostoCompresoIva(prodottoVenditaLibera.getCostoCompresoIva());
			else
				resoProdotto.setCostoCompresoIva(rs.getBigDecimal("COSTO"));
			//calcolo costo deivato
			if (resoProdotto.getCostoCompresoIva().doubleValue() > 0){
				double tempCostoDeivato = resoProdotto.getCostoCompresoIva().doubleValue()/(1+((double)resoProdotto.getAliquotaIva())/100);
				resoProdotto.setCostoNettoIva(new BigDecimal(tempCostoDeivato).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			}
			resoProdotto.setQuantita(rs.getInt("QUANT"));

			
			//se c'è differenza fra PREZZO_F e PREZZO valorizzo lo sconto payBack: tengo conto che non siano prodotti integrative regionale
			//if (!(rs.getString("K_ESENZION").equals("ESA"))){
				double tempPrezzoPayBack = rs.getBigDecimal("PREZZO").doubleValue();
				if (tempPrezzoPayBack > 0){
					double tempScontoPayBack = resoProdotto.getPrezzoVendita().doubleValue() - tempPrezzoPayBack;
					if (tempScontoPayBack > 0){
						resoProdotto.setScontoPayBack(new BigDecimal(tempScontoPayBack).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
					}
				}
			//}
			
			//calcolo scontoUnitario
			double tempScontoUnitario = (resoProdotto.getScontoProdotti().doubleValue()/resoProdotto.getQuantita()) + resoProdotto.getScontoPayBack().doubleValue();
			resoProdotto.setTotaleScontoUnitario(new BigDecimal(tempScontoUnitario).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			
			//calcolo PrezzoVenditaNetto: prezzo al netto dello sconto e dell'iva
			double tempPrezzoVenditaNetto = ( (resoProdotto.getPrezzoVendita().doubleValue()-resoProdotto.getTotaleScontoUnitario().doubleValue())/(1+((double)resoProdotto.getAliquotaIva())/100));
			resoProdotto.setPrezzoVenditaNetto(new BigDecimal(tempPrezzoVenditaNetto).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			
			// calcolo del profitto unitario
			resoProdotto.setProfittoUnitario(resoProdotto.getPrezzoVenditaNetto().subtract(resoProdotto.getCostoNettoIva()));

			/*
			 * Aggiorno ResoVenditaLibera e ResiVendita
			 */
			double tempValoreVendita = resoProdotto.getPrezzoVendita().doubleValue()*resoProdotto.getQuantita();
			double tempScontoProdotti = (resoProdotto.getScontoPayBack().doubleValue() * resoProdotto.getQuantita()) + resoProdotto.getScontoProdotti().doubleValue();
			double tempValoreVenditaScontata = tempValoreVendita - tempScontoProdotti;
			BigDecimal valoreVendita = new BigDecimal(tempValoreVendita).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
			BigDecimal valoreVenditaScontata = new BigDecimal(tempValoreVenditaScontata).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
			BigDecimal scontoProdotti = new BigDecimal(tempScontoProdotti).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
			resoVenditaLibera.setValoreVenditaLibera(resoVenditaLibera.getValoreVenditaLibera().add(valoreVendita));
			resoVendita.setTotaleResoVendita(resoVendita.getTotaleResoVendita().add(valoreVendita));
			resoVenditaLibera.setTotaleScontoProdotto(resoVenditaLibera.getTotaleScontoProdotto().add(scontoProdotti));
			resoVenditaLibera.setTotaleVenditaScontata(resoVenditaLibera.getTotaleVenditaScontata().add(valoreVenditaScontata));
			resoVendita.setTotaleResoVenditaScontata(resoVendita.getTotaleResoVenditaScontata().add(valoreVenditaScontata));
			resoVenditaLibera.setTotalePezziResi(resoVenditaLibera.getTotalePezziResi()+resoProdotto.getQuantita());
			resoVendita.setTotalePezziResi(resoVendita.getTotalePezziResi()+resoProdotto.getQuantita());

		}catch(SQLException ex){
			logger.error("mappingResoVenditaLibera(): I can't create record...",ex);
		}
	}
	
	private void valorizzaCosto(Object prodotto, BigDecimal costoMovimento, Integer aliquotaIva, String minsan,BigDecimal prezzoFustello) {
		ProdottiVenditaLibera prodottoVenditaLibera;
		ProdottiVenditaSSN prodottoVenditaSSN;
		boolean costoReale = false;
		double costoProdotto = 0;
		double iva = 1 + aliquotaIva.doubleValue()/100;
		double percCostoPres = percentualeCostoPresunto.doubleValue();
		Giacenze giacenza;
		// FASE AVVIO
		if (importazioneIniziale) {
			giacenza = GiacenzeDAOManager.findByMinsan(minsan);
			if (giacenza.getCostoUltimoDeivato().doubleValue() == 0) {
				costoReale = false;
				if(costoMovimento.doubleValue()!=0)
					costoProdotto = costoMovimento.doubleValue();
				else {
					costoProdotto = prezzoFustello.doubleValue() - (prezzoFustello.doubleValue()* percCostoPres/100);
				}
			}
			else {
				costoProdotto = giacenza.getCostoUltimoDeivato().doubleValue() * iva;
				costoReale = true;
			}
		}
		// fase REGIME
		else {
			if (costoMovimento.doubleValue() == 0) {
				giacenza = GiacenzeDAOManager.findByMinsan(minsan);
				if (giacenza.getCostoUltimoDeivato().doubleValue() == 0) {
					costoReale = false;
					costoProdotto = prezzoFustello.doubleValue() - (prezzoFustello.doubleValue()* percCostoPres/100);
				}
				else {
					costoReale = true;
					costoProdotto = giacenza.getCostoUltimoDeivato().doubleValue() * iva;
				}
			}
			else {
				costoProdotto = costoMovimento.doubleValue();
				costoReale = true;
			}				
		}
					
		if (prodotto instanceof ProdottiVenditaLibera) { 
			prodottoVenditaLibera = (ProdottiVenditaLibera) prodotto;
			prodottoVenditaLibera.setCostoCompresoIva(new BigDecimal(costoProdotto).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			if(costoReale)
				prodottoVenditaLibera.setTipoCosto(TipoCosto.REALE);
			else
				prodottoVenditaLibera.setTipoCosto(TipoCosto.PRESUNTO);
		}
		else {
			prodottoVenditaSSN = (ProdottiVenditaSSN) prodotto;
			prodottoVenditaSSN.setCostoCompresoIva(new BigDecimal(costoProdotto).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
			if(costoReale)
				prodottoVenditaSSN.setTipoCosto(TipoCosto.REALE);
			else
				prodottoVenditaSSN.setTipoCosto(TipoCosto.PRESUNTO);
		}
	}
	
	private void insertMovimento(Vendite venditaGenerale){
		
		//decurto il totale vendita generale dello scontoVendita( SCONTOCL from DBF)
		venditaGenerale.setTotaleVenditaScontata(venditaGenerale.getTotaleVenditaScontata().subtract(venditaGenerale.getScontoVendita()));
		
		/*
		 * per ogni prodotto della vendita bisogna assegnare la quota di sconto generale ed aggiornare il prezzoVenditaNetto ed il RicavoUnitario ed totaleScontoProdotto:
		 * 	- differenziare VenditeLibere da VenditeSSN
		 * 	- assegnare il valore della quota 
		 * 	- aggiungere l'iva al PrezzoVenditaNetto
		 *  - detrarre dal PrezzoUnitarioVenditaNetto la quota di sconto VenditaGenerale
		 *  - deivare il prezzoDiVenditaNettoUnitario
		 *  - ricalcolare il RicavoUnitario: prezzoVenditaNettoUnitario - Costo
		 */
		double quotaScontoRipartito = 0;
		double costoTemp;
		double scontoVendita = venditaGenerale.getScontoVendita().doubleValue();
		if (scontoVendita > 0)
		{
			quotaScontoRipartito = scontoVendita / venditaGenerale.getTotalePezziVenduti();
			double divisoreIva;
			BigDecimal deivatoTemp;
			BigDecimal totaleSconti;
			
			// VenditeLibere
			ArrayList<VenditeLibere> venditeLibere = (ArrayList<VenditeLibere>)venditaGenerale.getVenditeLibere();
			ArrayList<ProdottiVenditaLibera> prodottiVenditaLibera;
			ProdottiVenditaLibera prodottoVenditaLibera;
			VenditeLibere venditaLibera;
			if (venditeLibere != null)
			{	
				Iterator<VenditeLibere> iterVenditeLibere = venditeLibere.iterator();
				while(iterVenditeLibere.hasNext()){
					venditaLibera = iterVenditeLibere.next();
					prodottiVenditaLibera = (ArrayList<ProdottiVenditaLibera>)venditaLibera.getProdottiVenduti();
					Iterator<ProdottiVenditaLibera> iterProdottiVednitaLibera = prodottiVenditaLibera.iterator();
					while (iterProdottiVednitaLibera.hasNext()){
						prodottoVenditaLibera = iterProdottiVednitaLibera.next();
						prodottoVenditaLibera.setScontoVenditaRipartito(new BigDecimal(quotaScontoRipartito).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
						
						//aggiorno il totaleScontoUnitario
						prodottoVenditaLibera.setTotaleScontoUnitario(prodottoVenditaLibera.getTotaleScontoUnitario().add(new BigDecimal(quotaScontoRipartito).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));
	
						/*
						 *  aggiornamento prezzoVenditaNetto e RicavoUnitario
						 */
						double tempPrezzoVenditaNetto = (prodottoVenditaLibera.getPrezzoVendita().doubleValue() - prodottoVenditaLibera.getTotaleScontoUnitario().doubleValue())/(1+((double)prodottoVenditaLibera.getAliquotaIva())/100);
						prodottoVenditaLibera.setPrezzoVenditaNetto(new BigDecimal(tempPrezzoVenditaNetto).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
						prodottoVenditaLibera.setProfittoUnitario(prodottoVenditaLibera.getPrezzoVenditaNetto().subtract(prodottoVenditaLibera.getCostoNettoIva()));
						setProfittoMargineRicarico(prodottoVenditaLibera);
						/*
						if ((prodottoVenditaLibera.getCostoCompresoIva().doubleValue() > 0) ){
								prodottoVenditaLibera.setProfittoUnitario(prodottoVenditaLibera.getPrezzoVenditaNetto().subtract(prodottoVenditaLibera.getCostoNettoIva()));
								if (prodottoVenditaLibera.getProfittoUnitario().doubleValue() > 0){
								double tempMargine = (prodottoVenditaLibera.getProfittoUnitario().doubleValue()/prodottoVenditaLibera.getPrezzoVenditaNetto().doubleValue())*100;
								double tempRicarico = (prodottoVenditaLibera.getProfittoUnitario().doubleValue()/prodottoVenditaLibera.getCostoNettoIva().doubleValue())*100;
								prodottoVenditaLibera.setPercentualeMargineUnitario(new BigDecimal(tempMargine).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
								prodottoVenditaLibera.setPercentualeRicaricoUnitario(new BigDecimal(tempRicarico).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
							}
							else{
									prodottoVenditaLibera.setPercentualeMargineUnitario(new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
									prodottoVenditaLibera.setPercentualeRicaricoUnitario(new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
								}
						}

						 */
					}
					
					//aggiorno i dati relativi alla VenditaLibera: aggiungo lo sconto Ripartito al totale sconto prodotti; decurto il totaleVenditaScontata del totaleScontoRipartito
					double tempTotaleScontoRipartitoVenditaLibera =  quotaScontoRipartito * venditaLibera.getTotalePezziVenduti();
					venditaLibera.setTotaleScontoProdotto(venditaLibera.getTotaleScontoProdotto().add(new BigDecimal(tempTotaleScontoRipartitoVenditaLibera).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));
					venditaLibera.setTotaleVenditaScontata(venditaLibera.getTotaleVenditaScontata().subtract(new BigDecimal(tempTotaleScontoRipartitoVenditaLibera).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));
				}
			}
			/* Assegno il valore della quotaUnitaria delo sconto ad ogni prodotto delle VenditeSSN
			 * 
			 * ******ATTENZIONE: biosgna differenziare i prodotti per diabetici: il prezzo di vendita di cui tener conto è il prezzoPraticato *******
			 */
			ArrayList<VenditeSSN> venditeSSN = (ArrayList<VenditeSSN>)venditaGenerale.getVenditeSSN();
			Iterator<VenditeSSN> iterVenditeSSN; 
			VenditeSSN venditaSSN;
			ArrayList<ProdottiVenditaSSN> prodottiVendutiSSN;
			ProdottiVenditaSSN prodottoVenditaSSN;
			if (venditeSSN != null)
			{	
				iterVenditeSSN = venditeSSN.iterator();
				while(iterVenditeSSN.hasNext()){
					venditaSSN = iterVenditeSSN.next();
					prodottiVendutiSSN = (ArrayList<ProdottiVenditaSSN>)venditaSSN.getProdottiSsnVenduti();
					Iterator<ProdottiVenditaSSN>  iterProdottiVenditaSSN = prodottiVendutiSSN.iterator();
					while(iterProdottiVenditaSSN.hasNext()){
						prodottoVenditaSSN = iterProdottiVenditaSSN.next();
						prodottoVenditaSSN.setScontoVenditaRipartito(new BigDecimal(quotaScontoRipartito));
						prodottoVenditaSSN.setTotaleScontoUnitario(prodottoVenditaSSN.getTotaleScontoUnitario().add(new BigDecimal(quotaScontoRipartito).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));
	
						//controllo che siano dei prodotti per diabetici
						double tempPrezzoVenditaLordo;
						if(venditaSSN.getEsenzione().equals("ESA")){
							tempPrezzoVenditaLordo = prodottoVenditaSSN.getPrezzoPraticato().doubleValue();
						}else{
							tempPrezzoVenditaLordo = prodottoVenditaSSN.getPrezzoFustello().doubleValue();
						}
						// aggiornamento prezzoVenditaNetto
						double tempPrezzoVenditaNetto = (tempPrezzoVenditaLordo - prodottoVenditaSSN.getTotaleScontoUnitario().doubleValue())/(1+((double)prodottoVenditaSSN.getAliquotaIva())/100);
						prodottoVenditaSSN.setPrezzoVenditaNetto(new BigDecimal(tempPrezzoVenditaNetto).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));

						// aggiorno ProfittoUnitario
						double tempProfitto = prodottoVenditaSSN.getPrezzoVenditaNetto().doubleValue() - prodottoVenditaSSN.getCostoNettoIva().doubleValue();
						prodottoVenditaSSN.setProfittoUnitario(new BigDecimal(tempProfitto).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
						setProfittoMargineRicarico(prodottoVenditaSSN);
						/*
						if (prodottoVenditaSSN.getProfittoUnitario().doubleValue() != 0){
							if (prodottoVenditaSSN.getProfittoUnitario().doubleValue() > 0){
								double tempMargine = (tempProfitto/prodottoVenditaSSN.getPrezzoVenditaNetto().doubleValue())*100;
								double tempRicarico = (tempProfitto/prodottoVenditaSSN.getCostoNettoIva().doubleValue())*100;
								prodottoVenditaSSN.setPercentualeMargineUnitario(new BigDecimal(tempMargine).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
								prodottoVenditaSSN.setPercentualeRicaricoUnitario(new BigDecimal(tempRicarico).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
							}
						}

						 */
					}
					
					//aggiorno i dati di VenditaSSN: aggiungo l'eventuale scontoRipartito in scontoSSN e ricalcolo la Vendita scontata
					double tempTotaleScontoRipartito = quotaScontoRipartito * venditaSSN.getTotalePezziVenduti();
					venditaSSN.setTotaleScontoSSN(venditaSSN.getTotaleScontoSSN().add(new BigDecimal(tempTotaleScontoRipartito).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode())));			
				}
			}
		}
		
		venditaGenerale = VenditeDAOManager.inserimentoVenditaComposta(venditaGenerale);
		
		TotaliGeneraliVenditaEstrattiDAOManager.aggiornaTotaliGenerali(venditaGenerale);
		TotaliGeneraliVenditaEstrattiGiornalieriDAOManager.aggiornaTotaliGenerali(venditaGenerale);
	}

	private void insertResi(ResiVendite resoVendite){
		
		//resoVendite = ResiDAOManager.updateResoVendita(resoVendite);
		updateResi(resoVendite);
	}
	
	private void updateResi(ResiVendite resoVendite){
		
		resoVendite = ResiDAOManager.updateResoVendita(resoVendite);
		
	}
	
	/**
	 * Riporta le quantità reso nella colonna quantitaReso del record Prodotti* 
	 * @param reso
	 */
	private static void aggiornaQuantitaResoProdotto(Object reso) {
		
		ProdottiVenditaLibera prodottoVenditaLibera;
		ProdottiVenditaSSN prodottoVenditaSSN;
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoVenditaLiberaDAO;
		ProdottiVenditaSSNDAO prodottoVenditaSSNDAO;
		ResiProdottiVenditaLibera resoVenditaLibera;
		ResiProdottiVenditaSSN resoVenditaSSN;
		boolean exit = true;
		int capacitaReso = 0;
		int quantitaReso = 0;
		if(reso instanceof ResiProdottiVenditaLibera) {
			resoVenditaLibera = (ResiProdottiVenditaLibera) reso;
			prodottoVenditaLiberaDAO = daoFactory.getProdottoVenditaLiberaDAO();
			quantitaReso = resoVenditaLibera.getQuantita();
			do {
				prodottoVenditaLibera  = prodottoVenditaLiberaDAO.lookUpProdottoReso(resoVenditaLibera.getMinsan(), resoVenditaLibera.getDataReso());
				if (prodottoVenditaLibera.getIdProdottoVenditaLibera() == null)
					exit = true;
				else {
					capacitaReso = prodottoVenditaLibera.getQuantita().intValue() - prodottoVenditaLibera.getQuantitaReso().intValue();
					if (capacitaReso >= quantitaReso) {
						prodottoVenditaLibera.setQuantitaReso(prodottoVenditaLibera.getQuantitaReso().intValue()+quantitaReso);
						prodottoVenditaLibera = ProdottiVenditaLiberaDAOManager.modifica(prodottoVenditaLibera);
						exit = true;
					}
					else {
						prodottoVenditaLibera.setQuantitaReso(prodottoVenditaLibera.getQuantitaReso().intValue()+capacitaReso);
						prodottoVenditaLibera = ProdottiVenditaLiberaDAOManager.modifica(prodottoVenditaLibera);
						quantitaReso = quantitaReso - capacitaReso;
						exit = false;
					}
				}
			}while(!exit);
		}
		
		if (reso instanceof ResiProdottiVenditaSSN) {
			resoVenditaSSN   = (ResiProdottiVenditaSSN) reso;
			prodottoVenditaSSNDAO = daoFactory.getProdottoVenditaSSNDAO();
			quantitaReso = resoVenditaSSN.getQuantita();
			do {
				prodottoVenditaSSN = ProdottiVenditaSSNDAOManager.lookupProdottoPerReso(resoVenditaSSN.getMinsan(), resoVenditaSSN.getDataReso());
				if (prodottoVenditaSSN.getIdProdottoVenditaSSN() == null)
					exit = true;
				else {
					capacitaReso = prodottoVenditaSSN.getQuantita().intValue()-prodottoVenditaSSN.getQuantitaReso().intValue();
					if (capacitaReso >= quantitaReso) {
						prodottoVenditaSSN.setQuantitaReso(prodottoVenditaSSN.getQuantitaReso().intValue()+quantitaReso);
						prodottoVenditaSSN = ProdottiVenditaSSNDAOManager.modifica(prodottoVenditaSSN);
						exit = true;
					}
					else {
						prodottoVenditaSSN.setQuantitaReso(prodottoVenditaSSN.getQuantitaReso()+capacitaReso);
						prodottoVenditaSSN = ProdottiVenditaSSNDAOManager.modifica(prodottoVenditaSSN);
						quantitaReso = quantitaReso - capacitaReso;
						exit = false;
					}
				}
			}while(!exit);
		}
	}

	
	
	// test per connessione alla tabella dbf
	public void testConnection(){		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT count(*) FROM "+propsFarmaManager.getProperty("dbfTabellaName")+" ");
			if (rs.next()) System.out.println(rs.getInt(1));		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if (rs != null)
				try{
					rs.close();
				} catch(Exception ex){
					ex.printStackTrace();
				}
			if (stmt != null)
				try{
					stmt.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			if (connection != null)
				try{
					connection.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
		}	
	}
	
	/**
	 * Aggiorna la Tabella Importazioni: creando un record relativo all'importazione appena terminata
	 */
	public void aggiornaLogImportazioni(){

		ImportazioniDAOManager.insertLogImportazione("insert");

	}
	
}
