package com.klugesoftware.farmamanager.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.klugesoftware.farmamanager.model.CustomRoundingAndScaling;
import com.klugesoftware.farmamanager.model.Giacenze;
import com.klugesoftware.farmamanager.model.Importazioni;
import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ProdottiVenditaSSN;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaSSN;
import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.ResiVenditeLibere;
import com.klugesoftware.farmamanager.model.ResiVenditeSSN;
import com.klugesoftware.farmamanager.model.TipoCosto;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.model.VenditeLibere;
import com.klugesoftware.farmamanager.model.VenditeSSN;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstratti;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstrattiGiornalieri;
import com.klugesoftware.farmamanager.DTO.ElencoProdottiLiberaVenditaRowData;
import com.klugesoftware.farmamanager.DTO.ElencoMinsanLiberaVenditaRowData;
import com.klugesoftware.farmamanager.DTO.ElencoTotaliGiornalieriRowData;
import com.klugesoftware.farmamanager.utility.DateUtility;

public final class DAOUtil {
	
	private DAOUtil(){
	
	}

	public static PreparedStatement prepareStatement(Connection connection, String sql, boolean returnGeneretedKeys,Object...values) throws SQLException{
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql, returnGeneretedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		
		setValues(preparedStatement, values);
		
		return preparedStatement;
	
	}
	

	public static void setValues(PreparedStatement preparedStatement, Object...values) throws SQLException{
		
		for (int i = 0; i < values.length; i++){
			
			preparedStatement.setObject(i+1, values[i]);
		}
		
	}
	
