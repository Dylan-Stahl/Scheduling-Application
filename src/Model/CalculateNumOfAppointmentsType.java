package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.Month;

public class CalculateNumOfAppointmentsType {
    private static ObservableList<CalculateNumOfAppointmentsType> typeAndNum = FXCollections.observableArrayList();
    private String type;
    private int numOfType;


    public CalculateNumOfAppointmentsType(String type, int numOfType) {
        this.type = type;
        this.numOfType = numOfType;
    }

    public static ObservableList<CalculateNumOfAppointmentsType> initializeTypeAndNum() {
        typeAndNum.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT Type FROM appointments;")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String type = rs.getString("Type");

                int counter = 0;
                //loop through observable list, if type == the same as an element in there continue
                //if the type is already in there, the numOfType is already initialized to 0;
                for(CalculateNumOfAppointmentsType typeAdded : typeAndNum) {
                    if(type.equals(typeAdded.type)) {
                        typeAdded.numOfType +=1;
                        counter +=1;
                        continue;
                    }
                }
                if(counter > 0 ) {
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
