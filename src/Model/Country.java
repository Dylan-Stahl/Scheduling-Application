package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Country {
    private static ObservableList<Country> countries = FXCollections.observableArrayList();
    private String countryName;

    public Country(String countryName) {
        this.countryName = countryName;
    }

    //FIXME add to division
    public static String returnDivision(int division_ID) {
        String Division_IDStr;

        ArrayList<String> divisionResultsString = new ArrayList();

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement divisionPS = conn.prepareStatement("SELECT * FROM first_level_divisions WHERE " +
                "Division_ID = ?;")) {
            divisionPS.setInt(1, division_ID);
            ResultSet divisionRS = divisionPS.executeQuery();

            while (divisionRS.next()) {
                Division_IDStr = divisionRS.getString("Division");

                divisionResultsString.add(Division_IDStr);
            }
            divisionRS.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        return divisionResultsString.get(0);
    }

    public static int returnCountryID(int division_ID) {
        ArrayList<Integer> countryResultsID = new ArrayList();

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement divisionPS = conn.prepareStatement("SELECT * FROM first_level_divisions WHERE " +
                "Division_ID = ?;")) {
            divisionPS.setInt(1, division_ID);
            ResultSet divisionRS = divisionPS.executeQuery();

            while (divisionRS.next()) {
                int country_ID = divisionRS.getInt("Country_ID");
                countryResultsID.add(country_ID);
            }
            divisionRS.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        return  countryResultsID.get(0);
    }

    public static String returnCountryString(int country_ID) {
        ArrayList<String> countryStr = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps1 = conn.prepareStatement("Select * FROM countries WHERE Country_ID " +
                "= ?;")) {
            //The sql statement above should have only added 1 country results id to the array list.
            ps1.setInt(1, country_ID);
            ResultSet countryRS = ps1.executeQuery();


            while(countryRS.next()) {
                String Country = countryRS.getString("Country");
                countryStr.add(Country);
            }
            countryRS.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        return countryStr.get(0);
    }

    public static ObservableList<Country> initializeAllCountries() {
        countries.clear();

        Connection conn = DBConnection.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("SELECT Country FROM countries")) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String countryName = rs.getString("Country");

                Country newCountry = new Country(countryName);
                countries.add(newCountry);
            }
            rs.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        return countries;
    }

    @Override
    public String toString() {
        return (countryName);
    }

}
