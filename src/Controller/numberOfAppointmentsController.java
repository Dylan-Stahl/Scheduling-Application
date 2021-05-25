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

public class numberOfAppointmentsController {
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

    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException {
        mainMenuController.returnToMain(event);
    }

    @FXML
    void onActionViewAppointmentFiltering(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/appointmentsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void initialize() throws SQLException {
        numberOfApptsTableView.setItems(CalculateNumOfAppointmentsType.initializeTypeAndNum());
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        numberOfApptType.setCellValueFactory(new PropertyValueFactory<>("numOfType"));

        numberOfApptsMonthTableView.setItems(CalculateNumOfAppointmentsMonth.initializeMonthAndNum());
        appointmentMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        numberOfApptMonthCol.setCellValueFactory(new PropertyValueFactory<>("numPerMonth"));

        setTotalNumberOfAppointments.setText(String.valueOf(Appointments.initalizeAppts().size()));
        setTotalNumberOfAppointments1.setText(String.valueOf(Appointments.initalizeAppts().size()));
    }

}

