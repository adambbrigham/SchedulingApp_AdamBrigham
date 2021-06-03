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
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * this class controls the report for contact schedules
 * @author adamb
 */
public class ScheduleContactController
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
    private Button MainMenuButton;

    @FXML
    private ComboBox<Contact> contactsCombo;

    /**
     * Sets the tableview to appointments filtered by selected contact
     * @param event comboBox selection
     */
    @FXML
    void contactsCombo(ActionEvent event)
    {
        Contact selectedContact = contactsCombo.getSelectionModel().getSelectedItem();
        if (ApptsByContact(selectedContact).isEmpty())
            AppointmentsTable.setPlaceholder(new Label("No appointments scheduled with this contact"));
        else
        {
            AppointmentsTable.setItems(ApptsByContact(selectedContact));
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
    }

    @FXML
    void onActionApptsTable(ActionEvent event) {

    }

    /**
     * changes screen to the main menu
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
     * sets the contacts comboBox with all customers and gives user instructions in tableview
     */
    @FXML
    void initialize()
    {
        contactsCombo.setItems(ContactDAO.getAllContacts());
        AppointmentsTable.setPlaceholder(new Label("Select a contact to view his/her appointments"));
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
     * Filters appointments by Contact
     * uses lambda to filter appointments by contact, implements Predicate interface
     * @param contact Contact selected as filter
     * @return ObservableList of filtered Appointments
     */
    public ObservableList<Appointment> ApptsByContact(Contact contact)
    {
        return AppointmentDAO.getAllAppointments().stream()
                .filter( c -> c.getContactID() == contact.getContactID())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
