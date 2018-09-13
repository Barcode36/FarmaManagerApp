package com.klugesoftware.farmamanager.IOFunctions;

import com.klugesoftware.farmamanager.db.GiacenzeDAOManager;
import com.klugesoftware.farmamanager.db.MovimentiDAOManager;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InizializzaArchivi extends Task {

    private final Logger logger = LogManager.getLogger(InizializzaArchivi.class.getName());

    public Object call(){
        updateMessage("Cancellazione Giacenze ");
        logger.info("Cancellazione Giacenze");
        //truncate Giacenze
        GiacenzeDAOManager.deleteTable();

        updateMessage("Cancellazione Movimenti ");
        logger.info("Cancellazione Movimenti");

        //truncate movimenti
        MovimentiDAOManager.truncateMovimenti();

        return null;
    }
}
