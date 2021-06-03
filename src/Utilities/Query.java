package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * This class creates statements and prepared statements for queries to the database
 * @author adamb
 */
public class Query
{
    public static Statement statement; // Statement reference

    /**
     * setter for Statement objects
     * @param conn Connection
     * @throws SQLException if there is an error querying the database
     */
    public static void  setStatement(Connection conn) throws SQLException
    {
        statement = conn.createStatement();
    }

    // Return Statement object

    /**
     * getter for Statement objects
     * @return Statement
     */
    public static Statement getStatement()
    {
        return statement;
    }

    public static PreparedStatement preparedStatement; // PreparedStatement reference

     /**
     * Setter for PreparedStatement objects
     * @param conn Connection
     * @param sqlStatement String SQLStatement
     * @throws SQLException if there is an error querying the database
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);
    }

    /**
     * getter for PreparedStatement
     * @return PreparedStatement
     */
    public static PreparedStatement getPreparedStatement()
    {
        return preparedStatement;
    }

}
