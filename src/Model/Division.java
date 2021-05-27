package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *Holds Division Data used for combo boxes.
 */
public class Division {
    private static ObservableList<Division> divisions = FXCollections.observableArrayList();
    private static ObservableList<Division> divisionsSortedByCountry = FXCollections.observableArrayList();
    private String divisionName;

    public Division(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Accesses the database and collects the names of the divisions and stores them in an ObservableList.
     * @return returns an ObservableList with all the first level divisions in the database.
     */
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

    /**
     * Only selects divisions within a specified country as the argument.
     * @param countryID needed to search database.
     * @return ObservableList with first level divisions within that country.
     */
    public static ObservableList<Division> initializeDivisionWithSetCountry(int countryID) {
        divisionsSortedByCountry.clear();

        Connection conn = DBConnection.getConnection();
        String sqlSelect = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String firstLevelDivisionToAdd = rs.getString("Division");

                Division newDivision= new Division(firstLevelDivisionToAdd);
                divisionsSortedByCountry.add(newDivision);
            }
            rs.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  divisionsSortedByCountry;
    }

    /**
     * Clears the ObservableList divisionsSortedByCountry so that the ObservableList can be used again with a differet
     * argument.
     */
    public static void removeDivisionsSortedByCountry() {
        divisionsSortedByCountry.clear();
    }

    /**
     * Returns division ID given a division name.
     * @param divisionName
     * @return
     */
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

    /**
     * Formats the combo box to display divisionName.
     * @return
     */
    @Override
    public String toString() {
        return (divisionName);
    }
}
