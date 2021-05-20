package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contacts {
    private static ObservableList<Contacts> contacts = FXCollections.observableArrayList();
    private int contactID;
    private String contactName;

    public Contacts(String contactName, int contactID) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public static ObservableList<Contacts> initializeContacts()  {
        contacts.clear();

        Connection conn = DBConnection.getConnection();
        String sqlSelectAll = "SELECT * FROM contacts";
        try(PreparedStatement ps = conn.prepareStatement(sqlSelectAll)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String contactName = rs.getString("Contact_Name");
                int contactID = rs.getInt("Contact_ID");

                Contacts newContact = new Contacts(contactName, contactID);
                contacts.add(newContact);
            }
            rs.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        return contacts;
    }

    public static int returnContactID(Contacts contact) {
        return contact.contactID;
    }

    public static Contacts returnContact(Appointments appointment) {
        for(Contacts contact : contacts) {
            if(appointment.getContact_ID() == contact.contactID) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return (contactName);
    }
}
