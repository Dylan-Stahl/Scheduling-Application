package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


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
    private ComboBox<?> startsComboBox;

    @FXML
    private ComboBox<?> endsComboBox;

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
        mainMenuController.returnToMain(event);
    }
}
