package Controller;

import Model.ComboBox;
import Model.Contacts;
import Utilities.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class appointmentRecordsAddController {

    Stage stage;
    Parent scene;

    @FXML
    private TextField addApptIDField;
    @FXML
    private TextField addApptTitleField;
    @FXML
    private TextField addApptDescField;
    @FXML
    private TextField addApptLocField;
    @FXML
    private javafx.scene.control.ComboBox<Contacts> contactCombo;
    @FXML
    private DatePicker addApptEndsDatepicker;
    @FXML
    private DatePicker addApptStartsDatepicker;
    @FXML
    private TextField addApptTypeField;
    @FXML
    private TextField addCustomerIDField;
    @FXML
    private javafx.scene.control.ComboBox startsHourCombo;
    @FXML
    private javafx.scene.control.ComboBox startsMinuteCombo;
    @FXML
    private javafx.scene.control.ComboBox startsAMPMCombo;
    @FXML
    private javafx.scene.control.ComboBox endsHourCombo;
    @FXML
    private javafx.scene.control.ComboBox endsMinuteCombo;
    @FXML
    private javafx.scene.control.ComboBox endsAMPMCombo;

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
        mainMenuController.returnToMain(event);
    }

    @FXML
    void onActionSaveAppt(ActionEvent event) throws IOException {
        //Collection data from entered information.
        String newApptTitle = addApptTitleField.getText();
        String newApptDesc = addApptDescField.getText();
        String newApptLoc = addApptLocField.getText();
        String newApptType = addApptTypeField.getText();
        int newCustID = Integer.parseInt(addCustomerIDField.getText());
        String newApptEndDate = ((TextField)addApptEndsDatepicker.getEditor()).getText();
        String newApptStartDate = ((TextField)addApptStartsDatepicker.getEditor()).getText();
        int contactID = Contacts.returnContactID(contactCombo.getSelectionModel().getSelectedItem());

        //FIXME alert user if incorrect data is entered
        //Values will be passed into sql statement for date time.
        int startHourCombo;
        String startHourString;
        String minInComboStart = startsMinuteCombo.getValue().toString();

        int endHourCombo;
        String endsHourComboString;
        String minInComboEnd = endsMinuteCombo.getValue().toString();

        if(startsAMPMCombo.getSelectionModel().getSelectedItem() == "PM") {
            startHourCombo = Integer.parseInt(startsHourCombo.getValue().toString()) + 12;
            startHourString = String.valueOf(startHourCombo);
        }
        else{
            startHourCombo = Integer.parseInt(startsHourCombo.getValue().toString());
            if(startHourCombo < 10) {
                startHourString = "0" + String.valueOf(startHourCombo);
            }
            else {
                startHourString = String.valueOf(startHourCombo);
            }
        }

        if(endsAMPMCombo.getSelectionModel().getSelectedItem() == "PM") {
            endHourCombo = Integer.parseInt(endsHourCombo.getValue().toString()) + 12;
            endsHourComboString = String.valueOf(endHourCombo);

        }
        else{
            endHourCombo = Integer.parseInt(endsHourCombo.getValue().toString());
            if(endHourCombo < 10) {
                endsHourComboString = "0" + String.valueOf(endHourCombo);
            }
            else{
                endsHourComboString = String.valueOf(endHourCombo);
            }

        }

        //Created strings of the dates and times from the form that are to be converted into local date times.
        String startsDateTimeStr = newApptStartDate + " " + startHourString + "" + minInComboStart;
        String endsDateTimeStr = newApptEndDate + " " + endsHourComboString + "" + minInComboEnd;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        //DateTimes that will be converted into UTC time before being entered into database
        LocalDateTime startsDateTime = LocalDateTime.parse(startsDateTimeStr, formatter);
        LocalDateTime endsDateTime = LocalDateTime.parse(endsDateTimeStr, formatter);

        /* database automatically converts to correct time
        System.out.println(startsDateTime);

        //Convert localdate time of entered data into local zoneddatetime
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime startsDateTimeLocal = startsDateTime.atZone(zoneId);
        ZonedDateTime endsDateTimeLocal = endsDateTime.atZone(zoneId);

        System.out.println(startsDateTimeLocal);

        //Convert local zoneddatetime into UTC zoneddatetime
        ZonedDateTime startsDateTimeUTC = startsDateTimeLocal.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endsDateTimeUTC = endsDateTimeLocal.withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println(startsDateTimeUTC);

        //Convert utc zoneddate time into localdatetime

        LocalDateTime startsDateTimeUTCLCD = startsDateTimeUTC.toLocalDateTime();
        LocalDateTime endsDateTimeUTCLCD = endsDateTimeUTC.toLocalDateTime();
        System.out.println(startsDateTimeUTCLCD);
        System.out.println(Timestamp.valueOf(startsDateTimeUTCLCD)); */

        Connection conn = DBConnection.getConnection();
        String sqlInsert = "INSERT INTO appointments(Title,Description,Location,Type," +
                "Start,End,Customer_ID,Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ? , ?)";
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1, newApptTitle);
            ps.setString(2, newApptDesc);
            ps.setString(3, newApptLoc);
            ps.setString(4, newApptType);
            ps.setTimestamp(5, Timestamp.valueOf(startsDateTime));
            ps.setTimestamp(6, Timestamp.valueOf(endsDateTime));
            ps.setInt(7, newCustID);
            ps.setInt(8, contactID);

            ps.executeUpdate();
            mainMenuController.returnToMain(event);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
    }

    @FXML
    void initialize() {
        startsHourCombo.setItems(ComboBox.getAppointmentTimes());
        startsMinuteCombo.setItems(ComboBox.getAppointmentMinutes());
        startsAMPMCombo.setItems(ComboBox.getAppointmentAMPM());
        endsHourCombo.setItems(ComboBox.getAppointmentTimes());
        endsMinuteCombo.setItems(ComboBox.getAppointmentMinutes());
        endsAMPMCombo.setItems(ComboBox.getAppointmentAMPM());
        contactCombo.setItems(Contacts.initializeContacts());

        String pattern = "yyyy-MM-dd";
        addApptStartsDatepicker.setPromptText(pattern.toLowerCase());

        addApptStartsDatepicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        addApptEndsDatepicker.setPromptText(pattern.toLowerCase());
        addApptEndsDatepicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }
}
