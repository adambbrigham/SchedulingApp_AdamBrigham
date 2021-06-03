package FirstLevelDivisions;

/**
 * This class is for FirstLevelDivisions
 * @author adamb
 */
public class FirstLevelDivision
{
    private int divisionID;
    private String divisionName;
    private int countryID;

    /**
     * constructor for FirstLevelDivision objects
     * @param divisionID int divisionID
     * @param divisionName String divisionName
     * @param countryID int countryID
     */
    public FirstLevelDivision(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * toString method to describe FirstLevelDivision objects
     * @return String describing fields
     */
    @Override
    public String toString() {
        return //"FirstLevelDivision{" +
                "Division ID: " + divisionID + ",   " +
                "Division Name: " + divisionName + ",   " +
                "Country ID: " + countryID;
    }

    /**
     * getter for divisionIDs
     * @return int divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * setter for divisionID
     * @param divisionID int divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * getter for divisionName
     * @return String divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * setter for divisionName
     * @param divisionName String divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * getter for countryID
     * @return int countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * setter for countryID
     * @param countryID countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
