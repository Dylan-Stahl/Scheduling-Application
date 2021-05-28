package Controller;

import Model.Appointments;
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

/**
 * Controller for appointmentView view.
 */
public class appointmentViewController {
    //Used to set a new scene.
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

    //Following methods use are called when the radio button for that ActionEvent is clicked. They display appointments
    //that fit the criteria of the radio button title.
    @FXML
    void onActionDisplayAllAppts(ActionEvent event) throws SQLException {
        apptTableView.setItems(Appointments.initializeAppts());
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
    void onActionReturnToMain(ActionEvent event) throws IOException, SQLException {
        mainMenuController.returnToMain(event);
    }

    /**
     * Loads the numberOfAppointments view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionViewNumberOfAppts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/numberOfAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * The appointmentView initialize method loads all the appointments into the table using the Appointments' class
     * method initializeAppts().
     * @throws SQLException
     */
    @FXML
    void initialize() throws SQLException {
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
    }
}
