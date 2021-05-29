package Controller;

import Model.Appointments;
import Model.Customers;
import Utilities.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BooleanSupplier;

/**
 * Controller for the mainMenu view.
 */
public class mainMenuController {
    //Used to set new scene.
    Stage stage;
    Parent scene;

    //Used to open modify menu correctly
    Customers customerToModify;
    Appointments appointmentToModify;

    @FXML
    private Label showNumberOfApptsWithin15;

    //Appointments tableview FXML
    @FXML
    private TableView<Appointments> apptTableView;
    @FXML
    private TableColumn<Appointments, Integer> apptIDCol;
    @FXML
    private TableColumn<Appointments, String> apptTitleCol;
    @FXML
    private TableColumn<Appointments, String> apptDescCol;
    @FXML
    private TableColumn<Appointments, String> apptLocCol;
    @FXML
    private TableColumn<Appointments, Integer> apptContactCol;
    @FXML
    private TableColumn<Appointments, String> apptTypeCol;
    @FXML
    private TableColumn<Appointments, LocalDateTime> apptStartsCol;
    @FXML
    private TableColumn<Appointments, LocalDateTime> apptEndsCol;
    @FXML
    private TableColumn<Appointments, Integer> apptCustIDCol;

    //Customer table view FXML
    @FXML
    private TableView<Customers> customerTableView;
    @FXML
    private TableColumn<Customers, Integer> customersIDCol;
    @FXML
    private TableColumn<Customers, String> customersNameCol;
    @FXML
    private TableColumn<Customers, String> customersAddressCol;
    @FXML
    private TableColumn<Customers, String> customersPostalCol;
    @FXML
    private TableColumn<Customers, String> customerCountryCol;
    @FXML
    private TableColumn<Customers, String> customerFLDCol;

    //Appointment within 15 minutes tableview FXML
    @FXML
    private TableView<Appointments> apptWithin15TableView;
    @FXML
    private TableColumn<Appointments, Integer> apptWithin15Col;

