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
 * This class is the controller for adding customers with the GUI
 * @author adamb
 */
public class AddCustomerController
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
     * cancels changes and returns to main menu
     * @param event button pressed
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Discard changes and return to main menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
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

        divisionCombo.setItems(getSelectedDivisions(selectedItem));
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
     * Adds a customer
     * adds a customer to the database and ObservableArrayList based on comboBoxes and textField input from user
     * @param event button pressed
     */
    @FXML
    void onActionSave(ActionEvent event) {

        FirstLevelDivision FLD = divisionCombo.getSelectionModel().getSelectedItem();
        try {
            int id = getUniqueID();
            String name = nameTextField.getText();
            String address = addressTextField.getText();
            String postal = postalCodeTextField.getText();
            String phone = phoneNumberTextField.getText();
            int division = FLD.getDivisionID();

            Customer customer = new Customer(id, name,address, postal,phone,division);
            CustomerDAO.getAllCustomers().add(customer);

            CustomerDAO.addCustomer(id,name,address,postal,phone, division);
            changeWindow("/ViewsAndControllers/CustomersMain.fxml",event);
        }
        catch (SQLException | IOException throwables)
        {
            throwables.printStackTrace();
        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select all appropriate fields before saving");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * sets the country and division combo boxes when screen opens
     */
    @FXML
    void initialize()
    {
        countryComboBox.setItems(CountryDAO.getAllCountries());
        Tooltip tooltip = new Tooltip("Select a country prior to selecting a first level division.");
        divisionCombo.setTooltip(tooltip);

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
     * gets a unique ID to be used when adding a customer
     * @return int unique ID
     */
    int getUniqueID()
    {
        int count = CustomerDAO.getAllCustomers().size();
        return count + 1;
    }
}
