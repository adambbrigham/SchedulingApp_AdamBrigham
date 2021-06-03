package ViewsAndControllers;

import Appointments.Appointment;
import Appointments.AppointmentDAO;
import Customers.Customer;
import Customers.CustomerDAO;
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
import java.time.Month;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class makes a report of number the appointments by Customer by Type and by Month
 * Use the radio buttons to select Type or Month. Use comboBoxes to choose a Customer, and a Month or Type, as appropriate. Press OK.
 * @author adamb
 */
public class AppointmentsByCustomerController
{
    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Label resultLabel;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private ToggleGroup filterToggle;

    @FXML
    private RadioButton typeRadio;

    @FXML
    private ComboBox<String> typesCombo;

    @FXML
    private ComboBox<Month> monthsCombo;

    @FXML
    private Button OKButton;

    @FXML
    private RadioButton allRadio;

    @FXML
    void onActionAllRadio(ActionEvent event) {
        typesCombo.setDisable(true);
        monthsCombo.setDisable(true);

    }

    @FXML
    void onActionCustomerCombo(ActionEvent event) {

    }

    @FXML
    void onActionMonthsCombo(ActionEvent event) {

    }

    @FXML
    void onActionTypesCombo(ActionEvent event) {

    }



    /**
     * Displays the number of appointments based on the user selections
     * @param event OK button pressed
     * @throws NullPointerException occurs when not all fields are selected
     */
    @FXML
    void onActionOK(ActionEvent event){

        try {
            Customer customer = customerCombo.getSelectionModel().getSelectedItem();

            if (monthRadio.isSelected())
            {
                Month month = monthsCombo.getSelectionModel().getSelectedItem();
                resultLabel.setText(String.valueOf(countApptsByMonth(customer,month)));
            }
            else if (typeRadio.isSelected())
            {
                String type = typesCombo.getSelectionModel().getSelectedItem();
                resultLabel.setText(String.valueOf(countApptsByType(customer,type)));
            }
            else if (allRadio.isSelected())
            {
                resultLabel.setText(String.valueOf(countAllForCustomer(customer)));
            }

        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select all appropriate fields");
            Optional<ButtonType> result = alert.showAndWait();
        }


    }

    /**
     * Returns to the main menu screen of GUI
     * @param event button pressed
     */
    @FXML
    void onActionMainMenu(ActionEvent event) {
        try {
            changeWindow("/ViewsAndControllers/MainMenu.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * selects filter by month
     * gets ObservableList of Month objects, enables the Month comboBox and disables the Type comboBox
     * @param event radio button pressed
     */
    @FXML
    void onActionMonthRadio(ActionEvent event) {
        monthsCombo.setDisable(false);
        typesCombo.setDisable(true);
        monthsCombo.setItems(getMonths());
        monthsCombo.getSelectionModel().selectFirst();

    }

    /**
     * selects filter by appointment type
     * gets ObservableList of appointment types, enables the Month comboBox and disables the Type comboBox
     * @param event radio button pressed
     */
    @FXML
    void onActionTypeRadio(ActionEvent event) {
        monthsCombo.setDisable(true);
        typesCombo.setDisable(false);
        typesCombo.setItems(getTypes());
        typesCombo.getSelectionModel().selectFirst();

    }

    /**
     * selects a default selection for the fields and provides instructions with the results label
     */
    @FXML
    void initialize()
    {
        customerCombo.setItems(CustomerDAO.getAllCustomers());
        monthsCombo.setItems(getMonths());
        monthsCombo.setDisable(true);
        typesCombo.setDisable(true);
        resultLabel.setText("Select fields and press OK");

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
     * gets an ObservableArrayList of appointment types
     * @return ObservableList of all appointment types
     */
    public ObservableList<String> getTypes()
    {
        ObservableList<String> types = FXCollections.observableArrayList();
        for (Appointment appointment : AppointmentDAO.getAllAppointments())
            types.add(appointment.getType());

        return types;
    }

    /**
     * gets an ObservableArrayList of Months
     * @return ObservableList of all Months
     */
    public ObservableList<Month> getMonths()
    {
        ObservableList<Month> months = FXCollections.observableArrayList();
        months.addAll(Month.JANUARY,Month.FEBRUARY,Month.APRIL,Month.MAY,Month.JUNE,
                Month.JULY,Month.AUGUST,Month.SEPTEMBER,Month.OCTOBER, Month.NOVEMBER,Month.DECEMBER);
        return months;
    }

    /**
     * gets the count of appointments for a selected month for a selected customer
     * uses lambda to filter appointments to match those with parameter customerID
     * uses lambda to filter the customer filtered appointments by the month parameter
     * @param customer the customer selected by user
     * @param month the month selected by user
     * @return int the count of filtered appointments by month
     */
    public int countApptsByMonth(Customer customer,Month month)
    {
        ObservableList<Appointment> appointmentsByCustomer = AppointmentDAO.getAllAppointments().stream()
                .filter( a -> a.getCustomerID() == customer.getCustomerID())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return (int) appointmentsByCustomer.stream()
                .filter( m -> m.getStart().toLocalDate().getMonth().equals(month))
                .count();
    }

    /**
     * gets the count of appointments for a selected type for a selected customer
     * uses lambda to filter appointments to match those with parameter customerID
     * uses lambda to filter the customer filtered appointments by the type parameter
     * @param customer the customer selected by user
     * @param type the type selected by user
     * @return int the count of filtered appointments by type
     */
    public int countApptsByType(Customer customer, String type)
    {
        ObservableList<Appointment> appointmentsByType = AppointmentDAO.getAllAppointments().stream()
                .filter( c -> c.getCustomerID() == customer.getCustomerID())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return (int) appointmentsByType.stream()
                .filter( m -> m.getType().equals(type))
                .count();
    }

    /**
     * gets the total appointment count for the customer
     * uses lambda to filter appointments by customer
     * @param customer the customer selected by user
     * @return int total count of appointments for the customer
     */
    public int countAllForCustomer(Customer customer)
    {
        ObservableList<Appointment> appointmentsByType = AppointmentDAO.getAllAppointments().stream()
                .filter( c -> c.getCustomerID() == customer.getCustomerID())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return appointmentsByType.size();
    }
}
