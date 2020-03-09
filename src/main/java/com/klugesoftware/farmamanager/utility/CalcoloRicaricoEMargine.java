package com.klugesoftware.farmamanager.utility;

import com.klugesoftware.farmamanager.model.CustomRoundingAndScaling;
import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ProdottiVenditaSSN;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CalcoloRicaricoEMargine {

    /**
     *
     * @param prodotto
     *
     * 	Il metodo calcola il margine e ricarico nei vari casi con profittoUnitario,
     * 	costoNetto o prezzo Vendita con valori <0 oppure =0 oppure >0
     */
    public static void calcolo(Object prodotto){
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

    public static Map<String,Double> calcoloMargineERicarico(double profitto, double prezzoVenditaNetto, double costoNetto){
        Map<String,Double> ret = new HashMap<>();
        double ricarico = 0;
        double margine = 0;
        if(profitto != 0){

            if(profitto > 0 && costoNetto == 0 && prezzoVenditaNetto > 0){
                margine = (profitto/Math.abs(prezzoVenditaNetto))*100;
                ricarico = margine;
            }else {
                if (profitto < 0 && costoNetto > 0 && prezzoVenditaNetto == 0) {
                    ricarico = (profitto / Math.abs(costoNetto)) * 100;
                    margine = ricarico;
                }else{
                    if(profitto < 0 && costoNetto == 0 && prezzoVenditaNetto < 0){
                        margine = (profitto/Math.abs(prezzoVenditaNetto))*100;
                        ricarico = margine;
                    }else {
                        margine = (profitto/Math.abs(prezzoVenditaNetto))*100;
                        ricarico = (profitto/Math.abs(costoNetto))*100;
                    }
                }
            }
        }
        ret.put("ricarico",ricarico);
        ret.put("margine",margine);
        return  ret;
    }
}
