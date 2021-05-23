package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Division {
    private static ObservableList<Division> divisions = FXCollections.observableArrayList();
    private String divisionName;

    public Division(String divisionName) {
        this.divisionName = divisionName;
    }

    public static ObservableList<Division> initializeAllDivisions() {
        divisions.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT Division FROM first_level_divisions")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String divisionName = rs.getString("Division");

                Division newDivision = new Division(divisionName);
                divisions.add(newDivision);
            }
            rs.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        return divisions;
    }

    public static int getDivisionID(String divisionName) {
        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM first_level_divisions WHERE " +
                "Division = ?;")) {
            ps.setString(1, divisionName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                return divisionID;
            }
            rs.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        //Returning 0 will never be executed.
        return 0;
    }

    @Override
    public String toString() {
        return (divisionName);
    }
}
