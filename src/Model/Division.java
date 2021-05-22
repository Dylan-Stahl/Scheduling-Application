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

    @Override
    public String toString() {
        return (divisionName);
    }
}
