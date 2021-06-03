package ViewsAndControllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class for controlling the reports main menu
 * @author adamb
 */
public class ReportsMainController {

    Stage stage;
    Parent scene;

    @FXML
    private Button CustomersApptsButton;

    @FXML
    private Button ScheduleByContactButton;

    @FXML
    private Button StatisticsButton;

    @FXML
    private Button MainMenuButton;

    /**
     * changes screen to the appointments by customer screen
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionCustomersAppts(ActionEvent event) {
        try {
            changeWindow("/ViewsAndControllers/AppointmentsByCustomer.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes screen to the main menu
     * @param event button pressed
     * @throws IOException if fxml file is not found
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
     * changes screen to the Customers statistics screen
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionStatistics(ActionEvent event) {
        try {
            changeWindow("/ViewsAndControllers/CustomerStatistics.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes screen to the schedule by contact screen
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionScheduleByContact(ActionEvent event) {
        try {
            changeWindow("/ViewsAndControllers/ScheduleContact.fxml",event);
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
}
