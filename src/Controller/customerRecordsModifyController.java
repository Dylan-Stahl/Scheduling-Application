package Controller;

import Model.Country;
import Model.Customers;
import Model.Division;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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
    private ComboBox<Country> addCustomerCountryCombo;
    @FXML
    private ComboBox<Division> addCustomerDivCombo;

    public void sendCustomer(Customers customerToModify) {
        //Sets fields for the selected customer
        addCustomerIDField.setText(String.valueOf(customerToModify.getCustomer_ID()));
        addCustomerNameField.setText(String.valueOf(customerToModify.getCustomer_Name()));
        addCustomerAddressField.setText(String.valueOf(customerToModify.getAddress()));
        addPostalCodeField.setText(String.valueOf(customerToModify.getPostal_Code()));
        addCustomerPhoneField.setText(String.valueOf(customerToModify.getPhone()));

        //Sets combo box fields
        addCustomerCountryCombo.setItems(Country.initializeAllCountries());
        addCustomerCountryCombo.getSelectionModel().select(customerToModify.getCountryObj());
        addCustomerDivCombo.setItems(Division.initializeAllDivisions());//Divisons);
        addCustomerDivCombo.getSelectionModel().select(customerToModify.getDivisionObj());

    }

    @FXML
    void onActionAddCustomer(ActionEvent event) {

    }

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
        mainMenuController.returnToMain(event);
    }
}
