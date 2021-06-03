package Appointments;

import ViewsAndControllers.AppointmentsMainController;

import java.time.LocalDateTime;

/**
 * @author adamb
 * This is a class of Appointments
 */
public class Appointment
{
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Constructor for creating Appointment objects
     * @param id Appointment ID
     * @param title Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param type Appointment Type
     * @param start Appointment start LocalDateTime
     * @param end Appointment end LocalDateTime
     * @param customerID CustomerID
     * @param userID UserID
     * @param contactID ContactID
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * toString method
     * @return String describing the Appointment object fields
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", customerID=" + customerID +
                ", userID=" + userID +
                ", contactID=" + contactID +
                '}';
    }

    /**
     * getter for ID
     * @return int ID
     */
    public int getId() {
        return id;
    }

    /**
     * setter for ID
     * @param id int ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for title
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for title
     * @param title String title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for description
     * @return  String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for description
     * @param description String description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for location
     * @return String location
     */
    public String getLocation() {
        return location;
    }

    /**
     * setter for location
     * @param location String location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * getter for type
     * @return String type
     */
    public String getType() {
        return type;
    }

    /**
     * setter for type
     * @param type String type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter for start
     * @return  LocalDateTime start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * setter for start
     * @param start LocalDateTime start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * getter for end
     * @return LocalDateTime end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * setter for end
     * @param end LocalDateTime end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * getter for customerID
     * @return int customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * setter for customerID
     * @param customerID int customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
     * getter for contactID
     * @return int contactID
     */
    public int getContactID() {
        return contactID;

    }

    /**
     * setter for contactID
     * @param contactID int contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * getter for contactName
     * @return String contactName
     */
    public String getContactName()
    {
        AppointmentsMainController apc = new AppointmentsMainController();
        return apc.getContactName(contactID);
    }


}