    /**
     * Safely exits application.
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
        alert.setTitle("Exiting Program");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            DBConnection.closeConnection();
            System.exit(0);
        }
    }

    /**
     * Used by other controllers to return the user to the main menu.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public static void returnToMain(ActionEvent event) throws IOException, SQLException {
        Stage stage;
        Parent scene;


        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the customerRecordsAdd view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/customerRecordsAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the appointmentRecordsAdd view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/appointmentRecordsAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * When the user selects the delete button under the customers table view this method will either tell the user
     * to select a customer or that there are no customers in the table. If a customer is selected, the method
     * will perform a SQL operation on the database to delete that customer.
     * @param event
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

        try {
            if(customerTableView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("Select a customer to delete!");
                alert.showAndWait();
            }
            else {
                Customers customerToDelete = customerTableView.getSelectionModel().getSelectedItem();;
                //Tells user they are unable to delete a product with a part associated to that product.

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the " +
                        "customer selected?");
                alert.setTitle("Deleting Customer!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    //The deleteCustomer method will check to see if that customer has any appointments associated with
                    //it. If the customer has appointments than a warning will tell the user that they can't delete that
                    //customer.
                    if(Customers.customerAssociatedWithAppt(customerToDelete) == false) {
                        //If the customer didn't have any appointments associated with it, than a warning will tell the user
                        //that a customer was deleted.
                        Alert customerDeleted = new Alert(Alert.AlertType.WARNING);
                        customerDeleted.setTitle("Customer Deleted!");
                        customerDeleted.setContentText("The customer " + customerToDelete.getCustomer_Name() + " " +
                                "has been deleted!");
                        customerDeleted.showAndWait();

                        Customers.deleteCustomer(customerToDelete);
                    }
                    else {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Error Dialogue");
                        alert1.setContentText("Customers associated with appointments can not be deleted!");
                        alert1.showAndWait();
                    }
                }
            }
        }catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Select a customer!");
            alert.showAndWait();
        }
    }

    /**
     * When the user selects the delete button under the appointments table view this method will either tell the user
     * to select an appointment or that there are no appointments in the table. If an appointment is selected, the
     * method will perform a SQL operation on the database to delete that appointment.
     * @param event
     */
    @FXML
    void onActionDeleteAppt(ActionEvent event) {
        try {
            if(apptTableView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("No appointment selected!");
                alert.showAndWait();
            }
            else {
                Appointments apptToDelete = apptTableView.getSelectionModel().getSelectedItem();;

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the appointment selected?");
                alert.setTitle("Deleting Appointment!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Appointments.delecteAppt(apptToDelete);

                    //After the appointment was deleted, a warning will be displayed to the user letting them know
                    //details about the appointment deleted.
                    Alert customerDeleted = new Alert(Alert.AlertType.WARNING);
                    customerDeleted.setTitle("Appointment Deleted!");
                    customerDeleted.setContentText("Appointment with ID: \"" + apptToDelete.getAppointment_ID() + "\" " +
                            "and type: \"" + apptToDelete.getType() + "\" has been deleted!");
                    customerDeleted.showAndWait();
                }
            }
        }catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Select an appointment!");
            alert.showAndWait();
        }
    }


    /**
     * Opens the contactSchedules view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionViewContactSchedules(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/View/contactSchedules.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the appointmentRecordsModify view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        appointmentToModify = apptTableView.getSelectionModel().getSelectedItem();

        //The stage will be opened with data about the appointment shown using the sendAppt() method.
        if(appointmentToModify != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/appointmentRecordsModify.fxml"));
            loader.load();
            appointmentRecordsModifyController modifyAppt = loader.getController();
            modifyAppt.sendAppt(appointmentToModify);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        //No appointment was selected.
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Select an appointment!");
            alert.showAndWait();
        }
    }

    /**
     * Opens the customerRecordsModify view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        customerToModify = customerTableView.getSelectionModel().getSelectedItem();

        //The stage will be opened with data about the customer shown using the sendCustomer() method.
        if(customerToModify != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/customerRecordsModify.fxml"));
            loader.load();
            customerRecordsModifyController modifyCustomer = loader.getController();
            modifyCustomer.sendCustomer(customerToModify);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        //No customer was selected
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Select a customer!");
            alert.showAndWait();
        }
    }

    /**
     * Opens the appointmentsView view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionReportNumberOfAppts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/appointmentsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the numberOfAppointments view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionViewNumOfAppts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/numberOfAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes the tableviews in the main menu. The BooleanSupplier lambda expression will determine if an
     * appointment is within fifteen minutes. If the appointment is within 15 minutes, the if statement contents will
     * execute and show the appointments within 15 minutes in the table.
     * @throws SQLException
     */
    @FXML
    void initialize() throws SQLException {
        customerTableView.setItems(Customers.initializeCustomers());

        customersIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customersNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customersAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customersPostalCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        customerFLDCol.setCellValueFactory(new PropertyValueFactory<>("Division_IDStr"));


        apptTableView.setItems(Appointments.initializeAppts());

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptStartsCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        apptEndsCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

        //Everytime the main menu is loaded, i want to check if an appointment is within 15 minutes of the local date
        //time. To do this, I used the BooleanSupplier interface.
        //BooleanSupplier will rely on another function to determine if an appointment is within 15 minutes, if it is
        //it will return true, if it does return true, initializeApptAlertTable() will show the correct appointments.
        BooleanSupplier apptWithinFifteen = () -> Appointments.appointmentWithinFifteenMin();
        if(apptWithinFifteen.getAsBoolean() == true) {
            showNumberOfApptsWithin15.setText("Number of appointments: " +
                    Appointments.initializeApptAlertTable().size());
            apptWithin15TableView.setItems(Appointments.initializeApptAlertTable());
            apptWithin15Col.setCellValueFactory(new PropertyValueFactory<>("appointmentIDForAlert"));
        }
        else {
            showNumberOfApptsWithin15.setText("Number of appointments: 0");
        }
    }
}
