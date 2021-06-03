package ViewsAndControllers;

import Appointments.Appointment;
import Appointments.AppointmentDAO;
import Contacts.Contact;
import Contacts.ContactDAO;
import Customers.Customer;
import Customers.CustomerDAO;
import Users.UserDAO;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Class to control the updating Appointments
 * @author adamb
 */
public class UpdateAppointmentController
{
    Stage stage;
    Parent scene;

    LocalTime startCombo = LocalTime.of(3,00);
    LocalTime endCombo = LocalTime.of(23,00);

    @FXML
    private TextField idTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private DatePicker startDatepicker;

    @FXML
    private ComboBox<LocalTime> timeStartCombo;

    @FXML
    private Slider durationSlider;

    @FXML
    private Label DisplayDurationLabel;

    /**
     * Returns to main menu without updating Appointment
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
                changeWindow("/ViewsAndControllers/AppointmentsMain.fxml", event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onActionContactCombo(ActionEvent event) {

    }

    @FXML
    void onActionCustomer(ActionEvent event) {

    }

    @FXML
    void onActionDescription(ActionEvent event) {

    }

    @FXML
    void onActionID(ActionEvent event) {

    }

    @FXML
    void onActionLocation(ActionEvent event) {

    }

    /**
     * Updates Appointment
     * Appointment times are checked for overlap and business hours and updates database and ObservableArrayList
     * @param event button pressed
     * @throws IOException if fxml file is not found
     * @throws NullPointerException if fields are left null
     */
    @FXML
    void onActionSave(ActionEvent event) {

        try {
            int id = Integer.parseInt(idTextField.getText());
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String type = typeTextField.getText();
            String location = locationTextField.getText();
            LocalDateTime start = startDatepicker.getValue().atTime(timeStartCombo.getSelectionModel().getSelectedItem());
            LocalDateTime end = startDatepicker.getValue().atTime(timeStartCombo.getSelectionModel().getSelectedItem().plusMinutes((long) durationSlider.getValue()));
            int customerID = customerCombo.getSelectionModel().getSelectedItem().getCustomerID();
            int userID = UserDAO.getCurrentUserID();
            int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();


            if (!checkBusinessHours(start,end))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment scheduled outside of business hours");
                Optional<ButtonType> result = alert.showAndWait();
            }
            if (!checkForNoOverlap(id,start,end, customerID))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with another appointment");
                Optional<ButtonType> result = alert.showAndWait();
            }
            if (checkBusinessHours(start,end) && checkForNoOverlap(id,start,end, customerID))
            {
                AppointmentDAO.updateAppointment(id,title,description,location,type,start,end,customerID,contactID);
                Appointment appointment = new Appointment(id,title,description,location,type,start,end,customerID,userID,contactID);
                AppointmentDAO.getAllAppointments().set(getIndex(id),appointment);

                changeWindow("/ViewsAndControllers/AppointmentsMain.fxml",event);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select all appropriate fields before saving");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    void onActionStartDate(ActionEvent event) {

    }

    @FXML
    void onActionStartTimeCombo(ActionEvent event) {

    }

    @FXML
    void onActionType(ActionEvent event) {

    }

    @FXML
    void onActiontitle(ActionEvent event) {

    }

    /**
     * Sets initial comboBox and duration slider settings
     * lambda is used to make listener for duration slider
     */
    @FXML
    void initialize()
    {
        durationSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> DisplayDurationLabel.setText(String.valueOf(newValue)));

