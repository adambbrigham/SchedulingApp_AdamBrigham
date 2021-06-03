package Countries;

/**
 * This is the Country class
 * @author adamb
 */
public class Country
{
    private int countryID;
    private String countryName;


    /**
     * Constructor for Country objects
     * @param countryID int countryID
     * @param countryName String countryName
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;

    }

    /**
     * toString method for describing Country objects
     * @return String describing country fields
     */
    @Override
    public String toString() {
        return  "Country ID: " + countryID + ",   " +
                "Country Name: " + countryName;
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
     * @param countryID int countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * getter for countryName
     * @return String countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * setter for countryName
     * @param countryName String countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


}
