package Countries;

import Utilities.Query;
import Utilities.dbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * this class performs all queries to the database for Countries
 * @author adamb
 */
public class CountryDAO
{
    static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**
     * adds all Countries to an observableArrayList from the database
     * @throws SQLException if there is an error querying the database
     */
    public static void addAllCountries() throws SQLException {
        Connection con = dbconnection.getConnection();
        Query.setStatement(con);
        Statement statement = Query.getStatement();

        String selectStatement = "SELECT * FROM countries;";
        statement.execute(selectStatement);

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next())
        {
            int id = resultSet.getInt("Country_ID");
            String countryName = resultSet.getString("Country");

            Country country = new Country(id,countryName);

            allCountries.add(country);
        }
    }

    /**
     * gets all countries from the ObservableArrayList
     * @return ObservableList of all Countries
     */
    public static ObservableList<Country> getAllCountries()
    {
        return allCountries;
    }

}
