package Controller;

import Model.ComboBox;
import Utilities.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private TextField addApptContactField;
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
        //FIXME use combobox for contact field instead;
        String newApptTitle = addApptTitleField.getText();
        String newApptDesc = addApptDescField.getText();
        String newApptLoc = addApptLocField.getText();
        int newApptContact = Integer.parseInt(addApptContactField.getText());
        String newApptType = addApptTypeField.getText();
        int newCustID = Integer.parseInt(addCustomerIDField.getText());
        String newApptEndDate = ((TextField)addApptEndsDatepicker.getEditor()).getText();
        String newApptStartDate = ((TextField)addApptStartsDatepicker.getEditor()).getText();

        //FIXME alert user if incorrect data is entered
        //FIXME Data is being entered in UTC and displaying incorrectly
        //FIXME Convert time entered into UTC and then add.
        //Values will be passed into sql statement for date time.
        int startHourCombo;
        String startHourString;
        String minInComboStart = startsMinuteCombo.getValue().toString();


        int endHourCombo;
        String endsHourComboString;
        String minInComboEnd = startsMinuteCombo.getValue().toString();


        String seconds = "00";

        if(startsAMPMCombo.getSelectionModel().getSelectedItem() == "PM") {
            startHourCombo = Integer.parseInt(startsHourCombo.getValue().toString()) + 12;
            startHourString = String.valueOf(startHourCombo);
            System.out.println("testeres");
        }
        else{
            startHourCombo = Integer.parseInt(startsHourCombo.getValue().toString());
            startHourString = String.valueOf(startHourCombo);
        }

        if(endsAMPMCombo.getSelectionModel().getSelectedItem() == "PM") {
            endHourCombo = Integer.parseInt(endsHourCombo.getValue().toString()) + 12;
            endsHourComboString = String.valueOf(endHourCombo);
            System.out.println("testee");
        }
        else{
            endHourCombo = Integer.parseInt(endsHourCombo.getValue().toString());
            endsHourComboString = String.valueOf(endHourCombo);
        }



        Connection conn = DBConnection.getConnection();
        String sqlInsert = "INSERT INTO appointments(Title,Description,Location,Type," +
                "Start,End,Customer_ID,Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ? , ?)";
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1, newApptTitle);
            ps.setString(2, newApptDesc);
            ps.setString(3, newApptLoc);
            ps.setString(4, newApptType);
            ps.setString(5, newApptStartDate + " " + startHourString + ":" + minInComboStart +":" +
                    seconds);
            ps.setString(6, newApptEndDate + " " + endsHourComboString + ":" + minInComboEnd + ":" +
                    seconds);
            ps.setInt(7, newCustID);
            ps.setInt(8, newApptContact);

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

        //Stackoverflow
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
