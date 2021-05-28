package Controller;

import Model.Appointments;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

/**
 * Controller for the appointmentRecordsAdd view
 */
public class appointmentRecordsAddController {
    //Used to set a new scene.
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
    private Label exceptionLabelTitle;
    @FXML
    private Label exceptionLabelDesc;
    @FXML
    private Label exceptionLabelLoc;
    @FXML
    private Label exceptionLabelType;
    @FXML
    private Label exceptionLabelCustID;
    @FXML
    private Label exceptionLabelCustIDSQL;
    @FXML
    private Label exceptionLabelNullInput;
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

    /**
     * Returns the user to the main menu.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException, SQLException {
        mainMenuController.returnToMain(event);
    }

    /**
     * Saves inputted data as an appointment in the database. Uses labels and alerts to let the user know if incorrect
     * input is detected.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSaveAppt(ActionEvent event) throws IOException {
        //Set exception labels as empty when add appointment is clicked
        //That way the correct exceptions will be shown after each attempt at adding an appointment with incorrect data.
        exceptionLabelTitle.setText("");
        exceptionLabelDesc.setText("");
        exceptionLabelLoc.setText("");
        exceptionLabelType.setText("");
        exceptionLabelCustID.setText("");
        exceptionLabelNullInput.setText("");

        //Collection data from entered information.
        String newApptTitle = addApptTitleField.getText();
        String newApptDesc = addApptDescField.getText();
        String newApptLoc = addApptLocField.getText();
        String newApptType = addApptTypeField.getText();
        String newApptEndDate = ((TextField)addApptEndsDatepicker.getEditor()).getText();
        String newApptStartDate = ((TextField)addApptStartsDatepicker.getEditor()).getText();
        String newApptLastUpdateBy = DBConnection.returnUsername();
        String newApptCreatedBy = DBConnection.returnUsername();


        // Following ids are initialized to 0, however 0 will never be added into the database.
        int newApptCustID = 0;
        int contactID = 0;

        // Exception = false, means there is nothing wrong with the input.
        boolean exception = false;

        if(addCustomerIDField.getText().equals("")) {
            exceptionLabelCustID.setText("Appointment customer ID can't be null");
            exception = true;
        }
        else {
            try {
                newApptCustID = Integer.parseInt(addCustomerIDField.getText());
            }
            catch (NumberFormatException e) {
                exceptionLabelCustID.setText("Must enter integer associated with a customer for customer id");
            }
        }

        if(contactCombo.getSelectionModel().getSelectedItem() == null) {
            exceptionLabelNullInput.setText("Contact can't be empty");
            exception = true;
        }
        else{
            contactID = Contacts.returnContactID(contactCombo.getSelectionModel().getSelectedItem());
        }

        //Exceptions
        if(newApptTitle.equals(null) || newApptTitle.equals("")) {
            exceptionLabelTitle.setText("Appointment title can't be empty");
            exception = true;
        }
        if(newApptDesc.equals(null) || newApptDesc.equals("")) {
            exceptionLabelDesc.setText("Appointment description can't be empty");
            exception = true;
        }
        if(newApptLoc.equals(null) || newApptLoc.equals("")) {
            exceptionLabelLoc.setText("Appointment location can't be empty");
            exception = true;
        }
        if(newApptType.equals(null) || newApptType.equals("")) {
            exceptionLabelType.setText("Appointment type can't be empty");
            exception = true;
        }

        //Values will be passed into sql statement for date time.
        //Only continues if there are no exceptions up until this point.
        if(exception == false) {
            try {
                //Collects data from the start time hour combo box.
                int startHourCombo;
                String startHourString;
                String minInComboStart = startsMinuteCombo.getValue().toString();

                //Collects data from the end time hour combo box.
                int endHourCombo;
                String endsHourComboString;
                String minInComboEnd = endsMinuteCombo.getValue().toString();

                //Following if, else if branches gathers values from the start time combo boxes.
                if (startsAMPMCombo.getSelectionModel().getSelectedItem() == "PM" && !(startsHourCombo.getValue().toString().equals("12"))) {
                    startHourCombo = Integer.parseInt(startsHourCombo.getValue().toString()) + 12;
                    startHourString = String.valueOf(startHourCombo);
                }
                //Does not add 12 to 12 pm because 12 pm is not 24:00 but rather just 12 pm.
                else if(startsAMPMCombo.getSelectionModel().getSelectedItem() == "PM" && startsHourCombo.getValue().toString().equals("12")) {
                    startHourCombo = Integer.parseInt(startsHourCombo.getValue().toString());
                    startHourString = String.valueOf(startHourCombo);
                }
                //Does not allow user to enter 12 am into database.
                else if(startsAMPMCombo.getSelectionModel().getSelectedItem() == "AM" && startsHourCombo.getValue().toString().equals("12")) {
                    startHourCombo = Integer.parseInt(startsHourCombo.getValue().toString() + 12);
                    startHourString = String.valueOf(startHourCombo);

                    exception = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialogue");
                    alert.setContentText("Appointment is set to start before business hours!");
                    alert.showAndWait();
                }
                else {
                    startHourCombo = Integer.parseInt(startsHourCombo.getValue().toString());
                    if (startHourCombo < 10) {
                        startHourString = "0" + String.valueOf(startHourCombo);
                    } else {
                        startHourString = String.valueOf(startHourCombo);
                    }
                }

                //Following if, else if branches gathers values from the end time combo boxes.
                if (endsAMPMCombo.getSelectionModel().getSelectedItem() == "PM" && !(endsHourCombo.getValue().toString().equals("12"))) {
                    endHourCombo = Integer.parseInt(endsHourCombo.getValue().toString()) + 12;
                    endsHourComboString = String.valueOf(endHourCombo);

                }
                else if(endsAMPMCombo.getSelectionModel().getSelectedItem() == "PM" && endsHourCombo.getValue().toString().equals("12")) {
                    endHourCombo = Integer.parseInt(endsHourCombo.getValue().toString());
                    endsHourComboString = String.valueOf(endHourCombo);
                }
                //Does not allow user to enter 12 am into database.
                else if(endsAMPMCombo.getSelectionModel().getSelectedItem() == "AM" && endsHourCombo.getValue().toString().equals("12")) {
                    endHourCombo = Integer.parseInt(endsHourCombo.getValue().toString() + 12);
                    endsHourComboString = String.valueOf(endHourCombo);

                    exception = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialogue");
                    alert.setContentText("Appointment is set to end after business hours!");
                    alert.showAndWait();
                }
                else {
                    endHourCombo = Integer.parseInt(endsHourCombo.getValue().toString());
                    if (endHourCombo < 10) {
                        endsHourComboString = "0" + String.valueOf(endHourCombo);
                    } else {
                        endsHourComboString = String.valueOf(endHourCombo);
                    }
                }

                //Following code works with date and times to add the appointment within the correct time period of business hours
                //Created strings of the dates and times from the form that are to be converted into local date times.
                String startsDateTimeStr = newApptStartDate + " " + startHourString + "" + minInComboStart;
                String endsDateTimeStr = newApptEndDate + " " + endsHourComboString + "" + minInComboEnd;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                //Convert strings into LocalDateTime variables
                LocalDateTime startsDateTime = LocalDateTime.parse(startsDateTimeStr, formatter);
                LocalDateTime endsDateTime = LocalDateTime.parse(endsDateTimeStr, formatter);

                //Confirm that the appointment times do not correlate with the customer's other appointments
                Boolean overlap = Appointments.appointmentOverlap(startsDateTime, endsDateTime, newApptCustID);
                if (overlap == true) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialogue");
                    alert.setContentText("This customer has another appointment scheduled during this time!");
                    alert.showAndWait();
                }

                if (overlap == false && exception == false) {

                    //Zone ids
                    ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
                    ZoneId utcZoneID = ZoneId.of(TimeZone.getTimeZone("UTC").getID());
                    ZoneId estZoneID = ZoneId.of(TimeZone.getTimeZone("America/New_York").getID());

                    //Appt times in local zoneid
                    ZonedDateTime localStartZDT = ZonedDateTime.of(startsDateTime, localZoneId);
                    ZonedDateTime localEndZDT = ZonedDateTime.of(endsDateTime, localZoneId);

                    //Appointment times in EST!!
                    ZonedDateTime localDateTimeStartEST = localStartZDT.withZoneSameInstant(estZoneID);
                    ZonedDateTime localDateTimeEndEST = localEndZDT.withZoneSameInstant(estZoneID);
                    System.out.println(localDateTimeStartEST);
                    System.out.println(localDateTimeEndEST);

                    //Business hours in est
                    LocalDate businessOpensDate = LocalDate.now();
                    LocalDate businessClosesDate = LocalDate.now();
                    LocalTime businessOpensEST = LocalTime.of(8, 00);
                    LocalTime businessClosesEST = LocalTime.of(22, 00);
                    ZonedDateTime businessOpensESTZDT = ZonedDateTime.of(businessOpensDate, businessOpensEST, estZoneID);
                    ZonedDateTime businessClosesESTZDT = ZonedDateTime.of(businessClosesDate, businessClosesEST, estZoneID);
                    ZonedDateTime businessOpensUTCZDT = businessOpensESTZDT.withZoneSameInstant(utcZoneID);
                    ZonedDateTime businessClosesUTCZDT = businessClosesESTZDT.withZoneSameInstant(utcZoneID);

                    //Comparisons must be in localTime
                    LocalTime opensComparison = LocalTime.of(localDateTimeStartEST.getHour(), localDateTimeStartEST.getMinute());
                    LocalTime closesComparison = LocalTime.of(localDateTimeEndEST.getHour(), localDateTimeEndEST.getMinute());
                    LocalTime businessOpensComparion = LocalTime.of(businessOpensESTZDT.getHour(), businessOpensESTZDT.getMinute());
                    LocalTime businessClosesComparison = LocalTime.of(businessClosesESTZDT.getHour(), businessClosesESTZDT.getMinute());

                    //Makes sure that appointments aren't scheduled on weekdays
                    if ((localDateTimeStartEST.getDayOfWeek() == DayOfWeek.SATURDAY
                            || localDateTimeStartEST.getDayOfWeek() == DayOfWeek.SUNDAY)
                            || (localDateTimeEndEST.getDayOfWeek() == DayOfWeek.SATURDAY
                            || localDateTimeEndEST.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialogue");
                        alert.setContentText("Appointments can't be scheduled on the weekend!");
                        alert.showAndWait();
                    }

                    //Maintains correct times
                    if ((opensComparison.isAfter(businessOpensComparion) ||
                            opensComparison.equals(businessOpensComparion))
                            && (closesComparison.isBefore(businessClosesComparison) ||
                            closesComparison.equals(businessClosesComparison)) && exception == false) {
                        Connection conn = DBConnection.getConnection();
                        String sqlInsert = "INSERT INTO appointments(Title,Description,Location,Type," +
                                "Start,End,Customer_ID,Contact_ID, Last_Updated_By, Created_By, User_ID) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?)";
                        try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                            ps.setString(1, newApptTitle);
                            ps.setString(2, newApptDesc);
                            ps.setString(3, newApptLoc);
                            ps.setString(4, newApptType);
                            ps.setTimestamp(5, Timestamp.valueOf(startsDateTime));
                            ps.setTimestamp(6, Timestamp.valueOf(endsDateTime));
                            ps.setInt(7, newApptCustID);
                            ps.setInt(8, contactID);
                            ps.setString(9, newApptLastUpdateBy);
                            ps.setString(10, newApptCreatedBy);
                            //User id is set to 1 because a real user id is not currently being used besides "test", which is
                            //not an int
                            ps.setInt(11, 1);

                            ps.executeUpdate();
                            mainMenuController.returnToMain(event);
                        } catch (SQLException e) {
                            exceptionLabelCustIDSQL.setText("Invalid Customer ID!");
                            System.out.println(e.getMessage());
                            System.out.println(e.getSQLState());
                        }
                        //The last three else if statements show alerts if times entered are not during business hours.
                    } else if (opensComparison.isBefore(businessOpensComparion) && closesComparison.isAfter(businessClosesComparison)
                            && exception == false) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialogue");
                        alert.setContentText("Business hours are between 8:00 AM and 10:00 PM EST!");
                        alert.showAndWait();
                    } else if (opensComparison.isBefore(businessOpensComparion) && exception == false) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialogue");
                        alert.setContentText("Appointment is set to start before business hours!");
                        alert.showAndWait();
                    } else if (closesComparison.isAfter(businessClosesComparison) && exception == false) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialogue");
                        alert.setContentText("Appointment is set to end after business hours!");
                        alert.showAndWait();
                    }
                }
            }
            catch(NullPointerException e) {
                exceptionLabelNullInput.setText("Null input detected or incorrect data inputted!");
                System.out.println(e.getMessage());
            }
            catch (DateTimeParseException e) {
                exceptionLabelNullInput.setText("Incorrect date input");
            }
        }
    }

    /**
     * Initializes the combo boxes in the view and also sets how the DatePicker will write the selected date.
     */
    @FXML
    void initialize() {
        startsHourCombo.setItems(ComboBox.getAppointmentHours());
        startsMinuteCombo.setItems(ComboBox.getAppointmentMinutes());
        startsAMPMCombo.setItems(ComboBox.getAppointmentAMPM());
        endsHourCombo.setItems(ComboBox.getAppointmentHours());
        endsMinuteCombo.setItems(ComboBox.getAppointmentMinutes());
        endsAMPMCombo.setItems(ComboBox.getAppointmentAMPM());
        contactCombo.setItems(Contacts.initializeContacts());

        //How the date needs to be formatted to enter the database
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
