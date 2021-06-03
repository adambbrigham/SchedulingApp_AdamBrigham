package Contacts;

import Utilities.Query;
import Utilities.dbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for making queries to database for Contacts
 */
public class ContactDAO {
    static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * Gets all contacts from the database and adds them to an ObservableArrayList
     * @throws SQLException if there is an error querying the database
     */
    public static void addAddContacts() throws SQLException {
        Connection con = dbconnection.getConnection();
        Query.setStatement(con);
        Statement statement = Query.getStatement();

        String selectStatement = "SELECT * FROM contacts;";
        statement.execute(selectStatement);

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next())
        {
            int id = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String email = resultSet.getString("Email");

            Contact contact = new Contact(id,contactName,email);

            allContacts.add(contact);
        }
    }

    /**
     * gets all contacts from the ObservableArrayList
     * @return ObservableList of all contacts
     */
    public static ObservableList<Contact> getAllContacts()
    {
        return allContacts;
    }
}
