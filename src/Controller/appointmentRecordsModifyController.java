package Controller;

import Model.Appointments;
import Model.Contacts;
import Model.Date_Time;
import Utilities.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class appointmentRecordsModifyController {

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

    public void sendAppt(Appointments appointmentToModify) {
        addApptIDField.setText(String.valueOf(appointmentToModify.getAppointment_ID()));
        addApptTitleField.setText(String.valueOf(appointmentToModify.getTitle()));
        addApptDescField.setText(String.valueOf(appointmentToModify.getDescription()));
        addApptLocField.setText(String.valueOf(appointmentToModify.getLocation()));
        contactCombo.setItems(Contacts.initializeContacts());
        contactCombo.getSelectionModel().select(Contacts.returnContact(appointmentToModify));
        addApptEndsDatepicker.setValue(appointmentToModify.getEnd().toLocalDate());
        addApptStartsDatepicker.setValue(appointmentToModify.getStart().toLocalDate());
        addApptTypeField.setText(String.valueOf(appointmentToModify.getType()));
        addCustomerIDField.setText(String.valueOf(appointmentToModify.getCustomer_ID()));

        //Sets minute combo box for start.
        if(appointmentToModify.getStart().toLocalTime().getMinute() == 0) {
            startsMinuteCombo.getSelectionModel().select( Model.ComboBox.getAppointmentMinutes().get(0));
        }
        else if (appointmentToModify.getStart().toLocalTime().getMinute() == 15) {
            startsMinuteCombo.getSelectionModel().select(Model.ComboBox.getAppointmentMinutes().get(1));
        }
        else if( appointmentToModify.getStart().toLocalTime().getMinute() == 30) {
            startsMinuteCombo.getSelectionModel().select(Model.ComboBox.getAppointmentMinutes().get(2));
        }
        else {
            startsMinuteCombo.getSelectionModel().select(Model.ComboBox.getAppointmentMinutes().get(3));
        }

        //Sets minute combo box for end
        if(appointmentToModify.getEnd().toLocalTime().getMinute() == 0) {
            endsMinuteCombo.getSelectionModel().select( Model.ComboBox.getAppointmentMinutes().get(0) );
        }
        else if (appointmentToModify.getStart().toLocalTime().getMinute() == 15) {
            endsMinuteCombo.getSelectionModel().select(Model.ComboBox.getAppointmentMinutes().get(1));
        }
        else if( appointmentToModify.getStart().toLocalTime().getMinute() == 30) {
            endsMinuteCombo.getSelectionModel().select(Model.ComboBox.getAppointmentMinutes().get(2));
        }
        else {
            endsMinuteCombo.getSelectionModel().select(Model.ComboBox.getAppointmentMinutes().get(3));
        }

       //Sets hour combo box for start
        if(appointmentToModify.getStart().toLocalTime().getHour() > 12) {
            startsAMPMCombo.getSelectionModel().select("PM");
            //startsHourCombo.getSelectionModel().select(appointmentToModify.getStart().toLocalTime().getHour() - 12);
            startsHourCombo.getSelectionModel().select(appointmentToModify.getStart().getHour() - 12 - 1);
            System.out.println(appointmentToModify.getStart().getHour() - 12);
        }
        else {
            startsAMPMCombo.getSelectionModel().select("AM");
            //startsHourCombo.getSelectionModel().select(appointmentToModify.getStart().toLocalTime().getHour());
            startsHourCombo.getSelectionModel().select(appointmentToModify.getStart().getHour() - 1);
        }

        //Sets hour combo box for end
        if(appointmentToModify.getEnd().toLocalTime().getHour() > 12) {
            endsAMPMCombo.getSelectionModel().select("PM");
            endsHourCombo.getSelectionModel().select(appointmentToModify.getEnd().toLocalTime().getHour() - 12 - 1);
        }
        else {
            endsAMPMCombo.getSelectionModel().select("AM");
            endsHourCombo.getSelectionModel().select(appointmentToModify.getEnd().toLocalTime().getHour() - 1);
        }

    }


    @FXML
    void onActionModifyAppt(ActionEvent event) throws IOException {
        String newApptTitle = addApptTitleField.getText();
        String newApptDesc = addApptDescField.getText();
        String newApptLoc = addApptLocField.getText();
        String newApptType = addApptTypeField.getText();
        int newCustID = Integer.parseInt(addCustomerIDField.getText());
        String newApptEndDate = ((TextField)addApptEndsDatepicker.getEditor()).getText();
        String newApptStartDate = ((TextField)addApptStartsDatepicker.getEditor()).getText();
        int contactID = Contacts.returnContactID(contactCombo.getSelectionModel().getSelectedItem());
        int newApptID = Integer.parseInt(addApptIDField.getText());


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


        String startsDateTimeStr = newApptStartDate + " " + startHourString + "" + minInComboStart;
        String endsDateTimeStr = newApptEndDate + " " + endsHourComboString + "" + minInComboEnd;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        //DateTimes that will be converted into UTC time before being entered into database
        LocalDateTime startsDateTime = LocalDateTime.parse(startsDateTimeStr, formatter);
        LocalDateTime endsDateTime = LocalDateTime.parse(endsDateTimeStr, formatter);


        Connection conn = DBConnection.getConnection();
        String sqlUpdate = "UPDATE appointments " +
                "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, " +
                "Contact_ID = ? " +
                "WHERE Appointment_ID = ?;";
        try(PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
            ps.setString(1, newApptTitle);
            ps.setString(2, newApptDesc);
            ps.setString(3, newApptLoc);
            ps.setString(4, newApptType);
            ps.setTimestamp(5, Timestamp.valueOf(startsDateTime));
            ps.setTimestamp(6, Timestamp.valueOf(endsDateTime));
            ps.setInt(7, newCustID);
            ps.setInt(8, contactID);
            ps.setInt(9, newApptID);


            ps.executeUpdate();
            System.out.println("Updated Count: " + ps.getUpdateCount());
            mainMenuController.returnToMain(event);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
    }

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
        mainMenuController.returnToMain(event);
    }

    @FXML
    void initialize() {
        startsHourCombo.setItems(Model.ComboBox.getAppointmentTimes());
        startsMinuteCombo.setItems(Model.ComboBox.getAppointmentMinutes());
        startsAMPMCombo.setItems(Model.ComboBox.getAppointmentAMPM());
        endsHourCombo.setItems(Model.ComboBox.getAppointmentTimes());
        endsMinuteCombo.setItems(Model.ComboBox.getAppointmentMinutes());
        endsAMPMCombo.setItems(Model.ComboBox.getAppointmentAMPM());

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
