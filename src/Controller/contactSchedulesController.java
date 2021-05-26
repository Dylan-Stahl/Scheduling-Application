package Controller;

import Model.Contacts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class contactSchedulesController {

    @FXML
    private ComboBox<Contacts> contactComboBox;
    @FXML
    private TableView<Contacts> apptTableView;
    @FXML
    private TableColumn<Contacts, Integer> apptIDCol;
    @FXML
    private TableColumn<Contacts, String> apptTitleCol;
    @FXML
    private TableColumn<Contacts, String> apptTypeCol;
    @FXML
    private TableColumn<Contacts, String> apptDescCol;
    @FXML
    private TableColumn<Contacts, LocalDateTime> apptStartsCol;
    @FXML
    private TableColumn<Contacts, LocalDateTime> apptEndsCol;
    @FXML
    private TableColumn<Contacts, String> apptCustIDCol;

    @FXML
    void onActionReturnToMain(ActionEvent event) throws SQLException, IOException {
        mainMenuController.returnToMain(event);
    }

    @FXML
    void onActionUpdateTable(ActionEvent event) {
        try {
            String contactToDisplay = contactComboBox.getSelectionModel().getSelectedItem().toString();
            int contactID = Contacts.returnContactID(contactComboBox.getSelectionModel().getSelectedItem());
            apptTableView.setItems(Contacts.initializeContactSchedule(contactID));
            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            apptDescCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            apptStartsCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            apptEndsCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void initialize() throws SQLException {
        contactComboBox.setItems(Contacts.initializeContacts());
    }
}
