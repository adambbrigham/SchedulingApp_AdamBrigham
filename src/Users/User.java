package Users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * this class is for Users
 */
public class User
{
    private int userID;
    private String userName;
    private String password;
    private LocalDate date;
    private LocalTime time;


    /**
     * constructor for User objects
     * @param userID int userID
     * @param userName String userName
     * @param password String password
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * toString method to describe User objects
     * @return String to describe User fields
     */
    @Override
    public String toString() {
        return  "userID: " + userID +
                ", userName: " + userName +
                ", password: " + password;
    }

    /**
     * getter for userID
     * @return int userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * setter for userID
     * @param userID int userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * getter for userName
     * @return String userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * setter for userName
     * @param userName String userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * getter for userPassword
     * @return String userPassword
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for userPassword
     * @param password String userPassword
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for date
     * @return LocalDate date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * setter for date
     * @param date LocalDate date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * getter for Time
     * @return LocalTime time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * setter for time
     * @param time LocalTime time
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

}
