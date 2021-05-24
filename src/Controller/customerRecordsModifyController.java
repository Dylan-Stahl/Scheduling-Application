package Controller;

import Model.Country;
import Model.Customers;
import Model.Division;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        //addCustomerDivCombo.setItems(Division.initializeAllDivisions());//Divisons);
        addCustomerDivCombo.getSelectionModel().select(customerToModify.getDivisionObj());
        Division.removeDivisionsSortedByCountry();
        try {
            String newCustomerCountry = addCustomerCountryCombo.getValue().toString();
            int newCustomerCountryID = Country.returnCountryID(newCustomerCountry);
            ObservableList<Division> divisonToSet = FXCollections.observableArrayList(Division.initializeDivisionWithSetCountry(newCustomerCountryID));

            addCustomerDivCombo.setItems(divisonToSet);
        }
        catch (NullPointerException e) {}
    }

    @FXML
    void onActionSortFirstLevelDivision(ActionEvent event) {
        Division.removeDivisionsSortedByCountry();
        try {
            String newCustomerCountry = addCustomerCountryCombo.getValue().toString();
            int newCustomerCountryID = Country.returnCountryID(newCustomerCountry);
            ObservableList<Division> divisonToSet = FXCollections.observableArrayList(Division.initializeDivisionWithSetCountry(newCustomerCountryID));

            addCustomerDivCombo.setItems(divisonToSet);
        }
        catch (NullPointerException e) {}
    }

    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException{
        int newCustomerID = Integer.parseInt(addCustomerIDField.getText());
        String newCustomerName = addCustomerNameField.getText();
        String newCustomerAddress = addCustomerAddressField.getText();
        String newCustomerPostal = addPostalCodeField.getText();
        String newCustomerPhone = addCustomerPhoneField.getText();
        String newCustomerCountry = addCustomerCountryCombo.getValue().toString();
        String newCustomerDivision = addCustomerDivCombo.getValue().toString();

        //call division, search table with division name, and get id
        int divisionID = Division.getDivisionID(newCustomerDivision);

        //Determine user updating data
        String user = DBConnection.returnUsername();


        //Perform update
        Connection conn = DBConnection.getConnection();
        String sqlUpdate = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Division_ID = ?, Last_Update = ?, Last_Updated_By = ? WHERE Customer_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
            ps.setString(1, newCustomerName);
            ps.setString(2,newCustomerAddress);
            ps.setString(3, newCustomerPostal);
            ps.setString(4, newCustomerPhone);
            ps.setInt(5, divisionID);
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, user);
            ps.setInt(8, newCustomerID);

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
        Division.removeDivisionsSortedByCountry();
        mainMenuController.returnToMain(event);
    }
}
