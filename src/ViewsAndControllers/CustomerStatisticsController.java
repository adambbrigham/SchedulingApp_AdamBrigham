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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * This class makes a report of the number of appointments for customers and the average appointment duration
 * @author adamb
 */
public class CustomerStatisticsController
{
    Stage stage;
    Parent scene;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Label resultLabel;

    @FXML
    private Button OKButton;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private Label ApptNumberLabel;

    @FXML
    private Label ApptDurationLabel;

    @FXML
    void onActionCustomerCombo(ActionEvent event) {

    }

    /**
     * changes screen to main menu
     * @param event button pressed
     * @throws IOException if fxml is not found
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
     * gets input from the comboBoxes and displays the output in the results labels
     * @param event button pressed
     */
    @FXML
    void onActionOK(ActionEvent event) {

        ApptDurationLabel.setText(String.valueOf(getAverageApptDuration(customerCombo.getSelectionModel().getSelectedItem())));
        ApptNumberLabel.setText(String.valueOf(getNumberOfAppts(customerCombo.getSelectionModel().getSelectedItem())));
    }

    /**
     * sets customer comboBox with all customers when screen opens
     */
    @FXML
    void initialize()
    {
        customerCombo.setItems(CustomerDAO.getAllCustomers());
        customerCombo.getSelectionModel().selectFirst();
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
     * gets the number of appointments for the selected customer
     * @param customer Customer to count appointments
     * @return int number of appointments
     */
    public int getNumberOfAppts(Customer customer)
    {
        int numberOfAppts = 0;
        for (Appointment appointment : AppointmentDAO.getAllAppointments())
        {
            if (customer.getCustomerID() == appointment.getCustomerID())
                numberOfAppts++;
        }
        return numberOfAppts;
    }

    /**
     * gets the average duration of the appointments for a customer
     * if there are no appointments, then the average is set to zero to avoid dividing by zero
     * lambda is used to filter the appointments by customer
     * @param customer Customer to find average duration of appointments
     * @return double average appointment length
     */
    public double getAverageApptDuration(Customer customer)
    {
        double average = 0;
        int totalDuration = 0;

        ObservableList<Appointment> apptsForCustomer = AppointmentDAO.getAllAppointments().stream()
                .filter( a -> a.getCustomerID() == customer.getCustomerID())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (apptsForCustomer.isEmpty())
            average = 0;
        else
        {
            for (Appointment appointment : apptsForCustomer)
            {
                totalDuration += ChronoUnit.MINUTES.between(appointment.getStart().toLocalTime(),appointment.getEnd().toLocalTime());
            }
            average = (double) totalDuration / (double) apptsForCustomer.size();
        }
        return average;
    }
}
