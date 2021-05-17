package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Customers {
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
