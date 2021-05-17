package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class mainMenuController {
    Stage stage;
    Parent scene;

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
    private TableColumn<?, ?> customersIDCol;

    @FXML
    private TableColumn<?, ?> customersNameCol;

    @FXML
    private TableColumn<?, ?> customersAddressCol;

    @FXML
    private TableColumn<?, ?> customersPostalCol;

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
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(mainMenuController.class.getResource("/view/customerRecordsModify.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionReportNumberOfAppts(ActionEvent event) {

    }

    @FXML
    void onActionViewSchedules(ActionEvent event) {

    }


}
