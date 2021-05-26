package Model;

import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.Month;

public class CalculateNumOfAppointmentsMonth {
    private static ObservableList<CalculateNumOfAppointmentsMonth> monthAndNum = FXCollections.observableArrayList();
    private Month month;
    private int numPerMonth;

    public CalculateNumOfAppointmentsMonth(Month month, int numPerMonth) {
        this.month = month;
        this.numPerMonth = numPerMonth;
    }

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
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return monthAndNum;
    }

    //Month setters and getters
    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getNumPerMonth() {
        return numPerMonth;
    }

    public void setNumPerMonth(int numPerMonth) {
        this.numPerMonth = numPerMonth;
    }

}