        while (startCombo.isBefore(endCombo.plusSeconds(1)))
        {
            timeStartCombo.getItems().add(startCombo);
            startCombo = startCombo.plusMinutes(15);
        }
        customerCombo.setItems(CustomerDAO.getAllCustomers());
        contactComboBox.setItems(ContactDAO.getAllContacts());
    }

    /**
     * Used to transfer the data between update appointment controller and appointment main controller and set screen controls
     * @param appointment Appointment that was selected in appointment main controller
     */
    public void sendAppointment(Appointment appointment)
    {
        idTextField.setText(String.valueOf(appointment.getId()));
        titleTextField.setText(appointment.getTitle());
        descriptionTextField.setText(appointment.getDescription());
        typeTextField.setText(appointment.getType());
        locationTextField.setText(appointment.getLocation());

        startDatepicker.setValue(appointment.getStart().toLocalDate());
        timeStartCombo.getSelectionModel().select(appointment.getStart().toLocalTime());
        durationSlider.setValue(ChronoUnit.MINUTES.between(appointment.getStart().toLocalTime(),appointment.getEnd().toLocalTime()));
        DisplayDurationLabel.setText(String.valueOf(durationSlider.getValue()));

        contactComboBox.getSelectionModel().select(getContactForCombo(appointment.getContactID()));
        customerCombo.getSelectionModel().select(getCustomerForCombo(appointment.getCustomerID()));
    }

    /**
     * gets Contact object for comboBox from contactID
     * @param contactID int contactID to be used to get Contact
     * @return Contact object
     */
    public Contact getContactForCombo(int contactID)
    {
        Contact contact = null;
        for (Contact c : ContactDAO.getAllContacts())
            if (contactID == c.getContactID())
                contact = c;

         return contact;
    }

    /**
     * gets Customer object for comboBox from customerID
     * @param customerID int customerID to be used to get Customer
     * @return Customer object
     */
    public Customer getCustomerForCombo(int customerID)
    {
        Customer customer = null;
        for (Customer c : CustomerDAO.getAllCustomers())
            if (customerID == c.getCustomerID())
                customer = c;
        return customer;
    }

    /**
     * get appointment index
     * @param appointmentID int appointmentID for which to get index
     * @return int appointment index
     */
    int getIndex(int appointmentID)
    {
        int index = -1;

        for (int i =  0; i < AppointmentDAO.getAllAppointments().size(); i++)
            if (appointmentID == AppointmentDAO.getAllAppointments().get(i).getId())
                index = i;

        return index;

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
     * method to check if the appointment time is within business hours using eastern standard time
     * @param start LocalDateTime appointment start time
     * @param end LocalDateTime appointment end time
     * @return boolean true if appointment is within business hours eastern standard time
     */
    public boolean checkBusinessHours(LocalDateTime start, LocalDateTime end)
    {
        boolean withinHours = false;

        ZoneId zoneId = ZoneId.systemDefault();
        ZoneId easternZoneId = ZoneId.of("America/New_York");

        ZonedDateTime eastZonedOpen = ZonedDateTime.of(start.toLocalDate(),start.toLocalTime(), easternZoneId);
        ZonedDateTime zoneStart = eastZonedOpen.withZoneSameInstant(zoneId);
        LocalDateTime easternStart = zoneStart.toLocalDateTime();

        ZonedDateTime eastZonedClose = ZonedDateTime.of(end.toLocalDate(),end.toLocalTime(), easternZoneId);
        ZonedDateTime zoneEnd = eastZonedClose.withZoneSameInstant(zoneId);
        LocalDateTime easternEnd = zoneEnd.toLocalDateTime();

        System.out.println(easternStart.toLocalTime()); // for testing only
        System.out.println(easternEnd.toLocalTime());  // for testing only

        if (easternStart.toLocalTime().isAfter(LocalTime.of(7,59)) && easternEnd.toLocalTime().isBefore(LocalTime.of(22,00)))
            withinHours = true;
        return withinHours;
    }

    /**
     * method to check for no overlapping appointments
     * checks if there are no conflicting appointments for a given customer
     * uses lambda to filter all appointments aside from current appointment
     * uses lambda to filter the appointments by customerID
     * uses lambda to filter the appointments on the same date
     * @param AppointmentID int AppointmentID to prevent checking against same appointment
     * @param start LocalDateTime appointment start
     * @param end LocalDateTime appointment end
     * @param customerID int customerID
     * @return boolean true if there are no overlapping appointments
     */
    public boolean checkForNoOverlap(int AppointmentID, LocalDateTime start, LocalDateTime end, int customerID)
    {
        boolean noOverlap = true;
        ObservableList<Appointment> appointmentsWithCustomer = AppointmentDAO.getAllAppointments().stream()
                .filter( p -> p.getId() != AppointmentID)
                .filter( a -> a.getCustomerID() == customerID)
                .filter( b -> b.getStart().toLocalDate().isEqual(start.toLocalDate()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        for (Appointment appointment : appointmentsWithCustomer)
            if ((start.toLocalTime().isBefore(appointment.getEnd().toLocalTime())
                    && start.toLocalTime().isAfter(appointment.getStart().toLocalTime()))
                    || (start.toLocalTime().isBefore(appointment.getStart().toLocalTime())
                    && (end.toLocalTime().isAfter(appointment.getStart().toLocalTime()))))
            {
                noOverlap = false;
                break;
            }
        return noOverlap;
    }

}
