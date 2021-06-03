package ViewsAndControllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Class for controlling main menu screen
 * @author adamb
 */
public class MainController
{
    Stage stage;
    Parent scene;


    @FXML
    private Button CustomersButton;

    @FXML
    private Button ApptsButton;

    @FXML
    private Button ReportsButton;

    @FXML
    private Button ExitButton;

    /**
     * changes screen to the Appointments main screen
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionAppts(ActionEvent event) {
        try {
            changeWindow("/ViewsAndControllers/AppointmentsMain.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes screen to the Customers main screen
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionCustomers(ActionEvent event) {

        try {
            changeWindow("/ViewsAndControllers/CustomersMain.fxml" ,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the application
     * @param event button pressed
     */
    @FXML
    void onActionExit(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit Application?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
            System.exit(0);
    }

    /**
     * changes screen to the Reports main screen
     * @param event button pressed
     * @throws IOException if fxml file is not found
     */
    @FXML
    void onActionReports(ActionEvent event) {
        try {
            changeWindow("/ViewsAndControllers/ReportsMain.fxml" ,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){

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
