package Controller;

import Model.Appointments;
import Model.Date_Time;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void sendAppt(Appointments appointmentToModify) {
        addApptIDField.setText(String.valueOf(appointmentToModify.getAppointment_ID()));
        addApptTitleField.setText(String.valueOf(appointmentToModify.getTitle()));
        addApptDescField.setText(String.valueOf(appointmentToModify.getDescription()));
        addApptLocField.setText(String.valueOf(appointmentToModify.getLocation()));
        addApptContactField.setText(String.valueOf(appointmentToModify.getContact_ID()));
        addApptEndsDatepicker.setValue(appointmentToModify.getEnd().toLocalDate());
        addApptStartsDatepicker.setValue(appointmentToModify.getStart().toLocalDate());
        addApptTypeField.setText(String.valueOf(appointmentToModify.getType()));
        addCustomerIDField.setText(String.valueOf(appointmentToModify.getCustomer_ID()));

        //Sets minute combo box for start.
        if(appointmentToModify.getStart().toLocalTime().getMinute() == 0) {
            startsMinuteCombo.getSelectionModel().select( Model.ComboBox.getAppointmentMinutes().get(0) );
            System.out.println("" + Model.ComboBox.getAppointmentMinutes().get(0) + "");
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
            System.out.println("" + Model.ComboBox.getAppointmentMinutes().get(0) + "");
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
    void onActionModifyAppt(ActionEvent event) {

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
    }
}
