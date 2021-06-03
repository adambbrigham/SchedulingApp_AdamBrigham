package Customers;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This is the class for Customers
 * @author adamb
 */
public class Customer
{
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private LocalDate createdDate;
    private String createdBy;
    private LocalTime lastUpdate;
    private String lastUpdateBy;
    private int divisionID;

    /**
     * Constructor for Customer objects
     * @param customerID int customerID
     * @param customerName String customerName
     * @param customerAddress String customerAddress
     * @param customerPostalCode String customerPostalCode
     * @param customerPhone String customerPhone
     * @param divisionID int FirstLevelDivisionID
     */
    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.divisionID = divisionID;
    }

    /**
     * toString method to describe Customer objects
     * @return String customer names for comboboxes
     */
    @Override
    public String toString() {
        return customerName;

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
     * getter for customerName
     * @return String customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * setter for customerName
     * @param customerName String customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * getter for customerAddress
     * @return String customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * setter for customerAddress
     * @param customerAddress String customerAddress
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * getter for customerPostalCode
     * @return String customerPostalCode
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * setter for customerPostalCode
     * @param customerPostalCode String customerPostalCode
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * getter for customerPhone number
     * @return String customerPhone number
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * setter for customerPhone number
     * @param getCustomerPhone String customerPhone number
     */
    public void setCustomerPhone(String getCustomerPhone) {
        this.customerPhone = getCustomerPhone;
    }

    /**
     * getter for FirstLevelDivision
     * @return int FirstLevelDivisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * setter for FirstLevelDivision
     * @param divisionID int FirstLevelDivisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
