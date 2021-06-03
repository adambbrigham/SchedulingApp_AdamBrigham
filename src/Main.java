import Appointments.AppointmentDAO;
import Contacts.ContactDAO;
import Countries.CountryDAO;
import Customers.CustomerDAO;
import FirstLevelDivisions.FirstLevelDivisionDAO;
import Users.UserDAO;
import Utilities.dbconnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

/**
 * This class is used to launch the application
 * @author adamb
 */
public class Main extends Application {

    /**
     * This method creates the scene and displays it
     * @param scene the scene for this application
     * @throws Exception if fxml file is not found
     */
    @Override
    public void start(Stage scene) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/Login.fxml"));

        scene.setScene(new Scene(root));
        scene.show();
    }

    /**
     * launches application
     * Establishes connection with database and populates all ObservableArrayLists
     * @param args arg parameters
     * @throws SQLException if there is an error querying the database
     */
    public static void main(String[] args) {

        //Locale.setDefault(new Locale("fr","ca")); // for testing

        dbconnection.startConnection();

        try {
            CustomerDAO.addAllCustomers();
            CountryDAO.addAllCountries();
            ContactDAO.addAddContacts();
            AppointmentDAO.addAllAppointments();
            FirstLevelDivisionDAO.addAllDivisions();
            UserDAO.addAllUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        launch(args);
        dbconnection.closeConnection();
    }
}