	public static void close(Connection connection){

		if (connection != null){
			
			try {
				connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
	
	public static void close(Statement statement){
		
		if (statement != null){
			
			try {
				statement.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet resultSet){
		
		if (resultSet != null){
			
			try {
				resultSet.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		
	}
	
	public static void close(Connection connection,Statement statement){
		
		close(statement);
		
		close(connection);
	}
	
	public static void close(Connection connection,Statement statement,ResultSet resultSet){
	
		close(resultSet);

		close(statement);
		
		close(connection);
		
	}
	
	public static ElencoProdottiLiberaVenditaRowData mapElencoProdottiLiberaVenditaRowData(ResultSet resultSet) throws SQLException{
		return new ElencoProdottiLiberaVenditaRowData(
				new Integer(resultSet.getInt("idProdottoVenditaLibera")), 
				DateUtility.converteDateToGUIStringDDMMYYYY(resultSet.getDate("dataVendita")), 
				resultSet.getString("minsan"), 
				resultSet.getString("descrizione"), 
				new Integer(resultSet.getInt("quantitaVendute")), 
				resultSet.getBigDecimal("prezzoVendita"), 
				resultSet.getBigDecimal("totaleScontoUnitario"), 
				resultSet.getBigDecimal("prezzoVenditaNetto"), 
				resultSet.getBigDecimal("costoNettoIva"), 
				resultSet.getBigDecimal("percentualeMargineUnitario"), 
				resultSet.getBigDecimal("percentualeRicaricoUnitario"),
				resultSet.getBigDecimal("profittoUnitario"));
	}
	
	public static ElencoMinsanLiberaVenditaRowData mapElencoMinsanLiberaVenditaRowData(ResultSet rs) throws SQLException{
		//FIXME: da correggere in modo che il profitto sia negativo in caso di prezzoVeNett = 0
		Integer tempQuantitaTotale = new Integer(rs.getString("quantitaTotale"));
		double tempProfittoMedio=0;
		double tempMargineMedio=0;
		double tempRicaricoMedio=0;

		tempProfittoMedio = rs.getBigDecimal("prezzoVenditaNettoMedio").doubleValue() - rs.getBigDecimal("costoMedio").doubleValue();

		if ( (rs.getBigDecimal("prezzoVenditaNettoMedio").doubleValue() != 0) && (rs.getBigDecimal("costoMedio").doubleValue() != 0)  ){

			tempMargineMedio = (tempProfittoMedio/rs.getBigDecimal("prezzoVenditaNettoMedio").doubleValue()) * 100;
			tempRicaricoMedio = (tempProfittoMedio/rs.getBigDecimal("costoMedio").doubleValue()) * 100;
		}
		return new ElencoMinsanLiberaVenditaRowData(
				rs.getString("minsan"), 
				rs.getString("descrizione"), 
				tempQuantitaTotale, 
				rs.getBigDecimal("prezzoVenditaMedio").setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()), 
				rs.getBigDecimal("prezzoVenditaNettoMedio").setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()),
				rs.getBigDecimal("scontoMedio").setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()),
				rs.getBigDecimal("costoMedio").setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()),
				new BigDecimal(tempMargineMedio).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()),
				new BigDecimal(tempRicaricoMedio).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()), 
				new BigDecimal(tempProfittoMedio).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
	
	}
	
	public static ElencoTotaliGiornalieriRowData mapElencoTotaliGiornalieriRowData(ResultSet rs) throws SQLException{
		return new ElencoTotaliGiornalieriRowData(
				DateUtility.converteDateToGUIStringDDMMYYYY(rs.getDate("data")), 
				rs.getBigDecimal("totaleVenditeLorde"), 
				rs.getBigDecimal("totaleProfitti"),
				rs.getBigDecimal("totaleVenditeLordeSSN"),
				rs.getBigDecimal("totaleProfittiSSN"), 
				rs.getBigDecimal("totaleVenditeLordeLibere"), 
				rs.getBigDecimal("totaleProfittiLibere"));
	}
	
	public static ProdottiVenditaLibera mapProdottoVenditaLibera(ResultSet rs) throws SQLException{
		VenditeLibere venditaLibera = new VenditeLibere();
		venditaLibera.setIdVenditaLibera(rs.getInt("idVenditaLibera"));
		return new ProdottiVenditaLibera(
				new Integer(rs.getString("idProdottoVenditaLibera")), 
				rs.getInt("numreg"), 
				new Integer(rs.getInt("aliquotaIva")), 
				rs.getBigDecimal("costoCompresoIva"), 
				rs.getBigDecimal("costoNettoIva"), 
				( (rs.getString("tipoCosto")).equals(TipoCosto.PRESUNTO.toString()) ? TipoCosto.PRESUNTO : TipoCosto.REALE ), 
				rs.getString("deGrassi"),
				rs.getString("descrizione"),
				new Integer(rs.getInt("posizioneInVendita")), 
				rs.getString("minsan"), 
				rs.getBigDecimal("prezzoVendita"), 
				rs.getBigDecimal("prezzoPraticato"), 
				new Integer(rs.getInt("quantita")), 
				rs.getBigDecimal("scontoProdotti"), 
				rs.getBigDecimal("scontoPayBack"), 
				rs.getBigDecimal("scontoVenditaRipartito"), 
				rs.getBigDecimal("totaleScontoUnitario"), 
				rs.getBigDecimal("prezzoVenditaNetto"), 
				rs.getBigDecimal("profittoUnitario"), 
				rs.getBigDecimal("percentualeMargineUnitario"), 
				rs.getBigDecimal("percentualeRicaricoUnitario"), 
				rs.getString("tipoProdotto"), 
				rs.getString("tipoRicetta"),
				rs.getDate("dataVendita"),
				rs.getInt("quantitaReso"),
				venditaLibera);
	}
	
	public static ProdottiVenditaSSN mapProdottoVenditaSSN(ResultSet rs) throws SQLException{
		VenditeSSN venditaSSN = new VenditeSSN();
		venditaSSN.setIdVenditaSSN(rs.getInt("idVenditaSSN"));
		return new ProdottiVenditaSSN(
					new Integer(rs.getInt("idProdottoVenditaSSN")), 
					new Integer(rs.getInt("numreg")), 
					new Integer(rs.getInt("aliquotaIva")), 
					rs.getString("codiceAtcGmp"), 
					rs.getBigDecimal("costoCompresoIva"), 
					rs.getBigDecimal("costoNettoIva"), 
					( (rs.getString("tipoCosto")).equals(TipoCosto.PRESUNTO.toString()) ? TipoCosto.PRESUNTO : TipoCosto.REALE ),
					rs.getString("deGrassi"), 
					rs.getString("descrizione"), 
					new Integer(rs.getInt("posizioneInVendita")),
					rs.getString("minsan"), 
					rs.getBigDecimal("percentualeScontoSSN"), 
					rs.getBigDecimal("valoreScontoSSNExtra"), 
					rs.getBigDecimal("valoreScontoAifa"), 
					rs.getBigDecimal("prezzoFustello"), 
					rs.getBigDecimal("prezzoPraticato"), 
					rs.getBigDecimal("prezzoRimborso"), 
					new Integer(rs.getInt("quantita")), 
					rs.getBigDecimal("quotaAssistito"), 
					rs.getBigDecimal("quotaPezzo"), 
					rs.getBigDecimal("valoreScontoSSN"), 
					rs.getBigDecimal("scontoVenditaRipartito"), 
					rs.getBigDecimal("totaleScontoUnitario"), 
					rs.getBigDecimal("prezzoVenditaNetto"), 
					rs.getBigDecimal("profittoUnitario"), 
					rs.getBigDecimal("percentualeMargineUnitario"), 
					rs.getBigDecimal("percentualeRicaricoUnitario"), 
					rs.getString("tipoProdotto"), 
					rs.getString("tipoRicetta"),
					rs.getDate("dataVendita"),
					rs.getInt("quantitaReso"),
					venditaSSN
					);
	}
	
	public static VenditeLibere mapVenditaLibera(ResultSet rs) throws SQLException{
		return new VenditeLibere(
				new Integer(rs.getInt("idVenditaLibera")), 
				new Integer(rs.getInt("numreg")), 
				rs.getString("codiceFiscale"), 
				rs.getDate("dataVendita"), 
				new Integer(rs.getInt("posizioneInVendita")), 
				rs.getBigDecimal("totaleIva"), 
				new Integer(rs.getInt("totalePezziVenduti")), 
				rs.getBigDecimal("totaleScontoProdotto"), 
				rs.getBigDecimal("valoreVenditaLibera"),
				rs.getBigDecimal("totaleVenditaScontata"));
	}
	
	public static VenditeSSN mapVenditaSSN(ResultSet rs) throws SQLException{
		return new VenditeSSN(
				new Integer(rs.getInt("idVenditaSSN")), 
				new Integer(rs.getInt("numreg")), 
				rs.getDate("dataVenditaSSN"), 
				rs.getString("codiceFiscale"), 
				rs.getString("esenzione"), 
				new Integer(rs.getInt("posizioneInVendita")), 
				rs.getBigDecimal("totaleIva"), 
				new Integer(rs.getInt("totalePezziVenduti")), 
				rs.getBigDecimal("quotaAssistito"), 
				rs.getBigDecimal("quotaRicetta"),
				rs.getBigDecimal("totaleRicetta"),
				rs.getBigDecimal("totaleScontoSSN"),
				rs.getBigDecimal("valoreVenditaSSN")
				);
	}
	
	public static Vendite mapVendita(ResultSet rs) throws SQLException{
		return new Vendite(
				new Integer(rs.getInt("idVendita")),
				new Integer(rs.getInt("numreg")),
				rs.getDate("dataVendita"), 
				rs.getBigDecimal("totaleVendita"),
				rs.getBigDecimal("totaleVenditaScontata"),
				rs.getBigDecimal("scontoVendita"),
				new Integer(rs.getInt("totalePezziVenduti"))
				);
	}
	
	public static TotaliGeneraliVenditaEstratti mapTotaliGenerali(ResultSet rs) throws SQLException{
		return new TotaliGeneraliVenditaEstratti(
				new Integer(rs.getInt("idTotale")), 
				rs.getBigDecimal("totaleVenditeLorde"), 
				rs.getBigDecimal("totaleVenditeLordeLibere"), 
				rs.getBigDecimal("totaleVenditeLordeSSN"),
				rs.getBigDecimal("totaleVenditeNettoScontiEIva"),
				rs.getBigDecimal("totaleVenditeNettoScontiEIvaLibere"),
				rs.getBigDecimal("totaleVenditeNettoScontiEIvaSSN"),
				rs.getBigDecimal("totaleVenditeNettoSconti"),
				rs.getBigDecimal("totaleVenditeNettoScontiLibere"),
				rs.getBigDecimal("totaleVenditeNettoScontiSSN"),
				rs.getBigDecimal("totaleSconti"),
				rs.getBigDecimal("totaleScontiSSN"),
				rs.getBigDecimal("totaleScontiLibere"),
				rs.getBigDecimal("totaleCostiNettoIva"),
				rs.getBigDecimal("totaleCostiNettiIvaLibere"),
				rs.getBigDecimal("totaleCostiNettiIvaSSN"),
				rs.getBigDecimal("totaleProfitti"),
				rs.getBigDecimal("totaleProfittiLibere"),
				rs.getBigDecimal("totaleProfittiSSN"),
				rs.getBigDecimal("margineMedio"),
				rs.getBigDecimal("margineMedioLibere"),
				rs.getBigDecimal("margineMedioSSN"),
				rs.getBigDecimal("ricaricoMedio"),
				rs.getBigDecimal("ricaricoMedioLibere"),
				rs.getBigDecimal("ricaricoMedioSSN"),
				rs.getInt("giorniLavorativi"),
				rs.getInt("giorniFestivi"),
				rs.getBoolean("costiPresunti"),
				rs.getInt("mese"),
				rs.getInt("anno"),
				rs.getDate("dataUltimoAggiornamento"));
	}
	
	public static TotaliGeneraliVenditaEstrattiGiornalieri mapTotaliGeneraliGiornalieri(ResultSet rs) throws SQLException{
		return new TotaliGeneraliVenditaEstrattiGiornalieri(
				new Integer(rs.getInt("idTotale")),
				rs.getDate("data"),
				rs.getBigDecimal("totaleVenditeLorde"), 
				rs.getBigDecimal("totaleVenditeLordeLibere"), 
				rs.getBigDecimal("totaleVenditeLordeSSN"),
				rs.getBigDecimal("totaleVenditeNettoScontiEIva"),
				rs.getBigDecimal("totaleVenditeNettoScontiEIvaLibere"),
				rs.getBigDecimal("totaleVenditeNettoScontiEIvaSSN"),
				rs.getBigDecimal("totaleVenditeNettoSconti"),
				rs.getBigDecimal("totaleVenditeNettoScontiLibere"),
				rs.getBigDecimal("totaleVenditeNettoScontiSSN"),
				rs.getBigDecimal("totaleSconti"),
				rs.getBigDecimal("totaleScontiSSN"),
				rs.getBigDecimal("totaleScontiLibere"),
				rs.getBigDecimal("totaleCostiNettoIva"),
				rs.getBigDecimal("totaleCostiNettiIvaLibere"),
				rs.getBigDecimal("totaleCostiNettiIvaSSN"),
				rs.getBigDecimal("totaleProfitti"),
				rs.getBigDecimal("totaleProfittiLibere"),
				rs.getBigDecimal("totaleProfittiSSN"),
				rs.getBigDecimal("margineMedio"),
				rs.getBigDecimal("margineMedioLibere"),
				rs.getBigDecimal("margineMedioSSN"),
				rs.getBigDecimal("ricaricoMedio"),
				rs.getBigDecimal("ricaricoMedioLibere"),
				rs.getBigDecimal("ricaricoMedioSSN"),
				rs.getBoolean("costiPresunti"),
				rs.getDate("dataUltimoAggiornamento"));
	}
	
	
	public static Importazioni mapImportazioni(ResultSet rs) throws SQLException{
		return new Importazioni(
				new Integer(rs.getString("idImportazione")), 
				rs.getDate("dataImportazione"), 
				new Integer(rs.getString("ultimoNumRegImportato")),
				rs.getDate("dataUltimoMovImportato"),
				rs.getString("note"));
	}
	
	public static ResiVendite mapResiVendite(ResultSet rs) throws SQLException{
		return new ResiVendite(
				new Integer(rs.getInt("idResoVendita")), 
				rs.getDate("dataReso"), 
				new Integer(rs.getInt("numreg")), 
				rs.getBigDecimal("scontoVendita"), 
				rs.getInt("totalePezziResi"), 
				rs.getBigDecimal("totaleResoVendita"), 
				rs.getBigDecimal("totaleResoVenditaScontata"));
	}
	
	public static ResiProdottiVenditaLibera mapResiProdottiVenditaLibera(ResultSet rs) throws SQLException{
		ResiVenditeLibere reso = new ResiVenditeLibere();
		reso.setIdResoVenditaLibera(new Integer(rs.getInt("idResoVenditaLibera")));
		return new ResiProdottiVenditaLibera(
				new Integer(rs.getInt("idResoProdottoVenditaLibera")), 
				new Integer(rs.getInt("aliquotaIva")), 
				rs.getBigDecimal("costoCompresoIva"), 
				rs.getBigDecimal("costoNettoIva"), 
				rs.getString("descrizione"), 
				rs.getString("minsan"), 
				new Integer(rs.getInt("numreg")), 
				rs.getBigDecimal("prezzoPraticato"), 
				rs.getBigDecimal("prezzoVendita"), 
				rs.getBigDecimal("prezzoVenditaNetto"), 
				rs.getBigDecimal("profittoUnitario"), 
				new Integer(rs.getInt("quantita")), 
				rs.getBigDecimal("scontoPayBack"), 
				rs.getBigDecimal("scontoProdotti"), 
				rs.getBigDecimal("totaleScontoUnitario"),
				reso,
				rs.getDate("dataReso"));
	}
	
	public static ResiVenditeLibere mapResiVenditeLibere(ResultSet rs) throws SQLException{
		ResiVendite reso = new ResiVendite();
		reso.setIdResoVendita(new Integer(rs.getString("idResoVendita")));
		return new ResiVenditeLibere(
				new Integer(rs.getInt("idResoVenditaLibera")),
				rs.getString("codiceFiscale"), 
				rs.getDate("dataResoVendita"), 
				new Integer(rs.getInt("numreg")), 
				rs.getBigDecimal("totaleIva"), 
				new Integer(rs.getInt("totalePezziResi")), 
				rs.getBigDecimal("totaleScontoProdotto"), 
				rs.getBigDecimal("totaleVenditaScontata"), 
				rs.getBigDecimal("valoreVenditaLibera"), 
				reso);
	}
	
	public static ResiProdottiVenditaSSN mapResiProdottiVenditaSSN(ResultSet rs) throws SQLException{
		ResiVenditeSSN reso = new ResiVenditeSSN();
		reso.setIdResoVenditaSSN(new Integer(rs.getInt("idResoVenditaSSN")));
		return new ResiProdottiVenditaSSN(
				new Integer(rs.getInt("idResoProdottoVenditaSSN")), 
				new Integer(rs.getInt("aliquotaIva")), 
				rs.getBigDecimal("costoCompresoIva"), 
				rs.getBigDecimal("costoNettoIva"), 
				rs.getString("descrizione"), 
				rs.getString("minsan"), 
				new Integer(rs.getInt("numreg")), 
				rs.getBigDecimal("percentualeScontoSSN"), 
				rs.getBigDecimal("prezzoFustello"), 
				rs.getBigDecimal("prezzoPraticato"), 
				rs.getBigDecimal("prezzoRimborso"), 
				rs.getBigDecimal("prezzoVenditaNetto"), 
				rs.getBigDecimal("profittoUnitario"), 
				new Integer(rs.getInt("quantita")), 
				rs.getBigDecimal("quotaAssistito"), 
				rs.getBigDecimal("quotaPezzo"), 
				rs.getBigDecimal("totaleScontoUnitario"), 
				rs.getBigDecimal("valoreScontoAifa"), 
				rs.getBigDecimal("valoreScontoSSN"), 
				rs.getBigDecimal("valoreScontoSSNExtra"), 
				reso,
				rs.getDate("dataReso"));				
	}
	
	public static ResiVenditeSSN mapResiVenditeSSN(ResultSet rs) throws SQLException{
		ResiVendite resoVendita = new ResiVendite();
		resoVendita.setIdResoVendita(new Integer(rs.getInt("IdResoVendita")));
		return new ResiVenditeSSN(
				new Integer(rs.getInt("idResoVenditaSSN")), 
				rs.getString("codiceFiscale"), 
				rs.getDate("dataResoVenditaSSN"), 
				rs.getString("esenzione"), 
				new Integer(rs.getInt("numreg")), 
				rs.getBigDecimal("quotaAssistito"), 
				rs.getBigDecimal("quotaRicetta"), 
				rs.getBigDecimal("totaleIva"), 
				new Integer(rs.getInt("totalePezziResi")), 
				rs.getBigDecimal("totaleRicetta"), 
				rs.getBigDecimal("totaleScontoSSN"), 
				rs.getBigDecimal("valoreVenditaSSN"), 
				resoVendita);
	}
	
	public static Giacenze mapGiacenze(ResultSet rs) throws SQLException{
		return new Giacenze(
				rs.getInt("idGiacenza"), 
				rs.getBigDecimal("costoUltimoDeivato"), 
				rs.getDate("dataCostoUltimo"), 
				rs.getString("minsan"), 
				rs.getString("descrizione"), 
				rs.getInt("giacenza"), 
				rs.getInt("venditeAnnoInCorso"));
	}
}

