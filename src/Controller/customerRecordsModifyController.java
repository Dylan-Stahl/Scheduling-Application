package Controller;

import Model.Customers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class customerRecordsModifyController {

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

    public void sendCustomer(Customers customerToModify) {
        addCustomerIDField.setText(String.valueOf(customerToModify.getCustomer_ID()));
        addCustomerNameField.setText(String.valueOf(customerToModify.getCustomer_Name()));
        addCustomerAddressField.setText(String.valueOf(customerToModify.getAddress()));
        addPostalCodeField.setText(String.valueOf(customerToModify.getPostal_Code()));
        addCustomerPhoneField.setText(String.valueOf(customerToModify.getPhone()));
        //FIXME work on combo boxes, watch webinar
        //addCustomerCountryCombo.set
        //addCustomerDivCombo
    }

    @FXML
    void onActionAddCustomer(ActionEvent event) {

    }

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
        mainMenuController.returnToMain(event);
    }
}
