package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;



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
    private ComboBox<?> startsComboBox;

    @FXML
    private ComboBox<?> endsComboBox;

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
        mainMenuController.returnToMain(event);
    }
}
