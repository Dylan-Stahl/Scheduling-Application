package Model;

import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.DateTimeStringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointments {
    private static ObservableList<Appointments> allAppts = FXCollections.observableArrayList();

    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDateTime Start;
    private LocalDateTime End;
    private Date Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;
    private String startDateTime;
    private String endDateTime;


    public Appointments(int appointment_ID, String title, String description, String location, String type,
                        LocalDateTime start, LocalDateTime end, Date create_Date, String created_By, Timestamp last_Update,
                        String last_Updated_By, int customer_ID, int user_ID, int contact_ID) {
        Appointment_ID = appointment_ID;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
        Customer_ID = customer_ID;
        User_ID = user_ID;
        Contact_ID = contact_ID;
    }

    public static ObservableList<Appointments> initalizeAppts() throws SQLException {
        allAppts.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = (conn.prepareStatement("SELECT * FROM appointments;"))) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Customer_ID");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");
                LocalDateTime Start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End = rs.getTimestamp("End").toLocalDateTime();
                Date Create_Date = rs.getDate("Create_Date");
                String Created_By = rs.getString("Created_By");
                Timestamp Last_Update = rs.getTimestamp("Last_Update");
                String Last_Updated_By = rs.getString("Last_Updated_By");
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");


                Appointments newAppointment = new Appointments(Appointment_ID, Title, Description, Location, Type,
                        Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID,
                        User_ID, Contact_ID);
                allAppts.add(newAppointment);
            }
            rs.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        return allAppts;
    }

    public static void delecteAppt(Appointments apptToDelete) {
        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = (conn.prepareStatement("DELETE FROM appointments WHERE ?"))) {
            ps.setString(1, String.valueOf(apptToDelete.getAppointment_ID()));

            ps.executeUpdate();


            System.out.println("Deleted: " + ps.getUpdateCount() + " rows.");
            allAppts.remove(apptToDelete);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ObservableList<Appointments> returnAllAppts() {
        return allAppts;
    }

    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public LocalDateTime getStart() {
        return Start;
    }

    public void setStart(LocalDateTime start) {
        Start = start;
    }

    public LocalDateTime getEnd() {
        return End;
    }

    public void setEnd(LocalDateTime end) {
        End = end;
    }

    public Date getCreate_Date() {
        return Create_Date;
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

    public Timestamp getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(Timestamp last_Update) {
        Last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

}
