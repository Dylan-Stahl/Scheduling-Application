package Controller;

import Model.Country;
import Model.Division;
import Utilities.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private ComboBox<Country> addCustomerCountryCombo;
    @FXML
    private ComboBox<Division> addCustomerDivCombo;

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
        mainMenuController.returnToMain(event);

    }

    @FXML
    void onActionCreateCustomer(ActionEvent event) throws IOException {
        String newCustomerName = addCustomerNameField.getText();
        String newCustomerAddress = addCustomerAddressField.getText();
        String newCustomerPostal = addPostalCodeField.getText();
        String newCustomerPhone = addCustomerPhoneField.getText();
        String newCustomerDivision = addCustomerDivCombo.getValue().toString();

        //call division, search table with division name, and get id
        int divisionID = Division.getDivisionID(newCustomerDivision);

        //Determine user updating data
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
            System.out.println("Updated Count: " + ps.getUpdateCount());
            mainMenuController.returnToMain(event);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
    }

    @FXML
    void initialize() {
        addCustomerCountryCombo.setItems(Country.initializeAllCountries());
        addCustomerDivCombo.setItems(Division.initializeAllDivisions());
    }

}
