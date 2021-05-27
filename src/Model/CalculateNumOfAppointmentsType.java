package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * The CalculateNumOfAppointmentsType class is used to store objects in the typeAndNum ObservableList. This
 * ObservableList is used to set the items in the Number of Appointments (Type) table view
 */
public class CalculateNumOfAppointmentsType {
    private static ObservableList<CalculateNumOfAppointmentsType> typeAndNum = FXCollections.observableArrayList();
    private String type;
    private int numOfType;

    /**
     * Constructor, stores CalculateNumOfAppointmentsType objects in the ObservableList typeAndNum.
     * @param type
     * @param numOfType
     */
    public CalculateNumOfAppointmentsType(String type, int numOfType) {
        this.type = type;
        this.numOfType = numOfType;
    }

    /**
     * Initializes the Number of Appointments (Type) table in the Number of Appointments menu in the main menu.
     * @return returns the ObservableList with the CalculateNumOfAppointmentsType objects to be displayed.
     */
    public static ObservableList<CalculateNumOfAppointmentsType> initializeTypeAndNum() {
        typeAndNum.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT Type FROM appointments;")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String type = rs.getString("Type");

                int counter = 0;
                //loop through observable list, if type is the same as an element already in the ObservableList the
                //numOfType field in the already created object is tallied up
                for(CalculateNumOfAppointmentsType typeAdded : typeAndNum) {
                    if(type.equals(typeAdded.type)) {
                        typeAdded.numOfType +=1;
                        counter +=1;
                        continue;
                    }
                }
                if(counter > 0 ) {
                    //If the type already existed, don't create a new object, just add add 1 to numOfType and move to
                    //the next Appointment (continue)
                    continue;
                }

                CalculateNumOfAppointmentsType newType = new CalculateNumOfAppointmentsType(type, 1);
                typeAndNum.add(newType);
            }
            rs.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return typeAndNum;
    }

    //Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumOfType() {
        return numOfType;
    }

    public void setNumOfType(int numOfType) {
        this.numOfType = numOfType;
    }


}
