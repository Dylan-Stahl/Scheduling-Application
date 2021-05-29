package Controller;

import Model.Appointments;
import Model.CalculateNumOfAppointmentsMonth;
import Model.CalculateNumOfAppointmentsType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Month;

/**
 * Controller for numberOfAppointments view.
 */
public class numberOfAppointmentsController {
    //Used to set a new stage.
    Stage stage;
    Parent scene;

    @FXML
    private TableView<CalculateNumOfAppointmentsType> numberOfApptsTableView;
    @FXML
    private TableColumn<CalculateNumOfAppointmentsType, String> appointmentType;
    @FXML
    private TableColumn<CalculateNumOfAppointmentsType, Integer> numberOfApptType;
    @FXML
    private Label setTotalNumberOfAppointments;
    @FXML
    private TableView<CalculateNumOfAppointmentsMonth> numberOfApptsMonthTableView;
    @FXML
    private TableColumn<CalculateNumOfAppointmentsMonth, Month> appointmentMonthCol;
    @FXML
    private TableColumn<CalculateNumOfAppointmentsMonth, Integer> numberOfApptMonthCol;
    @FXML
    private Label setTotalNumberOfAppointments1;

    /**
     * Opens the main menu.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException, SQLException {
        mainMenuController.returnToMain(event);
    }

    /**
     * Opens the appointmentsView view.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionViewAppointmentFiltering(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/appointmentsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads the tables with the data from the database and also sets the text in two labels to tell the user how
     * many appointments there are.
     * @throws SQLException
     */
    @FXML
    void initialize() throws SQLException {
        numberOfApptsTableView.setItems(CalculateNumOfAppointmentsType.initializeTypeAndNum());
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        numberOfApptType.setCellValueFactory(new PropertyValueFactory<>("numOfType"));

        numberOfApptsMonthTableView.setItems(CalculateNumOfAppointmentsMonth.initializeMonthAndNum());
        appointmentMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        numberOfApptMonthCol.setCellValueFactory(new PropertyValueFactory<>("numPerMonth"));

        setTotalNumberOfAppointments.setText(String.valueOf(Appointments.initializeAppts().size()));
        setTotalNumberOfAppointments1.setText(String.valueOf(Appointments.initializeAppts().size()));
    }

}

