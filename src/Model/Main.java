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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/loginMenu.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        //Connect to database
        DBConnection.startConnection();

        //Working with sql statements
        DBQuery.setStatment(DBConnection.getConnection()); //Create statement object
        Statement statement = DBQuery.getStatement(); //get statement reference

        String selectStatement = "select * from country_test";
        statement.execute(selectStatement);

        ResultSet rs = statement.getResultSet();

        //forward scroll result set
        //loop will terminate once all records have iterated
        while(rs.next()){
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            LocalDate date = rs.getDate("Create_Date").toLocalDate();
            LocalTime time = rs.getTime("Create_Date").toLocalTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();

            //Display record
            System.out.println(countryID + " | " + country + " | " + date + " | " + time + " | " + createdBy + " | " + lastUpdate);
        }




        //raw sql insert statement
        //String insertStatement = "INSERT INTO customers_test(user_name) VALUES('Stahl')";

        //execute sql statement
        //statement.execute(insertStatement);

        //Confirms rows were affected
        //if(statement.getUpdateCount() > 0) System.out.println(statement.getUpdateCount() + " row(s) affected");
       // else System.out.println("No Change");

        //Update statement
        //String updateStatement = "UPDATE customers_test SET user_name = 'STAHLING' WHERE userID = 1";
        //statement.execute(updateStatement);

        //Delete Statement
        //String deleteStatement = "DELETE FROM customers_test" +
        //                         " WHERE userID = 3";
        //statement.execute(deleteStatement);




        launch(args);
        //executed when all windows are closed in application
        DBConnection.closeConnection();
    }
}
