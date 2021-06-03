package ViewsAndControllers;

import Appointments.Appointment;
import Appointments.AppointmentDAO;
import Customers.Customer;
import Customers.CustomerDAO;
import Users.User;
import Users.UserDAO;
import Utilities.ResourceBundles;
import Utilities.TimeFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class to control the login screen
 * User inputs username and password, and those are verified against the database
 */
public class LoginController {

    Stage stage;
    Parent scene;

        @FXML
        private TextField userNameText;

        @FXML
        private TextField passwordText;

        @FXML
        private Button OkButton;

        @FXML
        private Button cancelButton;

        @FXML
        private Label ZoneIDField;

        @FXML
        private Label LabelUserName;

        @FXML
        private Label LabelPassword;

    public LoginController() throws SQLException {
    }

    /**
     * cancels login attempt and exits application
     * @param event button pressed
     */
    @FXML
        void onActionCancel(ActionEvent event)
        {
            String exit = "Exit application";
            if(locale.test(getLocale()))
            {
                exit = ResourceBundles.getResource().getString("Exit")
                        + " " + ResourceBundles.getResource().getString("application") + "?";
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, exit);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
                System.exit(0);
        }

    /**
     * Varifies the username and password against the database
     * If user is validated, it is checked if there are appointments within 15 minutes and changes screen to main menu
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
        @FXML
        void onActionOK(ActionEvent event) {

            LocalDateTime dateTime = TimeFiles.getTimeNow();
            boolean success = false;
            String FailedLogin = "Failed Login Attempt";
            if(locale.test(getLocale()))
            {
                FailedLogin = ResourceBundles.getResource().getString("Failed")
                        + " " + ResourceBundles.getResource().getString("Login")
                        + " " + ResourceBundles.getResource().getString("Attempt");
            }

            if (validateUserStream(userNameText.getText(), passwordText.getText()))
            {
                success = true;
                UserDAO.setCurrentUserID(userNameText.getText());
                TimeFiles.timeOfUserLogin = LocalDateTime.now();

                if (checkFor15MinAppt().isPresent())
                {
                    int customerID = checkFor15MinAppt().get().getCustomerID();
                    int appointmentID = checkFor15MinAppt().get().getId();
                    LocalDate date = checkFor15MinAppt().get().getStart().toLocalDate();
                    LocalTime time = checkFor15MinAppt().get().getStart().toLocalTime();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID " + appointmentID
                            + " with " + getCustomerName(customerID)
                            + " is scheduled on " + date + " at " + time + ".");
                    alert.getDialogPane().setMinWidth(600);
                    Optional<ButtonType> result = alert.showAndWait();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No appointments within 15 minutes.");
                    Optional<ButtonType> result = alert.showAndWait();
                }

                try {
                    changeWindow("/ViewsAndControllers/MainMenu.fxml" ,event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, FailedLogin);
                Optional<ButtonType> result = alert.showAndWait();

            }
            String userLoginInfo = "Username: " + userNameText.getText() + " | Password: " + passwordText.getText();
            try {
                loginAttempt(userLoginInfo,dateTime,success); // create file for login attempt
            } catch (IOException e) {
                e.printStackTrace();
            }
            //readloginFile();  // method for report. Test only
        }

        @FXML
        void onActionPasswordText(ActionEvent event) {

        }

        @FXML
        void onActionUserNameText(ActionEvent event) {

        }

    /**
     * sets text based on default zoneID
     */
    @FXML
        void initialize(){
            // set label to user location
            ZoneId location = ZoneId.systemDefault();
            ZoneIDField.setText(location.toString());

            if (locale.test(getLocale()))
            {
                OkButton.setText(ResourceBundles.getResource().getString("OK"));
                cancelButton.setText((ResourceBundles.getResource().getString("cancel")));
                LabelPassword.setText(ResourceBundles.getResource().getString("Password"));
                LabelUserName.setText(ResourceBundles.getResource().getString("Username"));
            }
        }
        Predicate<String> locale = (l -> l.equals("fr"));

    /**
     * gets the Locale of the user and the appropriate language
     * @return Locale language
     */
    public String getLocale()
        {
            return Locale.getDefault().getLanguage();
        }

    /**
     * Validates the user by comparing the username and password against the database
     * uses lambda to filter user by username
     * uses lambda to filter user by password
     * @param u String username
     * @param p String password
     * @return boolean true if user is validated
     */
    boolean validateUserStream(String u, String p)
    {
        Optional<User> user = UserDAO.getAllUsers()
                .stream()
                .filter(obj -> obj.getUserName().equals(u))
                .filter(obj -> obj.getPassword().equals(p))
                .findAny();
        return user.isPresent();
    }

    /**
     * Makes a login_activity file
     * login activity records the username and password, success, and timestamp
     * @param s String username and password information
     * @param d LocalDateTime time of login attempt
     * @param b boolean true if user was validated
     * @throws IOException if error reading or writing to login_activity file
     */
    void loginAttempt(String s, LocalDateTime d, boolean b) throws IOException {
        String success = "Login successful";
        String fail = "login unsuccessful";
        String report;

        if (b)
            report = success;
        else
            report = fail;

        // FileWriter can append data to existing file by using true as argument
        FileWriter fwriter = new FileWriter("login_activity.txt", true);
        // use PrintWriter to use println method. use FileWriter instance as argument
        PrintWriter logFile = new PrintWriter(fwriter);
        logFile.print(s + " | " + report + " | " + d + "\n");

        // close file
        logFile.close();
    }

    /**
     * method to read the login_activity file to console. not used for application
     * @throws FileNotFoundException if there is an error reading login_activity file
     */
    void readloginFile() throws FileNotFoundException {
        File myFile = new File("login_activity.txt");
        // pass reference to File as argument to Scanner class constructor, using fileName as input
        Scanner inputFile = new Scanner(myFile);

        // Read lines from file until there are no more left
        while (inputFile.hasNext())
        {
            // Read next line
            String LineFromFile = inputFile.nextLine();

            // Display the line
            System.out.println(LineFromFile);
        }

        // close file
        inputFile.close();
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
     * checks if there is an appointment within 15 minutes
     * if the user is validated, this method checks if he/she has an appointment within 15 minutes of login
     * uses lambda to filter appointments by user
     * uses lambda to filter appointments by start time and +15 minutes
     * uses lambda to filter appointments by start time
     * @return Optional can have an Appointment or be null
     */
    public Optional<Appointment> checkFor15MinAppt()
    {
        ObservableList<Appointment> appointmentsWithUser = AppointmentDAO.getAllAppointments().stream()
                .filter( a -> a.getUserID() == UserDAO.getCurrentUserID())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        return appointmentsWithUser.stream()
                .filter(p -> p.getStart().isBefore(TimeFiles.timeOfUserLogin.plusMinutes(15)))
                .filter(q -> q.getStart().isAfter(TimeFiles.timeOfUserLogin))
                .findAny();
    }

    /**
     * gets the customerName from the customerID
     * @param customerID int customerID to determine name
     * @return String customerName
     */
    public String getCustomerName(int customerID)
    {
        String customerName = null;
        for ( Customer customer : CustomerDAO.getAllCustomers())
        {
            if (customer.getCustomerID() == customerID)
                customerName = customer.getCustomerName();
        }
        return customerName;
    }

}

