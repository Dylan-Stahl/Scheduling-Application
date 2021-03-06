package Model;

import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

/**
 * Holds customer data that is extracted from the database.
 */
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
    private String Country;
    private String Division_IDStr;
    private Country countryObj;
    private Division divisionObj;

    /**
     * Creates a customer object with data obtained from the initializeCustomers() method.
     * @param customer_ID holds customer ID
     * @param customer_Name holds customer name
     * @param address holds customer address
     * @param postal_Code holds customer postal code
     * @param phone holds customer phone number
     * @param create_Date holds the create date of the customer in the database
     * @param created_By holds the user who created the customer
     * @param last_Update holds the Timestamp of the last update made to the customer
     * @param last_Updated_By holds the user who last updated the customer in the database
     * @param division_ID holds the first level division ID of the customer
     * @param division_IDStr holds the first level division name of the customer
     * @param country holds the country of the customer
     * @param countryObj used to retrieve the country name where the customer is and display it in the modify customer
     *                   menu
     * @param divisionObj used to retrieve the division name where the customer is and display it in the modify customer
     *                    menu
     */
    public Customers(int customer_ID, String customer_Name, String address, String postal_Code, String phone,
                     Date create_Date, String created_By, Timestamp last_Update, String last_Updated_By,
                     int division_ID, String division_IDStr, String country, Country countryObj, Division divisionObj){
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
        this.Division_IDStr = division_IDStr;
        this.Country = country;
        this.Division_IDStr = division_IDStr;
        this.Country = country;
        this.countryObj = countryObj;
        this.divisionObj = divisionObj;
    }


    /**
     * Returns an ObservableList with all the customers from the database stored as Customers objects.
     * @return ObservableList with all customers
     */
    public static ObservableList<Customers> initializeCustomers() {
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

                //Division and country data require the methods in the Country class because sql statements are needed.
                String Division_IDStr = Model.Country.returnDivision(Division_ID);
                int Country_ID = Model.Country.returnCountryID(Division_ID);
                String Country = Model.Country.returnCountryString(Country_ID);

                Country countryObj = new Country(Country);
                Division divisionObj = new Division(Division_IDStr);

                Customers newCustomer = new Customers(Customer_ID,Customer_Name,Address, Postal_Code,Phone, Create_Date,
                        Created_By, Last_Update, Last_Updated_By, Division_ID, Division_IDStr, Country, countryObj,
                        divisionObj);
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

    /**
     * Returns the allCustomers observableList.
     * @return ObservableList
     */
    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    /**
     * Performs SQL operation on the MySQL database to delete a selected customer.
     * @param customerToDelete selected in the tableview and used as argument.
     */
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
        }

    }

    /**
     * Used to determine if a customer can be deleted from the database. If the customer has any appointment associated
     * with it, then the customer won't be allowed to be deleted.
     * @param customer uses a specified customer to search the appointments table with this customer's ID
     * @return true if the customer has any appointments associated with it. Returns falls otherwise
     */
    public static boolean customerAssociatedWithAppt(Customers customer) {
        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointments WHERE Customer_ID = ?")) {
            ps.setInt(1, customer.getCustomer_ID());

            ResultSet rs = ps.executeQuery();
            int counter = 0;
            while(rs.next()) {
                counter += 1;
            }
            if(counter > 0) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //Setters and getters
    public Model.Country getCountryObj() {
        return countryObj;
    }

    public void setCountryObj(Model.Country countryObj) {
        this.countryObj = countryObj;
    }

    public Division getDivisionObj() {
        return divisionObj;
    }

    public void setDivisionObj(Division divisionObj) {
        this.divisionObj = divisionObj;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getDivision_IDStr() {
        return Division_IDStr;
    }

    public void setDivision_IDStr(String division_IDStr) {
        Division_IDStr = division_IDStr;
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
