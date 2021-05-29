package Utilities;
import Controller.mainMenuController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public final class DBConnection {

    //Login information
    private static final String loginUsername = "test";
    private static final String loginPassword = "test";

    //JDBC URL Parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/WJ08OVD";
    private static final String disableSSLError = "?autoReconnect=true&useSSL=false";
    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + disableSSLError;

    //Driver Interface Reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    //Username
    private static final String username = "U08OVD";
    //Password
    private  static final String password = "53689347344";

    /**
     * When this method is called, at the start of the main method, it establishes a connection with database
     * using the jdbcURL, username, and password of the database.
     * @return connection.
     */
    public final static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * It is not efficient to call the startConnection method each time a SQL statement is queried. The getConnection
     * method returns the connection to the already connected database.
     * @return the database connection
     */
    public final static Connection getConnection() {
        return conn;
    }

    /**
     * When the application is closed, it is best practice to close the connection to the database, using this method.
     */
    public final static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection Closed");
        }
        catch(Exception e) {
        }
    }

    /**
     * Boolean method to determine if a user entered password is the correct password.
     * @param enteredPassword is compared with the correct login password.
     * @return returns true for correct password argument and false for incorrect password.
     */
    public final static boolean readPassword(String enteredPassword) {
        boolean isCorrect = false;

        if(enteredPassword.equals(loginPassword)) {
            isCorrect = true;
        }

        return isCorrect;
    }

    /**
     * Boolean method to determine if a user entered username is the correct username.
     * @param enteredUsername is compared with the correct login username.
     * @return returns true for correct username argument and false for incorrect username.
     */
    public final static boolean readUsername(String enteredUsername) {
        boolean isCorrect = false;

        if(enteredUsername.equals(loginUsername)) {
            isCorrect = true;
        }

        return isCorrect;
    }

    /**
     * When a user attempts to login, this function is called and returns true or false. It also writes to
     * login_activity.txt in the root folder of this application. The text files will show the time the login attempt
     * was at, where the attempt was, and whether it was successful or not. To determine if
     * the username and password are correct, a lambda expression using the predicate interface is
     * used to return a boolean value. Two predicates are used, for the username and password, when they both return
     * true, the credentials entered are correct and the method returns true. The login controller will use this boolean
     * value to either open the main menu or write to an error label.
     * @param username used to determine if it matches the correct username.
     * @param password used to determine if it maches the correct password.
     * @throws IOException possible input output exception.
     * @return true for correct username and password and returns false for incorrect username or password.
     */
    public static boolean login(String username, String password) throws IOException {
        String incorrectInformation = "Incorrect username or password!";

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
        if((userNameResults.test(username) == true) && (passwordResults.test(password) == true)) {
            printWriter.println("Login successful!");
            printWriter.println("");
            printWriter.close();
            return true;
        }
        //User entered incorrect information
        else {
            try{
                printWriter.println("Incorrect username or password entered!");
                printWriter.println("");
                printWriter.close();
                return false;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        printWriter.close();
        return false;
    }

    public final static String returnUsername() {return loginUsername;}
}
