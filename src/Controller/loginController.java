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
import java.util.function.Predicate;

/**
 * Controller for the loginController view. Comments are used to describe the FXML ActionEvents.
 */
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
        String enteredUsername = userIdField.getText();
        String enteredPassword = passwordField.getText();
        String incorrectInformation = "Incorrect username or password!";
        Boolean correctCredentials = DBConnection.login(enteredUsername, enteredPassword);

        if(correctCredentials == true) {
            mainMenuController.returnToMain(event);
        }
        //User entered incorrect information
        else if (correctCredentials == false){
            try{
                errorLabel.setText(incorrectInformation);

                ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/Nat" , Locale.getDefault());
                if(Locale.getDefault().getLanguage().equals("fr")) {
                    errorLabel.setText(rb.getString("incorrectInformation"));
                }
            }
            catch (Exception e) {
                //Locale language is not french
            }
        }
    }

    //All fields in the loginMenu view will be shown in French if the user's Locale is France.
    @FXML
    void initialize() {
        //Changing the locale to french for testing purposes
        //Locale france = new Locale("fr");
        //Locale.setDefault(france);

        try {
            //If the locale language is not french, the location label will display the correct information in English.
            locationZoneID.setText("Location: " + ZoneId.systemDefault());
            ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/Nat" , Locale.getDefault());

            if(Locale.getDefault().getLanguage().equals("fr")) {
                userIDLocale.setText(rb.getString("Username"));
                passwordLocale.setText(rb.getString("Password"));
                loginButton.setText(rb.getString("Login"));

                //If the locale is french, then location will always be translated to french
                locationZoneID.setText(rb.getString("Location") + ZoneId.systemDefault());
                System.out.println(ZoneId.systemDefault());
                //This attempts to translate the zone id to french if is it located in the Nat_fr.properties
                //If the zone id is not located in that file, than the zone id will be in english and the word location
                //will still be in french because of the statement above.
                locationZoneID.setText(rb.getString("Location") +
                        rb.getString(String.valueOf(ZoneId.systemDefault())));
            }
        }
        catch (Exception e) {
            //Program runs as usual with English
        }
    }
}
