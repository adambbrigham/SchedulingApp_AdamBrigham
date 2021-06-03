package ViewsAndControllers;

import Appointments.Appointment;
import Appointments.AppointmentDAO;
import Contacts.Contact;
import Contacts.ContactDAO;
import javafx.collections.FXCollections;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is for the main screen for Appointments
 * @author adamb
 */
public class AppointmentsMainController
{

    Stage stage;
    Parent scene;


    @FXML
    private TableView<Appointment> AppointmentsTable;

    @FXML
    private TableColumn<Appointment, Integer> idColumn;

    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, String> TypeColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;

    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;

    @FXML
    private TableColumn<Appointment, String> contactColumn;

    @FXML
    private Button AddButton;

    @FXML
    private Button ModifyButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button MainMenuButton;

    @FXML
    private RadioButton weekRadio;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private RadioButton allRadio;


    @FXML
    void onActionMonthRadio(ActionEvent event) {

    }

    @FXML
    void onActionWeekRadio(ActionEvent event) {

    }

    @FXML
    void onActionAllRadio(ActionEvent event) {

    }

    @FXML
    private DatePicker datePicker;

    /**
     * the date picker is used to choose the month or week and apply filter
     * First select the appropriate radio button, then choose the desired week or month to apply filter to tableView
     * @param event datepicker selected
     */
    @FXML
    void datePicker(ActionEvent event) {
        datePicker.setShowWeekNumbers(true);
        LocalDate date = datePicker.getValue();

        int weekOfYear = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        System.out.println("week number: " + weekOfYear);
        int monthOfYear = date.get(ChronoField.MONTH_OF_YEAR);
        System.out.println("month number: " + monthOfYear);

        if (monthRadio.isSelected())
            AppointmentsTable.setItems(filteredByMonthStream());
        else if (weekRadio.isSelected())
            AppointmentsTable.setItems(filteredByWeekStream());
        else if (allRadio.isSelected())
            AppointmentsTable.setItems(AppointmentDAO.getAllAppointments());
    }

    /**
     * changes to the add appointment screen
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        try {
            changeWindow("/ViewsAndControllers/AddAppointment.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionApptsTable(ActionEvent event) {

    }

    /**
     * deletes the appointment selected from the tableview
     * This method deletes the appointment from the database and the ObservableArrayList
     * @param event button pressed
     * @throws NullPointerException if no appointment is selected
     */
    @FXML
    void onActionDelete(ActionEvent event) {
        try {

            int appointmentID = AppointmentsTable.getSelectionModel().getSelectedItem().getId();
            String appointmentType = AppointmentsTable.getSelectionModel().getSelectedItem().getType();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the following appointment -> ID: " +
                    appointmentID + ", Type: " + appointmentType + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentDAO.deleteAppointment(appointmentID);
                AppointmentDAO.getAllAppointments().remove(AppointmentsTable.getSelectionModel().getSelectedItem());
                AppointmentsTable.getSelectionModel().clearSelection();
            }
        }
            catch (NullPointerException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Select a appointment to remove.");
                Optional<ButtonType> result = alert.showAndWait();
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * returns to main menu screen
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionMainMenu(ActionEvent event)
    {
        try {
            changeWindow("/ViewsAndControllers/MainMenu.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes to the update appointment screen
     * the user selects the appointment to be updated using the tableview
     * @param event button pressed
     * @throws NullPointerException if no appointment is selected
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionModify(ActionEvent event) {

        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ViewsAndControllers/UpdateAppointment.fxml" ));
            loader.load();

            UpdateAppointmentController updateAppointmentController = loader.getController();
            updateAppointmentController.sendAppointment(AppointmentsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Select a appointment to update");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * sets the tableView to display all appointments by default when screen opens
     */
    @FXML void initialize()
    {
        AppointmentsTable.setItems(AppointmentDAO.getAllAppointments());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("ContactName"));

    }

    /**
     * gets the contact name that matches the contactID
     * @param contactID int contactID
     * @return String contact name
     */
    public String getContactName(int contactID)
    {
        String contactName = null;
        for (Contact contact : ContactDAO.getAllContacts())
            if (contact.getContactID() == contactID)
                contactName = contact.getContactName();
        return contactName;
    }

    /**
     * filters the appointments by Month
     * uses lambda to filter appointments based on the month selected by datepicker
     * @return ObservableList of filtered appointments
     */
    public ObservableList<Appointment> filteredByMonthStream()
    {
        return AppointmentDAO.getAllAppointments().stream()
                .filter( a -> a.getStart().toLocalDate().get(ChronoField.MONTH_OF_YEAR) == datePicker.getValue().get(ChronoField.MONTH_OF_YEAR))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

    }

    /**
     * filters the appointments by Week
     * uses lambda to filter appointments based on the week selected by datepicker
     * @return ObservableList of filtered appointments
     */
    public ObservableList<Appointment> filteredByWeekStream()
    {
        return AppointmentDAO.getAllAppointments().stream()
                .filter( a -> a.getStart().toLocalDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR) == datePicker.getValue().get(ChronoField.ALIGNED_WEEK_OF_YEAR))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

    }



}
