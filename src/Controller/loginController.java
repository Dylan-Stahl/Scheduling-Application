package Controller;

import Utilities.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginController {
    @FXML
    private TextField userIdField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label userIDLocale;
    @FXML
    private Label passwordLocale;
    @FXML
    private Label errorLabel;
    @FXML
    private Label locationZoneID;


    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        //Login validation
        String incorrectInformation = "Incorrect username or password!";
        String enteredUsername = userIdField.getText();
        String enteredPassword = passwordField.getText();

        //Tracking user activity
        File fileReport = new File("login_activity.txt");
        FileWriter file = new FileWriter(fileReport, true);
        PrintWriter printWriter = new PrintWriter(file);

        printWriter.println("Login Attempt at " + LocalDateTime.now() + " " + ZoneId.systemDefault());

        boolean usernameResults = DBConnection.readUsername(enteredUsername);
        boolean passwordResults = DBConnection.readUsername(enteredPassword);

        if(usernameResults && passwordResults) {
            mainMenuController.returnToMain(event);
            printWriter.println("Login successful!");
            printWriter.println("");
        }
        else {
            try{
                errorLabel.setText(incorrectInformation);
                printWriter.println("Incorrect username or password entered!");
                printWriter.println("");

                ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/Nat" , Locale.getDefault());
                if(Locale.getDefault().getLanguage().equals("fr")) {
                    errorLabel.setText(rb.getString("incorrectInformation"));
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        printWriter.close();
    }

    @FXML
    void initialize() {
        //Locale france = new Locale("fr");
        //Locale.setDefault(france);
        //Locale US = new Locale("en");
        //Locale.setDefault(US);

        try {
            locationZoneID.setText("Location: " + ZoneId.systemDefault());

            ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/Nat" , Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")) {
                userIDLocale.setText(rb.getString("Username"));
                passwordLocale.setText(rb.getString("Password"));
                loginButton.setText(rb.getString("Login"));
                locationZoneID.setText(rb.getString("Location") + ZoneId.systemDefault());

                System.out.println("huh");
            }
        }
        catch (Exception e) {
            //Program runs as usual with English
        }
    }
}
