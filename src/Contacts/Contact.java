package Contacts;

/**
 * This class if for Contacts
 * @author adamb
 */
public class Contact
{
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Constructor
     * @param contactID int contactID
     * @param contactName String contact Name
     * @param contactEmail String contact email
     */
    public Contact (int contactID, String contactName, String contactEmail)
    {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * toString method to describe contact objects
     * @return String contact name for use in comboBoxes
     */
    @Override
    public String toString() {
        return contactName;
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
    public String getContactName() {
        return contactName;
    }

    /**
     * setter for contactName
     * @param contactName String contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * getter for contactEmail
     * @return String contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * setter for contactEmail
     * @param contactEmail String contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
