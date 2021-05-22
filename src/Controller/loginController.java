package Controller;

import Utilities.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
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
    void onActionLogin(ActionEvent event) throws IOException {
        String incorrectInformation = "Incorrect username or password!";
        String enteredUsername = userIdField.getText();
        String enteredPassword = passwordField.getText();

        boolean usernameResults = DBConnection.readUsername(enteredUsername);
        boolean passwordResults = DBConnection.readUsername(enteredPassword);

        if(usernameResults && passwordResults) {
            mainMenuController.returnToMain(event);
        }
        else {
            try{
                errorLabel.setText(incorrectInformation);

                ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/Nat" , Locale.getDefault());
                if(Locale.getDefault().getLanguage().equals("fr")) {
                    errorLabel.setText(rb.getString("incorrectInformation"));
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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
            System.out.println(e.getMessage());
        }

        //errorLabel.setText("working");
    }
}
