package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class establishes a connection to the database
 * @author adamb
 */
public class dbconnection
{
    // JDBC URL strings
    private static final String protocol = "jdbc";
    private static final String venderName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ083SW";
    // JDBC URL
    private static final String jdbcUrl = protocol + venderName + ipAddress + dbName+ "?connectionTimeZone = SERVER";
    // driver and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    private static final String username = "U083SW";
    private static final String password = "53689203638";

    /**
     * method starts a connection with the database driver
     * @return connection
     * @throws ClassNotFoundException if no definition for specified class is found
     * @throws SQLException if there is an error querying the database
     */
    public static Connection startConnection()
    {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection opened successfully");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            //System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return conn;
    }

    /**
     * to provide the connection in preparedStatements
     * @return connection
     */
    public static Connection getConnection()
    {
        return conn;
    }

    /**
     * CLose connection method
     * This is to prevent resource leaks
     * @throws Exception in the event of a race condition if connection already closed
     */
    public static void closeConnection()
    {
        try
        {
            conn.close();
            System.out.println("Connection closed successfully");
        }
        catch (Exception e)
        {
            // Do nothing because program would be closing
        }
    }



}
