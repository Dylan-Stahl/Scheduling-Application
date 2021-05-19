package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class ComboBox {
    private final static ObservableList<String> appointmentHours = FXCollections.observableArrayList("1","2","3",
            "4", "5","6","7","8","9","10","11","12");
    private final static ObservableList<String> appointmentMinutes = FXCollections.observableArrayList(":00", ":15",
            ":30", ":45");
    private final static ObservableList<String> appointmentAMPM = FXCollections.observableArrayList("AM","PM");


    public final static ObservableList<String> getAppointmentAMPM() {
        return appointmentAMPM;
    }

    public final static ObservableList<String> getAppointmentMinutes() {
        return appointmentMinutes;
    }

    public final static ObservableList<String> getAppointmentTimes() {
        return appointmentHours;
    }
}
