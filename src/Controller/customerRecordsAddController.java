package Controller;

import Model.Country;
import Model.Division;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Controller for customerRecordsAdd view. Comments are used to describe the FXML ActionEvents.
 */
public class customerRecordsAddController {
    //Used to set a new scene.
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
    @FXML
    private Label exceptionLabelName;
    @FXML
    private Label exceptionLabelAddress;
    @FXML
    private Label exceptionLabelPostal;
    @FXML
    private Label exceptionLabelPhone;
    @FXML
    private Label exceptionLabelCountry;
    @FXML
    private Label exceptionLabelFLD;


    //Returns user to main menu.
    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException, SQLException {
        Division.removeDivisionsSortedByCountry();
        mainMenuController.returnToMain(event);
    }


    //When a country is selected, only first level divisions in that country will be displays in the State/province
    //combo box.
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


    //When the add button is clicked, this method will attempt to add a new customer to the database. If incorrect
    //or null data is input, then the appropriate error message will display.
    @FXML
    void onActionCreateCustomer(ActionEvent event) throws IOException, SQLException {
        //Resets the exception labels each time a user trys to add a customer to ensure accurate error messages
        //are displayed.
        exceptionLabelName.setText("");
        exceptionLabelAddress.setText("");
        exceptionLabelPostal.setText("");
        exceptionLabelPhone.setText("");
        exceptionLabelCountry.setText("");
        exceptionLabelFLD.setText("");
        boolean exception = false;

        String newCustomerName = addCustomerNameField.getText();
        String newCustomerAddress = addCustomerAddressField.getText();
        String newCustomerPostal = addPostalCodeField.getText();
        String newCustomerPhone = addCustomerPhoneField.getText();

        //Initialize, assign value if selected item in combo is not null
        String newCustomerDivision = null;
        String newCustomerCountry = null;

        //Exceptions
        if(addCustomerNameField.getText().equals("")) {
            exceptionLabelName.setText("Customer name can't be empty");
            exception = true;
        }
        if(addCustomerAddressField.getText().equals("")) {
            exceptionLabelAddress.setText("Customer address can't be empty");
            exception = true;
        }
        if(addPostalCodeField.getText().equals("")) {
            exceptionLabelPostal.setText("Customer postal code can't be empty");
            exception = true;
        }
        if(addCustomerPhoneField.getText().equals("")) {
            exceptionLabelPhone.setText("Customer phone can't be empty");
            exception = true;
        }
        if(addCustomerDivCombo.getSelectionModel().getSelectedItem() == null) {
            exceptionLabelFLD.setText("Customer first level division can't be empty");
            exception = true;
        } else{
            newCustomerDivision = addCustomerDivCombo.getValue().toString();
        }
        if(addCustomerCountryCombo.getSelectionModel().getSelectedItem() == null) {
            exceptionLabelCountry.setText("Customer country selection can't be empty");
            exception = true;
        } else {
            newCustomerCountry = addCustomerCountryCombo.getValue().toString();
        }

        //Only continues with SQL statement if there are no exceptions up until this point.
        if(exception == false) {
            //call getDivisionID, search table with division name, and get id
            int divisionID = Division.getDivisionID(newCustomerDivision);

            //Save user updating data for insertion into database
            String user = DBConnection.returnUsername();
            Connection conn = DBConnection.getConnection();
            //Insert information
            String sqlInsert = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                    "Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?);";
            try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setString(1,newCustomerName);
                ps.setString(2,newCustomerAddress);
                ps.setString(3,newCustomerPostal);
                ps.setString(4,newCustomerPhone);
                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, user);
                ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(8, user);
                ps.setInt(9, divisionID);

                ps.executeUpdate();
                //After the customer is added successfully, the user is returned to the main menu.
                mainMenuController.returnToMain(event);
            }
            catch(SQLException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getSQLState());
            }
        }
    }


    //Sets items within combo boxes.
    @FXML
    void initialize() {
        addCustomerCountryCombo.setItems(Country.initializeAllCountries());
        addCustomerDivCombo.setItems(Division.initializeAllDivisions());
    }

}
