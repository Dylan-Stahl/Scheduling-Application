package Model;

import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;

import java.sql.*;

public class Customers {
    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private Date Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int Division_ID;

    public Customers(int customer_ID, String customer_Name, String address, String postal_Code, String phone,
                     Date create_Date, String created_By, Timestamp last_Update, String last_Updated_By,
                     int division_ID){
        this.Customer_ID = customer_ID;
        this.Customer_Name = customer_Name;
        this.Address = address;
        this.Postal_Code = postal_Code;
        this.Phone = phone;
        this.Create_Date = create_Date;
        this.Created_By = created_By;
        this.Last_Update = last_Update;
        this.Last_Updated_By = last_Updated_By;
        this.Division_ID = division_ID;
    }

    public static ObservableList<Customers> initializeCustomers() throws SQLException {
        allCustomers.clear();


        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers;")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                Date Create_Date = rs.getDate("Create_Date");
                String Created_By = rs.getString("Created_By");
                Timestamp Last_Update = rs.getTimestamp("Last_Update");
                String Last_Updated_By = rs.getString("Last_Updated_By");
                int Division_ID = rs.getInt("Division_ID");

                Customers newCustomer = new Customers(Customer_ID,Customer_Name,Address, Postal_Code,Phone, Create_Date,
                        Created_By, Last_Update, Last_Updated_By, Division_ID);
                allCustomers.add(newCustomer);

            }
            rs.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }

        return allCustomers;
    }

    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    public static void deleteCustomer(Customers customerToDelete) {
        try{
            int customerToDeleteSQL = customerToDelete.getCustomer_ID();

            DBQuery.setStatment(DBConnection.getConnection()); //Create statement object
            Statement statement = DBQuery.getStatement(); //get statement reference

            String deleteStatement = "DELETE FROM customers WHERE Customer_ID = " + "'" + customerToDeleteSQL + "'";
            statement.execute(deleteStatement);

            allCustomers.remove(customerToDelete);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Customers associated with appointments can not be deleted!");
            alert.showAndWait();
        }

    }


    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public void setCreate_Date(Date create_Date) {
        Create_Date = create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public void setLast_Update(Timestamp last_Update) {
        Last_Update = last_Update;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

}
