package com.klugesoftware.farmamanager.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Date;
import java.util.Optional;

public class AlertIntervalloDate {

    public boolean getAlertIntervalloDate(Date dateFrom,Date dateTo){
        if(dateTo.before(dateFrom))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Intervallo Date");
            alert.setHeaderText("Intervallo date incongruente!");
            alert.setContentText("La seconda data deve essere successiva alla prima ");
            Optional<ButtonType> result = alert.showAndWait();
            return true;
        }
        else
            return false;
    }
}
