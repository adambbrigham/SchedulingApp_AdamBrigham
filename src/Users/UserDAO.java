package Users;

import Utilities.Query;
import Utilities.dbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * /**
 * This class makes queries to the database
 * @author adamb
 */
public class UserDAO
{
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    private static int currentUser;

    /**
     * gets all Users from the database
     * Select query to get Users from database and add them to an ObservableArrayList
     * @throws SQLException if there is an error querying the database
     */
    public static void addAllUsers() throws SQLException{
        Connection con = dbconnection.getConnection(); // get connection
        try {
            Query.setStatement(con);
            Statement statement = Query.getStatement();

            String selectStatement = "SELECT * FROM users;"; // SELECT statement
            statement.execute(selectStatement);  // execute statement

            ResultSet resultSet = statement.getResultSet(); // get result set

            while (resultSet.next()) {
                int userID = resultSet.getInt("User_ID"); // get user ID from database
                String username = resultSet.getString("User_Name");  // get username from database
                String password = resultSet.getString("Password"); // get user password from database
                LocalDate date = resultSet.getDate("Create_Date").toLocalDate(); // get local date
                LocalTime time = resultSet.getTime("Create_Date").toLocalTime();  // get local time
                String createdBy = resultSet.getString("Created_By"); // get created by
                LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime(); // last update

                //Create user object
                User user = new User( userID,  username,  password);

                //Add user to ObservableArraylist
                allUsers.add(user);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * gets all Users from ObservableArrayList
     * @return ObservableList of all Users
     */
    public static ObservableList<User> getAllUsers()
    {
        return allUsers;
    }

    /**
     * method to set the user operating the application (currentUser)
     * @param userName String userName
     */
    public static void setCurrentUserID (String userName)
    {
        int currentUserID = -1;
        for (User user : UserDAO.getAllUsers())
            if (userName.equals(user.getUserName()))
                currentUserID = user.getUserID();
            currentUser = currentUserID;
    }

    /**
     * method for getting the currentUser
     * @return int userID
     */
    public static int getCurrentUserID()
    {
        return currentUser;
    }





}
