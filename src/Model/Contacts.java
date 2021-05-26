package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Contacts {
    private static ObservableList<Contacts> contacts = FXCollections.observableArrayList();
    private static ObservableList<Contacts> contactSchedule = FXCollections.observableArrayList();


    private int contactID;
    private String contactName;

    //Second Constructor variables
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentType;
    private String appointmentDescription;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;
    private int customerID;

    public Contacts(String contactName, int contactID) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public Contacts(int appointmentID, String appointmentTitle, String appointmentType, String appointmentDescription,
                    LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentType = appointmentType;
        this.appointmentDescription = appointmentDescription;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.customerID = customerID;
    }

    public static ObservableList<Contacts> initializeContactSchedule(int contactID) {
        contactSchedule.clear();

        Connection conn = DBConnection.getConnection();
        String sqlSelect = "SELECT * FROM appointments WHERE Contact_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentType = rs.getString("Type");
                String appointmentDescription = rs.getString("Description");
                LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");

                Contacts contactScheduleDisplay = new Contacts(appointmentID, appointmentTitle, appointmentType,
                        appointmentDescription, appointmentStart, appointmentEnd, customerID);
                contactSchedule.add(contactScheduleDisplay);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contactSchedule;
    }


    public static ObservableList<Contacts> initializeContacts() {
        contacts.clear();

        Connection conn = DBConnection.getConnection();
        String sqlSelectAll = "SELECT * FROM contacts";
        try (PreparedStatement ps = conn.prepareStatement(sqlSelectAll)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String contactName = rs.getString("Contact_Name");
                int contactID = rs.getInt("Contact_ID");

                Contacts newContact = new Contacts(contactName, contactID);
                contacts.add(newContact);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        return contacts;
    }

    public static int returnContactID(Contacts contact) {
        return contact.contactID;
    }

    public static Contacts returnContact(Appointments appointment) {
        for (Contacts contact : contacts) {
            if (appointment.getContact_ID() == contact.contactID) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return (contactName);
    }


    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
