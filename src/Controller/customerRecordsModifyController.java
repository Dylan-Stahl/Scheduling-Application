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

    /**
     * When a customer is selected in the main menu controller and modify is clicked, that user is used as an argument
     * in this method. This method takes all the data of that customer and fills in the fields to show what is currently
     * saved in the database.
     * @param customerToModify selected in the main menu.
     */
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

    /**
     * When a country is selected, only first level divisions in that country will be displays in the State/province
     * combo box.
     * @param event
     */
    @FXML
    void onActionSortFirstLevelDivision(ActionEvent event) {
        Division.removeDivisionsSortedByCountry();
        //if a different country is selected when modifying a customer, it sets the division combo box as null,
        //that way a user cant, for example, set the customer to be in the UK for country and Alabama for first level
        //division.
        addCustomerDivCombo.valueProperty().set(null);
        try {
            String newCustomerCountry = addCustomerCountryCombo.getValue().toString();
            int newCustomerCountryID = Country.returnCountryID(newCustomerCountry);
            ObservableList<Division> divisonToSet = FXCollections.observableArrayList(Division.initializeDivisionWithSetCountry(newCustomerCountryID));

            addCustomerDivCombo.setItems(divisonToSet);
        }
        catch (NullPointerException e) {}
    }

    /**
     * When the modify button is clicked, this method will attempt to update the customer in the database. If incorrect
     * or null data is input, then the appropriate error message will display.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException, SQLException{
        //Resets the exception labels each time a user trys to add a customer to ensure accurate error messages
        //are displayed.
        exceptionLabelName.setText("");
        exceptionLabelAddress.setText("");
        exceptionLabelPostal.setText("");
        exceptionLabelPhone.setText("");
        exceptionLabelCountry.setText("");
        exceptionLabelFLD.setText("");
        boolean exception = false;

        int newCustomerID = Integer.parseInt(addCustomerIDField.getText());
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
            //call division, search table with division name, and get id
            int divisionID = Division.getDivisionID(newCustomerDivision);

            //Save user updating data for insertion into database
            String user = DBConnection.returnUsername();

            //Perform update
            Connection conn = DBConnection.getConnection();
            String sqlUpdate = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                    "Division_ID = ?, Last_Update = ?, Last_Updated_By = ? WHERE Customer_ID = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, newCustomerName);
                ps.setString(2, newCustomerAddress);
                ps.setString(3, newCustomerPostal);
                ps.setString(4, newCustomerPhone);
                ps.setInt(5, divisionID);
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(7, user);
                ps.setInt(8, newCustomerID);

                ps.executeUpdate();
                //After a customer is modified in the database, the user is sent back to the main menu.
                mainMenuController.returnToMain(event);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getSQLState());
            }
        }
    }

    /**
     * Returns user to main menu.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException, SQLException {
        Division.removeDivisionsSortedByCountry();
        mainMenuController.returnToMain(event);
    }
}
