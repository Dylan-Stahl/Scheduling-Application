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

    /**
     * When a user attempts to login, this function is called and either logs the user in, or, tells the user "Incorrect
     * username or password entered". It also writes to login_activity.txt in the root folder of this application. The
     * text files will show the time the login attempt was at, where the attempt was, and whether it was successful or
     * not. To determine if the username and password are correct, a lambda expression using the predicate interface is
     * used to return a boolean value. Two predicates are used, for the username and password, when they both return
     * true, the credentials entered are correct and the user is logged in.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
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

        //Uses predicate interface to interact with the readUsername and readPassword methods.
        Predicate<String> userNameResults = (s) -> DBConnection.readUsername(s);
        Predicate<String> passwordResults = (s) -> DBConnection.readPassword(s);

        //User entered correct information
        //Predicate interface uses the test abstract method to return a boolean result, if both return true, the
        //credentials are correct and the user is successfully logged in
        if(userNameResults.test(enteredUsername) && passwordResults.test(enteredPassword)) {
            mainMenuController.returnToMain(event);
            printWriter.println("Login successful!");
            printWriter.println("");
        }
        //User entered incorrect information
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

    /**
     * All fields in the loginMenu view will be shown in French if the user's Locale is France.
     */
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
