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
import java.util.Optional;

public class mainMenuController {
    Stage stage;
    Parent scene;

    //Used to open modify menu correctly
    Customers customerToModify;

    @FXML
    private TableView<Appointments> apptTableView;

    @FXML
    private ToggleGroup appointmentView;

    @FXML
    private TableColumn<?, ?> apptIDCol;

    @FXML
    private TableColumn<?, ?> apptTitleCol;

    @FXML
    private TableColumn<?, ?> apptDescCol;

    @FXML
    private TableColumn<?, ?> apptLocCol;

    @FXML
    private TableColumn<?, ?> apptContactCol;

    @FXML
    private TableColumn<?, ?> apptTypeCol;

    @FXML
    private TableColumn<?, ?> apptStartsCol;

    @FXML
    private TableColumn<?, ?> apptEndsCol;

    @FXML
    private TableColumn<?, ?> apptCustIDCol;

    @FXML
    private TableView<Customers> customerTableView;

    @FXML
    private TableColumn<?, ?> customersIDCol;

    @FXML
    private TableColumn<?, ?> customersNameCol;

    @FXML
    private TableColumn<?, ?> customersAddressCol;

    @FXML
    private TableColumn<?, ?> customersPostalCol;

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
    void onActionDeletePart(ActionEvent event) {

    }

    @FXML
    void onActionDisplayMonthlyAppts(ActionEvent event) {

    }

    @FXML
    void onActionDisplayWeekly(ActionEvent event) {

    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/appointmentRecordsModify.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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

        apptTableView.setItems(Appointments.initalizeAppts());

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptStartsCol.setCellValueFactory(new PropertyValueFactory<>("Starts"));
        apptEndsCol.setCellValueFactory(new PropertyValueFactory<>("Ends"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

    }
}
