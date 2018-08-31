package com.klugesoftware.farmamanager.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class GetDBFFileName {

    private String annoCorrente = "";
    private String annoPrecedente = "";
    private final String PROPERIES_FILE_NAME = "./resources/config/config.properties";
    private final Properties properties = new Properties();


    public GetDBFFileName(){
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        int annoCorrenteInt = myCal.get(Calendar.YEAR);
        myCal.set(Calendar.YEAR,annoCorrenteInt-1);
        int annoPrecedenteInt = myCal.get(Calendar.YEAR);
        annoCorrente = Integer.toString(annoCorrenteInt).substring(2);
        annoPrecedente = Integer.toString(annoPrecedenteInt).substring(2);

        try {
            InputStream in = new FileInputStream(PROPERIES_FILE_NAME);
            properties.load(in);
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }

    public String getMovimentiFileNameAnnoCorrente(){
        return properties.getProperty("dbfTabellaMovName")+annoCorrente+"."+properties.getProperty("dbfTabellaEstensione");
    }

    public String getMovimentiFileNameAnnoPrecedente(){
        return properties.getProperty("dbfTabellaMovName")+annoPrecedente+"."+properties.getProperty("dbfTabellaEstensione");
    }

    public String getMovimentiFileName(Date dateFrom){
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(dateFrom);
        int anno = myCal.get(Calendar.YEAR);
        String annofromDate = Integer.toString(anno).substring(2);
        return properties.getProperty("dbfTabellaMovName")+annofromDate+"."+properties.getProperty("dbfTabellaEstensione");
    }

    public String getGiacenzeFileNameAnnoCorrente(){
        return properties.getProperty("dbfTabellaGiacName")+annoCorrente+"."+properties.getProperty("dbfTabellaEstensione");
    }

    public static void main(String[] args){
        // test class GetDBFFileName
        GetDBFFileName gfn = new GetDBFFileName();
        System.out.println(gfn.getMovimentiFileNameAnnoCorrente());
        System.out.println(gfn.getMovimentiFileNameAnnoPrecedente());
        System.out.println(gfn.getMovimentiFileName(DateUtility.converteGUIStringDDMMYYYYToDate("01/02/2021")));
        System.out.println(gfn.getGiacenzeFileNameAnnoCorrente());
    }
}
