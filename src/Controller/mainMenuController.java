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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public class mainMenuController {
    Stage stage;
    Parent scene;

    //Used to open modify menu correctly
    Customers customerToModify;
    Appointments appointmentToModify;

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
    private TableColumn<Appointments, LocalDate> apptEndsCol;
    @FXML
    private TableColumn<Appointments, Integer> apptCustIDCol;

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


    public static void returnToMain(ActionEvent event) throws IOException {
        Stage stage;
        Parent scene;
/*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The information inputted will not be saved, are you "
                + "sure you would like to return to the main menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

 */
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/customerRecordsAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/appointmentRecordsAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

        try {
            if(customerTableView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("No customer to delete!");
                alert.showAndWait();
            }
            else {
                Customers customerToDelete = customerTableView.getSelectionModel().getSelectedItem();;
                //Tells user they are unable to delete a product with a part associated to that product.

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the customer selected?");
                alert.setTitle("Deleting Customer!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Customers.deleteCustomer(customerToDelete);
                    Alert customerDeleted = new Alert(Alert.AlertType.WARNING);
                    customerDeleted.setTitle("Customer Deleted!");
                    customerDeleted.setContentText("The selected customer has been deleted!");
                    customerDeleted.showAndWait();
                }
            }

        }catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Select a customer!");
            alert.showAndWait();
        }
    }


    @FXML
    void onActionDeleteAppt(ActionEvent event) {
        try {
            if(apptTableView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("No appointment to delete!");
                alert.showAndWait();
            }
            else {
                Appointments apptToDelete = apptTableView.getSelectionModel().getSelectedItem();;


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the appointment selected?");
                alert.setTitle("Deleting Appointment!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Appointments.delecteAppt(apptToDelete);
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

    @FXML
    void onActionDisplayMonthlyAppts(ActionEvent event) {

    }

    @FXML
    void onActionDisplayWeekly(ActionEvent event) {

    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        appointmentToModify = apptTableView.getSelectionModel().getSelectedItem();
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
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Select an appointment!");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        customerToModify = customerTableView.getSelectionModel().getSelectedItem();
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
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Select a customer!");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionReportNumberOfAppts(ActionEvent event) {

    }

    @FXML
    void onActionViewSchedules(ActionEvent event) {

    }

    @FXML
    void initialize() throws SQLException {
        customerTableView.setItems(Customers.initializeCustomers());

        customersIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customersNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customersAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customersPostalCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        customerFLDCol.setCellValueFactory(new PropertyValueFactory<>("Division_IDStr"));


        apptTableView.setItems(Appointments.initalizeAppts());

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptStartsCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        apptEndsCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

    }
}
