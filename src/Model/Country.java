package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The country class stores country names and has methods that find data regarding countries in the database.
 */
public class Country {
    private static ObservableList<Country> countries = FXCollections.observableArrayList();
    private String countryName;

    /**
     * Country constructor
     * @param countryName saves the country name in the country object
     */
    public Country(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Method returns division name based on the division id
     * @param division_ID used in the SQL statement.
     * @return returns division name.
     */
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

    /**
     * Method finds country id based on a division id.
     * @param division_ID
     * @return countryID is returned
     */
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
        //Selects element 0 in the array list because only one first level division will be in the SQL result.
        return  countryResultsID.get(0);
    }

    /**
     * Method finds country ID based on the countryName. The method searches the databases table countries and the
     * column Country_ID. It then returns the Country_ID from the result.
     * @param countryName
     * @return countryID is returned.
     */
    public static int returnCountryID(String countryName) {
        ArrayList<Integer> countryResultsID = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement("SELECT Country_ID FROM countries WHERE Country =" +
                " ?;")) {
            ps.setString(1, countryName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryID = rs.getInt("Country_ID");
                countryResultsID.add(countryID);
            }
            rs.close();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Selects element 0 in the array list because only one Country ID will be in the SQL result.
        return  countryResultsID.get(0);
    }

    /**
     * Returns country name based on a country ID.
     * @param country_ID
     * @return
     */
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

    /**
     * Used by the customer add and modify menu combo boxes to set the items within them. The method returns an
     * ObservableList with Country objects that contain the countries names.
     * @return
     */
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

    /**
     * Used to format the combo box for country selection.
     * @return Combo boxes will display the countryName.
     */
    @Override
    public String toString() {
        return (countryName);
    }
}
