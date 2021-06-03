package ViewsAndControllers;

import Countries.Country;
import Countries.CountryDAO;
import Customers.Customer;
import Customers.CustomerDAO;
import FirstLevelDivisions.FirstLevelDivision;
import FirstLevelDivisions.FirstLevelDivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class to control the updating Customers
 * @author adamb
 */
public class UpdateCustomerController
{
    Stage stage;
    Parent scene;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private ComboBox<FirstLevelDivision> divisionCombo;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void onActionAddress(ActionEvent event) {

    }

    /**
     * Returns to main menu without updating Customer
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionCancel(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Discard changes and return to main menu?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            try {
                changeWindow("/ViewsAndControllers/CustomersMain.fxml",event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sets the divisionCombo
     * @param event country selected
     */
    @FXML
    void onActionCountryCombo(ActionEvent event)
    {
        int selectedItem = countryComboBox.getSelectionModel().getSelectedItem().getCountryID();

        Tooltip tooltip = new Tooltip("Select country prior to setting the first level division");
        divisionCombo.setItems(getSelectedDivisions(selectedItem));
        divisionCombo.setTooltip(tooltip);

    }

    @FXML
    void onActionDivision(ActionEvent event) {

    }

    @FXML
    void onActionID(ActionEvent event) {

    }

    @FXML
    void onActionName(ActionEvent event) {

    }

    @FXML
    void onActionPhone(ActionEvent event) {

    }

    @FXML
    void onActionPostalCode(ActionEvent event) {

    }

    /**
     * Updates a customer
     * Updates a customer to the database and ObservableArrayList based on comboBoxes and textField input from user
     * @param event button pressed
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String postal = postalCodeTextField.getText();
        String phone = phoneNumberTextField.getText();
        int division = (divisionCombo.getSelectionModel().getSelectedItem().getDivisionID());

        CustomerDAO.updateCustomer(id,name,address, postal, phone, division); // update query to database
        Customer customer = new Customer(id,name,address, postal, phone, division); // create customer object for observableArrayList

        CustomerDAO.getAllCustomers().set(getIndex(id),customer);  // replace customer object at index with new object

        changeWindow("/ViewsAndControllers/CustomersMain.fxml",event);
    }

    /**
     * sets the country combo box when screen opens
     */
    @FXML
    void initialize()
    {
        countryComboBox.setItems(CountryDAO.getAllCountries());
    }

    /**
     * allows user to change to a different screen
     * @param str the location the new screen fxml file
     * @param event the button pressed
     * @throws IOException if fxml file is not found
     */
    public void changeWindow(String str, Event event) throws IOException
    {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(str));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * used to filter the first level divisions based on countryID
     * lambda is used to filter divisions by countryID implements Predicate interface
     * @param countryID int countryID
     * @return ObservableList of FirstLevelDivisions with a given countryID
     */
    ObservableList<FirstLevelDivision> getSelectedDivisions(int countryID)
    {
        ObservableList<FirstLevelDivision> selectedDivisions;

        selectedDivisions = FirstLevelDivisionDAO.getAllDivisions().stream()
                .filter( d -> d.getCountryID() == countryID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        return selectedDivisions;
    }

    /**
     * get customer index
     * @param customerID int customerID for which to get index
     * @return int customer index
     */
    int getIndex(int customerID)
    {
        int index = -1;

        for (int i =  0; i < CustomerDAO.getAllCustomers().size(); i++)
            if (customerID == CustomerDAO.getAllCustomers().get(i).getCustomerID())
                index = i;

        return index;

    }


    /**
     * gets FirstLevelDivision for comboBox based on divisionID
     * @param divisionID int FirstLevelDivisionID
     * @return FirstLevelDivision object
     */
    FirstLevelDivision getDivisionForCombo(int divisionID)
    {
        FirstLevelDivision firstLevelDivision = null;

        for (FirstLevelDivision fld : FirstLevelDivisionDAO.getAllDivisions())
            if (divisionID == fld.getDivisionID())
                 firstLevelDivision = fld;

        return firstLevelDivision;
    }

    /**
     * gets Country for comboBox based on divisionID
     * @param divisionID int FirstLevelDivisionID used to filter Countries
     * @return Country object
     */
    Country getCountryForCombo(int divisionID)
    {
        int countryID = 0;
        Country country = null;

        for (FirstLevelDivision fld : FirstLevelDivisionDAO.getAllDivisions())
            if(divisionID == fld.getDivisionID())
                countryID = fld.getCountryID();

        for (Country c : CountryDAO.getAllCountries())
            if(countryID == c.getCountryID())
                country = c;

            return country;
    }

    /**
     * Used to transfer the data between update customer controller and customer main controller and set screen controls
     * @param customer Customer that was selected in customer main controller
     */
    public void sendCustomer(Customer customer)
    {
        idTextField.setText(String.valueOf(customer.getCustomerID()));
        nameTextField.setText(customer.getCustomerName());
        addressTextField.setText(customer.getCustomerAddress());
        postalCodeTextField.setText(customer.getCustomerPostalCode());
        phoneNumberTextField.setText(customer.getCustomerPhone());

        divisionCombo.getSelectionModel().select(getDivisionForCombo(customer.getDivisionID()));
        countryComboBox.getSelectionModel().select(getCountryForCombo(customer.getDivisionID()));
        int selectedItem = countryComboBox.getSelectionModel().getSelectedItem().getCountryID();

        Tooltip tooltip = new Tooltip("Select country prior to setting the first level division");
        divisionCombo.setItems(getSelectedDivisions(selectedItem));
        divisionCombo.setTooltip(tooltip);
    }

}
