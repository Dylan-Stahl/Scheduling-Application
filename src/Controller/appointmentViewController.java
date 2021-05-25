package Controller;

import Model.Appointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class appointmentViewController {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton allApptsRBtn;
    @FXML
    private ToggleGroup appointmentsViewToggle;
    @FXML
    private RadioButton apptsWeeklyRBtn;
    @FXML
    private RadioButton apptsMonthlyRBtn;
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
    void onActionDisplayAllAppts(ActionEvent event) throws SQLException {
        apptTableView.setItems(Appointments.initalizeAppts());
    }

    @FXML
    void onActionDisplayMonthlyAppts(ActionEvent event) throws SQLException {
        apptTableView.setItems(Appointments.initializeMonthlyAppts());
    }

    @FXML
    void onActionDisplayWeeklyAppts(ActionEvent event) throws SQLException {
        apptTableView.setItems(Appointments.initializeWeeklyAppts());
    }

    @FXML
    void onActionDisplayPastAppointments(ActionEvent event) throws SQLException {
        apptTableView.setItems(Appointments.initializePastAppts());
    }

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
        mainMenuController.returnToMain(event);
    }

    @FXML
    void initialize() throws SQLException {
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
