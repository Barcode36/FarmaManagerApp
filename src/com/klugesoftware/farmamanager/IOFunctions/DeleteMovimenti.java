package com.klugesoftware.farmamanager.IOFunctions;

import com.klugesoftware.farmamanager.db.VenditeDAOManager;
import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Date;


/**
 * DeleteMovimenti: cancella tutti i movimenti relative alle vendite Libere e SSN, inoltre Resi, TotaliMensili e TotaliGiornalieri;
 * per convezione si assume che la cancellazione è fatta ad intervalli mensili.
 */
public class DeleteMovimenti  extends Task {

    private final Logger logger = LogManager.getLogger(DeleteMovimenti.class.getName());
    private Date dateFrom;
    private Date dateTo;

    public DeleteMovimenti(Date dateFrom, Date dateTo){
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Object call(){

        updateMessage("Cancellazione movimenti dalla data: "+(DateUtility.inizioMese(dateFrom))+" alla data: "+DateUtility.fineMese(dateTo));
        logger.info("Cancellazione movimenti dalla data: "+(DateUtility.inizioMese(dateFrom))+" alla data: "+DateUtility.fineMese(dateTo));
        //cancello le vendite ad intervalli mensili: cioè dal primo all'ultimo giorno del mese oppure dei mesi

        //TODO: dividere la delete delle vendite dai resi
        VenditeDAOManager.deleteVenditeBetweenDate(DateUtility.converteGUIStringDDMMYYYYToDate(DateUtility.inizioMese(dateFrom)),DateUtility.converteGUIStringDDMMYYYYToDate(DateUtility.fineMese(dateTo)));
        return null;
    }
}
