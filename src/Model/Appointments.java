package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

/**
 * The Appointments class is used to read data from the MySQL Database and display that data
 * in the JavaFX Application.
 */
public class Appointments {
    /**
     * ObservableList that holds all the appointments located in the database.
     */
    private static ObservableList<Appointments> allAppts = FXCollections.observableArrayList();

    /**
     * ObservableList that will hold appointments within fifteen minutes. This list will be used to set the items
     * of the Appointments within 15 minutes table located in the main menu.
     */
    private static ObservableList<Appointments> appointmentAlerts = FXCollections.observableArrayList();

    //Field used for the second constructor
    private int appointmentIDForAlert;

    //Fields used for the first constructor
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

    /**
     * The primary constructor for the Appointments class. The parameters are data that is provided from the
     * MySQL database.
     * @param appointment_ID holds the appointment ID of the appointment object
     * @param title holds the title of the appointment
     * @param description holds the description of the appointment
     * @param location holds the location of the appointment
     * @param type holds the type of the appointment
     * @param start holds the LocalDateTime of the appointment starting time
     * @param end holds the LocalDateTime of the appointment ending time
     * @param create_Date holds the Date of when the appointment was created
     * @param created_By holds the data regarding who created the appointment
     * @param last_Update holds the Timestamp of when the appointment was last updated/modified
     * @param last_Updated_By holds the user who updated the appointment last
     * @param customer_ID saves the customer ID of whom the appointment is with
     * @param user_ID holds the user id of the appointment
     * @param contact_ID saves the contact ID of whom the appointment is with
     */
    public Appointments(int appointment_ID, String title, String description, String location, String type,
                        LocalDateTime start, LocalDateTime end, Date create_Date, String created_By,
                        Timestamp last_Update, String last_Updated_By, int customer_ID, int user_ID, int contact_ID) {
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

    /**
     * The second constructor for the Appointments class is used to initialize the Appointment Alert Table.
     * @param appointmentIDForAlert The appointment alert table only displays the Appointment ID
     *                              and for that reason, only the appointment ID is needed as a constructor.
     */
    public Appointments(int appointmentIDForAlert) {
        this.appointmentIDForAlert = appointmentIDForAlert;
    }

    /**
     * When the main menu is opened, this method provided the appointments that are within 15 minuted of the
     * users LocalDateTime.
     * @return the appointmentAlerts ObservableList that holds the appointments within 15 minutes of the
     *         users LocalDateTime.
     */
    public static ObservableList<Appointments> initializeApptAlertTable() {
        //Every time the method is called, the ObservableList is recreated to show accurate data.
        //Without clearing the ObservableList, the data in the ObservableList would have incorrect results.
        appointmentAlerts.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointments;")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                LocalDateTime startLDT = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime currentLDT = LocalDateTime.now();
                int appointmentID = rs.getInt("Appointment_ID");

                //Determine time between the current time and the start time of the appointment.
                long timeDifMonthStart = ChronoUnit.MONTHS.between(currentLDT, startLDT);
                long timeDifDayStart = ChronoUnit.DAYS.between(currentLDT, startLDT);
                long timeDifMinStart = ChronoUnit.MINUTES.between(currentLDT, startLDT);

                //If the appointment is within 15 minutes of the LocalDateTime, the appointment is added to the
                //ObservableList. This list is then used to fill in the TableView in the main menu.
                if (timeDifMonthStart == 0 && timeDifDayStart == 0 && (timeDifMinStart >= 0 && timeDifMinStart <= 15)) {
                    Appointments appointmentAlert = new Appointments(appointmentID);
                    appointmentAlerts.add(appointmentAlert);
                }
            }
            rs.close();
        }
        catch (Exception e ) {
            System.out.println(e.getMessage());
        }
        return appointmentAlerts;
    }

    /**
     * When an appointments table view is shown in the program, the table set it's items by calling this method to return
     * the ObservableList with all the appointments in the database.
     * @return the ObservableList with the appointments in the database.
     */
    public static ObservableList<Appointments> initializeAppts() {
        //Every time the method is called, the ObservableList is recreated to show accurate data.
        //Without clearing the ObservableList, the data in the ObservableList would have incorrect results.
        allAppts.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = (conn.prepareStatement("SELECT * FROM appointments;"))) {
            ResultSet rs = ps.executeQuery();

            //Collect data from each appointment.
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

                //Creates a new Appointments object of the information provided from the database.
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

    /**
     * The appointmentsView menu displays an option to show appointments within the next week. The table view
     * calls this method to show that data.
     * @return the ObservableList with appointments within the next week of the users current LocalDateTime.
     */
    public static ObservableList<Appointments> initializeWeeklyAppts() {
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
                long timeDifMinStart = ChronoUnit.MINUTES.between(Start, LocalDateTime.now());

                if(intervalStart < -7 || intervalStart > 0 || ((intervalStart) == 0 && (timeDifMinStart > 0))) {
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

    /**
     * The appointmentsView menu displays an option to show appointments within the next month. The table view
     * calls this method to show that data.
     * @return the ObservableList with appointments within the next month of the users current LocalDateTime.
     */
    public static ObservableList<Appointments> initializeMonthlyAppts() {
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
                long timeDifMinStart = ChronoUnit.MINUTES.between(Start, LocalDateTime.now());

                long intervalStart= timeDifMonthStart;
                long intervalEnd = timeDifMonthEnd;

                //timeDifMonthStart must be 0 to add
                //timeDifDaysStart must be <= -31 and <= 0
                if(timeDifMonthStart !=0 || timeDifDaysStart < -31 || timeDifDaysStart > 0 || ((timeDifDaysStart) == 0 && (timeDifMinStart > 0))) {
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

    /**
     * The appointmentsView menu displays an option to show past appointments. The table view
     * calls this method to show that data.
     * @return the ObservableList with appointments that have already taken place..
     */
    public static ObservableList<Appointments> initializePastAppts() {
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

    /**
     * The main menu has a button to delete appointments. This method provides that functionality.
     * @param apptToDelete the selected appointment in the Appointments table view
     *                     that will be deleted.
     */
    public static void deleteAppt(Appointments apptToDelete) {
        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = (conn.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?"))) {
            ps.setString(1, String.valueOf(apptToDelete.getAppointment_ID()));

            ps.executeUpdate();

            allAppts.remove(apptToDelete);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Used by the lambda expression in the initialize() method in the mainMenuController.
     * @return true if an appointment is within 15 minutes of the users current LocalDataTime
     *                 and returns false if there are no appointments within 15 minutes.
     */
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

    /**
     * Used by the appointmentRecordsModifyController to determine if the current appointment being modified
     * will overlap any other appointments that the customer has besides the current appointment being changed.
     * @param appointmentStart  used to compare with appointments in the database.
     * @param appointmentEnd    used to compare with appointments in the database.
     * @param customer_ID   used in the SQL statement to find all appointments with that specified customer.
     * @param appointment_ID    provided that all appointments with the selected customer should be searched
     *                          except the appointment_ID of the current appointment being modified
     * @return true if the appointment being modified can't be added due to another appointment with the same
     *         customer that take place sometime in between. Returns false is there is no overlap in appointments
     */
    public static boolean appointmentOverlap(LocalDateTime appointmentStart, LocalDateTime appointmentEnd,
                                             int customer_ID, int appointment_ID) {
        //Sql statement to select all appointments with that customers id, iterate through, determine if the time works
        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointments WHERE Customer_ID = ? AND " +
                "Appointment_ID <> ?")) {
            ps.setInt(1, customer_ID);
            ps.setInt(2, appointment_ID);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                LocalDateTime setApptStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime setApptEnd = rs.getTimestamp("End").toLocalDateTime();

                if(appointmentStart.isEqual(setApptStart) || (appointmentStart.isAfter(setApptStart) && appointmentStart.isBefore(setApptEnd))) {
                    //Appointment is in between another appointment for the selected customer
                    return true;
                }
                else if(appointmentEnd.equals(setApptEnd) || (((appointmentEnd.isAfter(setApptStart) ||
                        appointmentEnd.equals(setApptEnd)) && (appointmentEnd.isBefore(setApptEnd) ||
                        appointmentEnd.equals(setApptEnd))))) {
                    //Appointment end is in between another appointment with that customer.
                    return true;
                }
                else if((setApptStart.isAfter(appointmentStart) || setApptStart.isEqual(appointmentStart)) && (setApptEnd.isBefore(appointmentEnd) || setApptEnd.isEqual(appointmentEnd))) {
                    //Determine if the current appointment being iterated through in the sql databased has overlap with
                    //the appointment being modified.
                    return true;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Used by the appointmentRecordsAddController to determine if the current appointment being added
     * will overlap any other appointments that the customer has besides the current appointment being added.
     * @param appointmentStart used to compare with appointments in the database.
     * @param appointmentEnd used to compare with appointments in the database.
     * @param customer_ID used in the SQL statement to find all appointments with that specified customer.
     * @return true if the appointment being added can't be added due to another appointment with the same
     *         customer that take place sometime in between. Returns false is there is no overlap in appointments.
     */
    public static boolean appointmentOverlap(LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customer_ID) {
        //Sql statement to select all appointments with that customers id, iterate through, determine if the time works.
        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointments WHERE Customer_ID = ?")) {
            ps.setInt(1, customer_ID);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                LocalDateTime setApptStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime setApptEnd = rs.getTimestamp("End").toLocalDateTime();

                if(appointmentStart.isEqual(setApptStart) || (appointmentStart.isAfter(setApptStart) && appointmentStart.isBefore(setApptEnd))) {
                    //Appointment is in between another appointment for the selected customer
                    return true;
                }
                else if(appointmentEnd.equals(setApptEnd) || (((appointmentEnd.isAfter(setApptStart) ||
                        appointmentEnd.equals(setApptEnd)) && (appointmentEnd.isBefore(setApptEnd) ||
                        appointmentEnd.equals(setApptEnd))))) {
                    //Appointment end is in between another appointment with that customer.
                    return true;
                }
                else if((setApptStart.isAfter(appointmentStart) || setApptStart.isEqual(appointmentStart)) && (setApptEnd.isBefore(appointmentEnd) || setApptEnd.isEqual(appointmentEnd))) {
                    //Determine if the current appointment being iterated through in the sql databased has overlap with
                    //the appointment being modified.
                    return true;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //Getters and Setters for fields.

    /**
     * Getter for the allAppts ObservableList.
     * @return ObservableList allAppts
     */
    public static ObservableList<Appointments> returnAllAppts() {
        return allAppts;
    }

    /**
     * Getter for Appointment_ID.
     * @return appointment's appointment ID
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }

    /**
     * Setter for Appointment_ID, not used. Database automatically creates appointment IDs.
     * @param appointment_ID sets the appointment ID to whatever this is.
     */
    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    /**
     * Getter for appointment title.
     * @return appointment's title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Setter for appointment title.
     * @param title sets the appointment's title as this argument
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Getter for appointment description.
     * @return appointment's description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Setter for appointment description.
     * @param description sets the appointment's description as this argument
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Getter for appointment location.
     * @return appointment's location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * Setter for the appointment location.
     * @param location sets appointment's location
     */
    public void setLocation(String location) {
        Location = location;
    }

    /**
     * Getter for the appointment type.
     * @return appointment's type
     */
    public String getType() {
        return Type;
    }

    /**
     * Setter for the appointment type.
     * @param type sets appointments type
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * Getter for the appointment start LocalDateTime.
     * @return appointment's start LocalDateTime
     */
    public LocalDateTime getStart() {
        return Start;
    }

    /**
     * Setter for the appointment start LocalDateTime.
     * @param start sets appointment's start as this
     */
    public void setStart(LocalDateTime start) {
        Start = start;
    }

    /**
     * Getter for the appointment end LocalDateTime.
     * @return appointments end time
     */
    public LocalDateTime getEnd() {
        return End;
    }

    /**
     * Setter for the appointment end LocalDateTime.
     * @param end sets appointments end time
     */
    public void setEnd(LocalDateTime end) {
        End = end;
    }

    /**
     * Getter for the appointment create date.
     * @return appointment's create date
     */
    public Date getCreate_Date() {
        return Create_Date;
    }

    /**
     * Setter for the appointment create date.
     * @param create_Date sets appointments create date
     */
    public void setCreate_Date(Date create_Date) {
        Create_Date = create_Date;
    }

    /**
     * Getter for returning who created the appointment.
     * @return who created the appointment
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Setter for setting who created the appointment.
     * @param created_By sets who created the appointment
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * Getter for returning the timestamp of the last update.
     * @return appointments Timestamp of last update
     */
    public Timestamp getLast_Update() {
        return Last_Update;
    }

    /**
     * Setter for the last update.
     * @param last_Update sets appointments last update
     */
    public void setLast_Update(Timestamp last_Update) {
        Last_Update = last_Update;
    }

    /**
     * Getter for returning who last updated an appointment.
     * @return who updated the appointment last
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * Setter for who last updated an appointment.
     * @param last_Updated_By sets the field to who updated the appointment last
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * Getter for the customer ID.
     * @return appointment's customer ID
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * Setter for the customer ID.
     * @param customer_ID sets appointments customer ID
     */
    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    /**
     * Getter for the user ID.
     * @return appointments user ID
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * Setter for the user ID.
     * @param user_ID sets appointment's user ID
     */
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }


    /**
     * Getter for the appointment contact ID
     * @return appointment's contact ID
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * Setter for the appointment contact ID.
     * @param contact_ID sets appointments contact ID
     */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    /**
     * Getter for the appointmentIDForAlert.
     * @return the appointment ID in which is in less than 15 minutes
     */
    public int getAppointmentIDForAlert() {
        return appointmentIDForAlert;
    }

    /**
     * Setter for the appointmentIDForAlert.
     * @param appointmentIDForAlert sets the appointment id for which is in less than 15 minutes
     */
    public void setAppointmentIDForAlert(int appointmentIDForAlert) {
        this.appointmentIDForAlert = appointmentIDForAlert;
    }
}
