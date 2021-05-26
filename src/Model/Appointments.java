package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

public class Appointments {
    private static ObservableList<Appointments> allAppts = FXCollections.observableArrayList();
    private static ObservableList<Appointments> appointmentAlerts = FXCollections.observableArrayList();

    private int appointmentIDForAlert;
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

    public Appointments(int appointmentIDForAlert) {
        this.appointmentIDForAlert = appointmentIDForAlert;
    }

    public static ObservableList<Appointments> initializeApptAlertTable() {
        appointmentAlerts.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointments;")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                LocalDateTime startLDT = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime currentLDT = LocalDateTime.now();
                int appointmentID = rs.getInt("Appointment_ID");

                long timeDifMonthStart = ChronoUnit.MONTHS.between(currentLDT, startLDT);
                long timeDifDayStart = ChronoUnit.DAYS.between(currentLDT, startLDT);
                long timeDifMinStart = ChronoUnit.MINUTES.between(currentLDT, startLDT);

                if (timeDifMonthStart == 0 && timeDifDayStart == 0 && (timeDifMinStart >= 0 && timeDifMinStart <= 15)) {
                    Appointments appointmentAlert = new Appointments(appointmentID);
                    appointmentAlerts.add(appointmentAlert);
                }
            }
        }
        catch (Exception e ) {
            System.out.println(e.getMessage());
        }
        return appointmentAlerts;
    }


    public static ObservableList<Appointments> initalizeAppts() throws SQLException {
        allAppts.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = (conn.prepareStatement("SELECT * FROM appointments;"))) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
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

    public static ObservableList<Appointments> initializeWeeklyAppts() throws SQLException{
        allAppts.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = (conn.prepareStatement("SELECT * FROM appointments WHERE start;"))) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
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

                //Ensures only appointments within 7 days will be added to the table
                long timeDifDaysStart = ChronoUnit.DAYS.between(Start, LocalDateTime.now());
                long timeDifDaysEnd = ChronoUnit.DAYS.between(End, LocalDateTime.now());
                long intervalStart= timeDifDaysStart;
                long intervalEnd = timeDifDaysEnd;

                System.out.println(Appointment_ID + " time difference days " + timeDifDaysStart);
                if(intervalStart < -7 || intervalStart > 0) {
                    continue;
                }

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

    public static ObservableList<Appointments> initializeMonthlyAppts() throws SQLException{
        allAppts.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = (conn.prepareStatement("SELECT * FROM appointments WHERE start;"))) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
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

                //Ensures only appointments within 7 days will be added to the table
                long timeDifMonthStart = ChronoUnit.MONTHS.between(Start, LocalDateTime.now());
                long timeDifMonthEnd = ChronoUnit.MONTHS.between(End, LocalDateTime.now());
                long timeDifDaysStart = ChronoUnit.DAYS.between(Start, LocalDateTime.now());
                long timeDifDaysEnd = ChronoUnit.DAYS.between(End, LocalDateTime.now());

                long intervalStart= timeDifMonthStart;
                long intervalEnd = timeDifMonthEnd;

                //timeDifMonthStart must be 0 to add
                //timedifDatsStart must be <= -31 and <= 0
                if(timeDifMonthStart !=0 || timeDifDaysStart < -31 || timeDifDaysStart > 0) {
                    continue;
                }


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

    public static ObservableList<Appointments> initializePastAppts() throws SQLException{
        allAppts.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = (conn.prepareStatement("SELECT * FROM appointments WHERE start;"))) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
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

                //Ensures only appointments within 7 days will be added to the table
                long timeDifSecStart = ChronoUnit.SECONDS.between(Start, LocalDateTime.now());
                long timeDifSecEnd = ChronoUnit.SECONDS.between(End, LocalDateTime.now());

                long intervalStart= timeDifSecStart;
                long intervalEnd = timeDifSecEnd;

                if(intervalEnd < 0) {
                    continue;
                }


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
        try(PreparedStatement ps = (conn.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?"))) {
            ps.setString(1, String.valueOf(apptToDelete.getAppointment_ID()));

            ps.executeUpdate();

            System.out.println("Deleted: " + ps.getUpdateCount() + " rows.");
            allAppts.remove(apptToDelete);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean appointmentWithinFifteenMin() {
        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = (conn.prepareStatement("SELECT Start FROM appointments"))) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocalDateTime startLDT = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime currentLDT = LocalDateTime.now();

                //use chronounit to determine time between the LCD
                long timeDifMonthStart = ChronoUnit.MONTHS.between(currentLDT, startLDT);
                long timeDifDayStart = ChronoUnit.DAYS.between(currentLDT, startLDT);
                long timeDifMinStart = ChronoUnit.MINUTES.between(currentLDT, startLDT);

                if (timeDifMonthStart == 0 && timeDifDayStart == 0 && (timeDifMinStart >= 0 && timeDifMinStart <= 15)) {
                    return true;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //If after iterating through all appointment start times and there are no times within 15 minutes, the method
        //returns false, meaning there are no appointments to display on the main menu.
        return false;
    }

    //Create boolean method to determine if the customer has an appointment at that selected time.

    public static boolean appointmentOverlap(LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customer_ID, int appointment_ID) {
        //Sql statment to select all appointments with that customers id, iterate through, determine if the time works
        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointments WHERE Customer_ID = ? AND Appointment_ID <> ?")) {
            ps.setInt(1, customer_ID);
            ps.setInt(2, appointment_ID);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                LocalDateTime setApptStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime setApptEnd = rs.getTimestamp("End").toLocalDateTime();

                if(appointmentStart.isEqual(setApptStart) || (appointmentStart.isAfter(setApptStart) && appointmentStart.isBefore(setApptEnd))) {
                    System.out.println("Appointment is in between another appointment for the selected customer");
                    return true;
                }
                else if(appointmentEnd.equals(setApptEnd) || (((appointmentEnd.isAfter(setApptStart) ||
                        appointmentEnd.equals(setApptEnd)) && (appointmentEnd.isBefore(setApptEnd) ||
                        appointmentEnd.equals(setApptEnd))))) {
                    System.out.println("Appointment end in between another appointment.");
                    return true;
                }
                else if((setApptStart.isAfter(appointmentStart) || setApptStart.isEqual(appointmentStart)) && (setApptEnd.isBefore(appointmentEnd) || setApptEnd.isEqual(appointmentEnd))) {
                    return true;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean appointmentOverlap(LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customer_ID) {
        //Sql statment to select all appointments with that customers id, iterate through, determine if the time works
        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointments WHERE Customer_ID = ?")) {
            ps.setInt(1, customer_ID);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                LocalDateTime setApptStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime setApptEnd = rs.getTimestamp("End").toLocalDateTime();

                if(appointmentStart.isEqual(setApptStart) || (appointmentStart.isAfter(setApptStart) && appointmentStart.isBefore(setApptEnd))) {
                    System.out.println("Appointment is in between another appointment for the selected customer");
                    return true;
                }
                else if(appointmentEnd.equals(setApptEnd) || (((appointmentEnd.isAfter(setApptStart) ||
                        appointmentEnd.equals(setApptEnd)) && (appointmentEnd.isBefore(setApptEnd) ||
                        appointmentEnd.equals(setApptEnd))))) {
                    System.out.println("Appointment end in between another appointment.");
                    return true;
                }
                else if((setApptStart.isAfter(appointmentStart) || setApptStart.isEqual(appointmentStart)) && (setApptEnd.isBefore(appointmentEnd) || setApptEnd.isEqual(appointmentEnd))) {
                    return true;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
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

    public int getAppointmentIDForAlert() {
        return appointmentIDForAlert;
    }

    public void setAppointmentIDForAlert(int appointmentIDForAlert) {
        this.appointmentIDForAlert = appointmentIDForAlert;
    }
}
