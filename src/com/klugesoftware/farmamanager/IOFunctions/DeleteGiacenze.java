package com.klugesoftware.farmamanager.IOFunctions;

import com.klugesoftware.farmamanager.db.GiacenzeDAOManager;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteGiacenze extends Task {

    private final Logger logger = LogManager.getLogger(DeleteGiacenze.class.getName());

    public Object call(){

        updateMessage("Cancellazione della tabella Giacenze");
        logger.info("Cancellazione della tabella Giacenze");
        GiacenzeDAOManager.deleteTable();

        return null;
    }
}
