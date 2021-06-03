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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class controls the screen for adding Appointments using the GUI
 * @author adamb
 */
public class AddAppointmentController {

    LocalTime startCombo = LocalTime.of(3,00);
    LocalTime endCombo = LocalTime.of(23,00);

    Stage stage;
    Parent scene;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField typeTextField;

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

    /**
     * returns to the main menu
     * @param event Cancel button pressed
     * @throws IOException fxml file not found
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Discard changes and return to main menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            try {
                changeWindow("/ViewsAndControllers/MainMenu.fxml", event);
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
    private Label DisplayDurationLabel;

    @FXML
    void onActionLocation(ActionEvent event) {

    }

    /**
     * adds an appointment
     * appointment is added to the database and ObservableArrayList based on textFields input from user
     * checks for overlapping appointments and business hours in eastern time
     * @param event push save button
     */
    @FXML
    void onActionSave(ActionEvent event) {

        try {

            int apptID = getUniqueID();
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
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
            if (!checkForNoOverlap(start,end, customerID))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment cannot overlap with another appointment with this customer");
                Optional<ButtonType> result = alert.showAndWait();
            }
            if (checkBusinessHours(start,end) && checkForNoOverlap(start,end, customerID))
            {
                Appointment appointment = new Appointment(apptID,title,description,location,type,start,end,customerID,userID,contactID);
                AppointmentDAO.getAllAppointments().add(appointment);

                AppointmentDAO.addAppointment(apptID,title,description,location,type,start,end,customerID,userID,contactID);
                changeWindow("/ViewsAndControllers/AppointmentsMain.fxml", event);
            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
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
    private Slider durationSlider; // sets duration

    @FXML
    void onActionType(ActionEvent event) {

    }

    @FXML
    void onActiontitle(ActionEvent event) {

    }

    /**
     * initialize method sets combo boxes and duration slider
     * contains lambda to implement ChangeListener interface for duration slider
     */
    @FXML
    void initialize()
    {

        customerCombo.setItems(CustomerDAO.getAllCustomers());
        contactComboBox.setItems(ContactDAO.getAllContacts());

        while (startCombo.isBefore(endCombo.plusSeconds(1)))
        {
            timeStartCombo.getItems().add(startCombo);
            startCombo = startCombo.plusMinutes(15);
        }
        timeStartCombo.getSelectionModel().select(LocalTime.of(8,00));

        durationSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> DisplayDurationLabel.setText(String.valueOf(newValue))
        );
        DisplayDurationLabel.setText(String.valueOf(durationSlider.getValue()));
    }

    /**
     * method to get a uniqueID when adding appointments
     * @return int unique ID to be assigned as appointment ID
     */
    int getUniqueID()
    {
        int count = AppointmentDAO.getAllAppointments().size();
        return count + 1;
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
     * uses lambda to filter the appointments by customerID
     * uses lambda to filter the appointments on the same date
     * @param start LocalDateTime appointment start
     * @param end LocalDateTime appointment end
     * @param customerID int customerID
     * @return boolean true if there are no overlapping appointments
     */
    public boolean checkForNoOverlap(LocalDateTime start, LocalDateTime end, int customerID)
    {
        boolean noOverlap = true;
        ObservableList<Appointment> appointmentsWithCustomer = AppointmentDAO.getAllAppointments().stream()
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
