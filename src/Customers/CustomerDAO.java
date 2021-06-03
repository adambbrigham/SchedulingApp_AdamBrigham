package Customers;

import Utilities.Query;
import Utilities.dbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class makes queries to the database
 * This class handles all of the CRUD operations for Customers with the database
 */
public class CustomerDAO
{
    static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * gets all customers from the database
     * Select query to get customers from database and add them to an ObservableArrayList
     * @throws SQLException if there is an error querying the database
     */
    public static void addAllCustomers() throws SQLException {

        Connection con = dbconnection.getConnection(); // get connection
        Query.setStatement(con);
        Statement statement = Query.getStatement();

        String selectStatement = "SELECT * FROM customers;"; // SELECT statement
        statement.execute(selectStatement);  // execute statement

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next())
        {
            int id = resultSet.getInt("Customer_ID");
            String name = resultSet.getString("Customer_Name");
            String address = resultSet.getString("Address");
            String postalCode = resultSet.getString("Postal_Code");
            String phone = resultSet.getString("Phone");
            LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();
            String createBy = resultSet.getString("Created_By");
            LocalTime lastUpdate = resultSet.getTime("Last_Update").toLocalTime();
            String lastUpdateBy = resultSet.getString("Last_Updated_By");
            int division = resultSet.getInt("Division_ID");

            Customer customer = new Customer(id,name,address,postalCode,phone,division);

            allCustomers.add(customer);

        }
    }

    /**
     * gets all Customers from ObservableArrayList
     * @return ObservableList of all customers
     */
    public static ObservableList<Customer> getAllCustomers()
    {
        return allCustomers;
    }

    /**
     * Adds a customer to the database
     * @param id int customerID
     * @param name String customerName
     * @param address String customerAddress
     * @param postalCode String customerPostalCode
     * @param phone String customerPhone number
     * @param division int FirstLevelDivision
     * @throws SQLException if there is an error querying the database
     */
    public static void addCustomer(int id, String name, String address, String postalCode, String phone, int division) throws SQLException {
        try {
            Connection con = dbconnection.getConnection();
            String insertStatement = "INSERT INTO customers(Customer_ID,Customer_Name,Address,Postal_Code,Phone,Division_ID) VALUES(?,?,?,?,?,?)";

            Query.setPreparedStatement(con, insertStatement);

            PreparedStatement ps = Query.getPreparedStatement();

            // key-value mapping
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setInt(6, division);

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
        };
    }

    /**
     * Deletes a customer from the database
     * customer to delete is determined by customerID
     * @param customerID int customerID
     * @throws SQLException if there is an error querying the database
     */
    public static void deleteCustomer(int customerID) throws SQLException{
        Connection con = dbconnection.getConnection();
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";

        try {
            Query.setPreparedStatement(con, deleteStatement);


            PreparedStatement ps = Query.getPreparedStatement();

            ps.setInt(1,customerID);

            ps.execute();

            if (ps.getUpdateCount() > 0)
                System.out.println("Rows affected: " + ps.getUpdateCount());
            else
                System.out.println("no change");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Updates the a customer in the database
     * customer to be updated is determined by customerID
     * @param customerID int customerID
     * @param name String customerName
     * @param address String customerAddress
     * @param postal String customerPostalCode
     * @param phone String customerPhone number
     * @param division int FirstLevelDivisionID
     * @throws SQLException if there is an error querying the database
     */
    public static void updateCustomer(int customerID,String name, String address, String postal, String phone, int division) throws SQLException{
        Connection con = dbconnection.getConnection();
        String updateStatement = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? " +
                "WHERE Customer_ID = ?";

        try {
            Query.setPreparedStatement(con, updateStatement);


            PreparedStatement ps = Query.getPreparedStatement();

            ps.setString(1,name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, division);
            ps.setInt(6,customerID);

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


