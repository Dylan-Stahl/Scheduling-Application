package Controller;

import Model.ComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


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
    void initialize() {
        startsHourCombo.setItems(ComboBox.getAppointmentTimes());
        startsMinuteCombo.setItems(ComboBox.getAppointmentMinutes());
        startsAMPMCombo.setItems(ComboBox.getAppointmentAMPM());
        endsHourCombo.setItems(ComboBox.getAppointmentTimes());
        endsMinuteCombo.setItems(ComboBox.getAppointmentMinutes());
        endsAMPMCombo.setItems(ComboBox.getAppointmentAMPM());
    }
}
