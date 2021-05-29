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
 * The CalculateNumOfAppointmentsMonth class is used to store objects in the monthAndNum ObservableList. The
 * monthAndNum ObservableList is used in the monthAndNum table view.
 */
public class CalculateNumOfAppointmentsMonth {
    /**
     * ObservableList that holds the number of appointments per month. If there are no appointments in a specific month,
     * then that month is not included in the list.
     */
    private static ObservableList<CalculateNumOfAppointmentsMonth> monthAndNum = FXCollections.observableArrayList();

    private Month month;
    private int numPerMonth;

    /**
     * Constructor for number of appointment in each month that has at least one appointment.
     * @param month month name in which the number of appointments will be associated with
     * @param numPerMonth number of appointments in that month.
     */
    public CalculateNumOfAppointmentsMonth(Month month, int numPerMonth) {
        this.month = month;
        this.numPerMonth = numPerMonth;
    }

    /**
     * Initializes the Number of Appointments (Month) table in the Number of Appointments menu in the main menu.
     * @return the ObservableList with the CalculateNumOfAppointmentsMonth objects to be displayed.
     */
    public static ObservableList<CalculateNumOfAppointmentsMonth> initializeMonthAndNum() {
        monthAndNum.clear();
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement("SELECT Start FROM appointments;")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                Month apptMonth = start.getMonth();

                int monthCounter = 0;
                for (CalculateNumOfAppointmentsMonth typeAdded : monthAndNum) {
                    if (apptMonth.equals(typeAdded.month)) {
                        typeAdded.numPerMonth += 1;
                        monthCounter +=1;
                        continue;
                    }
                }

                if(monthCounter > 0) {
                    continue;
                }
                CalculateNumOfAppointmentsMonth newMonth = new CalculateNumOfAppointmentsMonth(apptMonth, 1);
                monthAndNum.add(newMonth);
            }
            rs.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return monthAndNum;
    }

    //Setters and getters

    /**
     * Getter for the month field.
     * @return month of the object.
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Setter for the month field.
     * @param month sets the month for which the number of appointments will be associated with
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * Getter for the numPerMonth field.
     * @return number of appointments for a month
     */
    public int getNumPerMonth() {
        return numPerMonth;
    }

    /**
     * Setter for the numPerMonth field.
     * @param numPerMonth sets the number of appointment associated with a month
     */
    public void setNumPerMonth(int numPerMonth) {
        this.numPerMonth = numPerMonth;
    }

}
