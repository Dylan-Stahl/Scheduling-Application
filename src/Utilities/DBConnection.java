package Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

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

    public static Connection startConnection() {
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

    public static Connection getConnection() {
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection Closed");
        }
        catch(Exception e) {
        }
    }
}
