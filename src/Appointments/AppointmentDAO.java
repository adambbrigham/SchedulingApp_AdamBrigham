package Appointments;

import Utilities.Query;
import Utilities.dbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 *
 * This class makes queries to the database
 * This class handles all of the CRUD operations for Appointments with the database
 * @author adamb
 *
 */
public class AppointmentDAO
{
    static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * This method gets all appointments from the database
     * A select query gets all appointments and then they are added to an ObservableArrayList
     * @throws SQLException if there is an error querying the database
     */
    public static void addAllAppointments() throws SQLException{
        Connection con = dbconnection.getConnection();
        try {
            Query.setStatement(con);

        Statement statement = Query.getStatement();

        String selectStatement = "SELECT * FROM appointments;";
        statement.execute(selectStatement);

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next())
        {
            int id = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");

            Appointment appointment = new Appointment(id, title,description,location,type,start,end,customerID,userID,contactID);

            allAppointments.add(appointment);

        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * gets all appointments from ObservableArrayList
     * @return ObservableList all appointments
     */
    public static ObservableList<Appointment> getAllAppointments()
    {
        return allAppointments;
    }

    /**
     * Add an appointment to the database
     *
     * @param id int ID
     * @param title String title
     * @param description String description
     * @param location String location
     * @param type String type
     * @param start LocalDateTime start
     * @param end LocalDateTime end
     * @param customerID int customerID
     * @param userID int userID
     * @param contactID int contactID
     * @throws SQLException if there is an error querying the database
     */
    public static void addAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
        try {
            Connection con = dbconnection.getConnection();
            String insertStatement = "INSERT INTO appointments(Appointment_ID,Title,Description,Location,Type,Start,End,Customer_ID,User_ID,Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";

            Query.setPreparedStatement(con, insertStatement);

            PreparedStatement ps = Query.getPreparedStatement();

            // key-value mapping
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, Timestamp.valueOf(start));
            ps.setTimestamp(7, Timestamp.valueOf(end));
            ps.setInt(8,customerID);
            ps.setInt(9,userID);
            ps.setInt(10,contactID);

            ps.execute();

            // Check rows affected
            if (ps.getUpdateCount() > 0)
                System.out.println("Rows affected: " + ps.getUpdateCount());
            else
                System.out.println("no change");
        }
        catch (SQLException e)
        {
            e.getStackTrace();
        }
    }

    /**
     * Deletes an appointment from the database
     * @param appointmentID int AppointmentID for which appointment is to be deleted
     * @throws SQLException if there is an error querying the database
     */
    public static void deleteAppointment(int appointmentID) throws SQLException {
        Connection con = dbconnection.getConnection();
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

        Query.setPreparedStatement(con, deleteStatement);

        PreparedStatement ps = Query.getPreparedStatement();

        ps.setInt(1,appointmentID);

        ps.execute();

        if (ps.getUpdateCount() > 0)
            System.out.println("Rows affected: " + ps.getUpdateCount());
        else
            System.out.println("no change");
    }

    /**
     * Updates an appointment in the database
     * New fields for updated appointment are passed from UpdateAppointment screen
     * User is not updated due to constraints and should not change
     * @param appointmentID int appointmentID
     * @param title String title
     * @param description String description
     * @param location String location
     * @param type String type
     * @param start LocalDateTime start
     * @param end LocalDateTime end
     * @param customerID int customerID
     * @param contactID int contactID
     */
    public static void updateAppointment(int appointmentID,String title, String description, String location, String type, LocalDateTime start,
            LocalDateTime end, int customerID, int contactID)
    {
        Connection con = dbconnection.getConnection();
        String updateStatement = "UPDATE appointments SET " +
                "Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, Contact_ID=? " +
                "WHERE Appointment_ID = ?";


        try {
            Query.setPreparedStatement(con, updateStatement);
            PreparedStatement ps = Query.getPreparedStatement();

            // key-value mapping
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7,customerID);
            ps.setInt(8,contactID);
            ps.setInt(9,appointmentID);

            ps.execute();

            if (ps.getUpdateCount() > 0)
                System.out.println("Rows affected: " + ps.getUpdateCount());
            else
                System.out.println("no change");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}


