package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ComboBox class is used to set the items in the combo boxes for the appointmentRecordsAdd and appointmentRecordsModify.
 */
public final class ComboBox {
    private final static ObservableList<String> appointmentHours = FXCollections.observableArrayList("1","2","3",
            "4", "5","6","7","8","9","10","11","12");
    private final static ObservableList<String> appointmentMinutes = FXCollections.observableArrayList(":00", ":15",
            ":30", ":45");
    private final static ObservableList<String> appointmentAMPM = FXCollections.observableArrayList("AM","PM");

    /**
     * Used to initialize the appointmentRecordsAdd and appointmentRecordsModify AM and PM box combos.
     * @return
     */
    public final static ObservableList<String> getAppointmentAMPM() {
        return appointmentAMPM;
    }

    /**
     * Used to initialize the appointmentRecordsAdd and appointmentRecordsModify minute box combos.
     * @return
     */
    public final static ObservableList<String> getAppointmentMinutes() {
        return appointmentMinutes;
    }

    /**
     * Used to initialize the appointmentRecordsAdd and appointmentRecordsModify hour box combos.
     * @return
     */
    public final static ObservableList<String> getAppointmentHours() {
        return appointmentHours;
    }


}
