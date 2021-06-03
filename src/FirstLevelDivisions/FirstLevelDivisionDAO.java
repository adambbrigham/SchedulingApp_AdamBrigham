package FirstLevelDivisions;

import Utilities.Query;
import Utilities.dbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * this class is for FirstLevelDivisions
 * @author adamb
 */
public class FirstLevelDivisionDAO
{

    private static ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

    /**
     * gets all FirstLevelDivisions from the database
     * Select query to get FirstLevelDivisions from database and add them to an ObservableArrayList
     * @throws SQLException if there is an error querying the database
     */
    public static void addAllDivisions() throws SQLException{
        Connection con = dbconnection.getConnection(); // get connection
        try {
            Query.setStatement(con);

            Statement statement = Query.getStatement();

            String selectStatement = "SELECT * FROM first_level_divisions;";
            statement.execute(selectStatement);

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                int divisionID = resultSet.getInt("Division_ID");
                String division = resultSet.getString("Division");
                LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();
                String createdBy = resultSet.getString("Created_By");
                LocalTime lastUpdate = resultSet.getTime("Last_Update").toLocalTime();
                String lastUpdateBy = resultSet.getString("Last_Updated_By");
                int countryID = resultSet.getInt("COUNTRY_ID");

                FirstLevelDivision FLD = new FirstLevelDivision(divisionID, division, countryID);

                allDivisions.add(FLD);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets all FirstLevelDivisions from ObservableArrayList
     * @return ObservableList of all FirstLevelDivisions
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions()
    {
        return allDivisions;
    }




}
