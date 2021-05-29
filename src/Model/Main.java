package Model;

import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main Method. Connects to the database when application is run and starts up the JavaFX application. When all the
 * windows are close, the database connection is closed.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/loginMenu.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root, 450, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        //Connect to database
        DBConnection.startConnection();

        //Launch javaFX application
        launch(args);

        //executed when all windows are closed in application
        DBConnection.closeConnection();
    }
}
