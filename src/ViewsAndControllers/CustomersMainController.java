package ViewsAndControllers;

import Appointments.Appointment;
import Appointments.AppointmentDAO;
import Customers.Customer;
import Customers.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * This class is for the main screen for Customers
 * @author adamb
 */
public class CustomersMainController
{

    Stage stage;
    Parent scene;

    @FXML
    private Button AddButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button ModifyButton;

    @FXML
    private Button MainMenuButton;

    @FXML
    private TableView<Customer> CustomersTable;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> postalColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TableColumn<Customer, Integer> divisionColumn;

    /**
     * changes to the add customer screen
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionAdd(ActionEvent event)  {

        try {
            changeWindow("/ViewsAndControllers/AddCustomer.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionCustomerTable(ActionEvent event)
    {

    }

    /**
     * deletes the customer selected from the tableview
     * This method deletes the customer from the database and the ObservableArrayList
     * The customer is checked to have scheduled appointments, and if true, the appointments are first deleted before the customer is deleted
     * @param event button pressed
     * @throws SQLException if there is an error querying the database
     * @throws NullPointerException if no customer is selected
     */
    @FXML
    void onActionDelete(ActionEvent event)  {
        try
        {
            if (apptsPresent(CustomersTable.getSelectionModel().getSelectedItem().getCustomerID()))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer has scheduled appointments. Delete?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    while (apptsPresent(CustomersTable.getSelectionModel().getSelectedItem().getCustomerID()))
                    {
                        AppointmentDAO.deleteAppointment(getAssociatedAppt(CustomersTable.getSelectionModel().getSelectedItem().getCustomerID()));
                        AppointmentDAO.getAllAppointments().remove(getAppointmentIndex(getAssociatedAppt(CustomersTable.getSelectionModel().getSelectedItem().getCustomerID())));
                    }
                    CustomerDAO.deleteCustomer(CustomersTable.getSelectionModel().getSelectedItem().getCustomerID());
                    CustomerDAO.getAllCustomers().remove(CustomersTable.getSelectionModel().getSelectedItem());
                    CustomersTable.getSelectionModel().clearSelection();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete customer?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    AppointmentDAO.deleteAppointment(getAssociatedAppt(CustomersTable.getSelectionModel().getSelectedItem().getCustomerID()));
                    CustomerDAO.deleteCustomer(CustomersTable.getSelectionModel().getSelectedItem().getCustomerID());
                    CustomerDAO.getAllCustomers().remove(CustomersTable.getSelectionModel().getSelectedItem());
                    CustomersTable.getSelectionModel().clearSelection();
                }
            }

        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a customer to remove.");
            Optional<ButtonType> result = alert.showAndWait();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void onActionFirstDivisionCombo(ActionEvent event)
    {

    }

    /**
     * changes screen to main menu
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionMainMenu(ActionEvent event)  {
        try {
            changeWindow("/ViewsAndControllers/MainMenu.fxml" ,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes to update customer screen with selected customer
     * @param event button pressed
     * @throws IOException if fxml file is not found
     * @throws NullPointerException if no customer is selected
     */
    @FXML
    void onActionModify(ActionEvent event) throws IOException {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ViewsAndControllers/UpdateCustomer.fxml" ));
            loader.load();

            UpdateCustomerController updateCustomerController = loader.getController();
            updateCustomerController.sendCustomer(CustomersTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Select a customer to update");
            alert.showAndWait();
        }
    }

    /**
     * sets all customers to be displayed on tableView when screen is opened
     */
    @FXML
    void initialize() {

        CustomersTable.setItems(CustomerDAO.getAllCustomers());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerAddress"));
        postalColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerPostalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerPhone"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));
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
     * checks if a customer has scheduled appointments
     * uses lambda to filter customers by customerID
     * @param customerID int customerID
     * @return boolean true if there are scheduled appointments for customer
     */
    public boolean apptsPresent(int customerID)
    {
        Optional<Appointment> appointment = AppointmentDAO.getAllAppointments().stream()
                .filter(a -> a.getCustomerID() == customerID)
                .findAny();
        return appointment.isPresent();
    }

    /**
     * gets the appointmentID of the scheduled appointment with customer
     * @param customerID int customerID
     * @return int appointmentID
     */
    public int getAssociatedAppt(int customerID)
    {
        int appointmentID = -1;
        for (Appointment appointment : AppointmentDAO.getAllAppointments())
            if (appointment.getCustomerID() == customerID)
                appointmentID = appointment.getId();
        return appointmentID;
    }

    /**
     * gets the index of the scheduled appointment
     * @param appointmentID int appointmentID
     * @return int appointment index from ObservableArrayList
     */
    public int getAppointmentIndex(int appointmentID)
    {
        int index = -1;
        ObservableList<Appointment> appointments;
        appointments = AppointmentDAO.getAllAppointments();
        for (int i = 0; i < appointments.size(); i++)
        {
            if (appointments.get(i).getId() == appointmentID)
                index = i;
        }
        return index;
    }

}
