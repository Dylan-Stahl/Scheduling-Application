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



public class customerRecordsAddController {

    Stage stage;
    Parent scene;

    @FXML
    private TextField addCustomerIDField;

    @FXML
    private TextField addCustomerNameField;

    @FXML
    private TextField addCustomerAddressField;

    @FXML
    private TextField addPostalCodeField;

    @FXML
    private TextField addCustomerPhoneField;

    @FXML
    private ComboBox<?> addCustomerCountryCombo;

    @FXML
    private ComboBox<?> addCustomerDivCombo;

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
       mainMenuController.returnToMain(event);
    }



}
