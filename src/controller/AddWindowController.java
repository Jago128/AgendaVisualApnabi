package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;

/**
 * Controller for Add Window. Handles adding new routines to an user and the database.
 *
 * @author Jago128
 */
public class AddWindowController implements Initializable {

    @FXML
    private TextField textFieldTitle;

    @FXML
    private TextField textFieldPerson;

    @FXML
    private TextArea textAreaInstruction;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnExit;

    private Controller cont;
    private MainWindowController mainCont;
    private Profile user;

    /**
     * Sets up the app controller.
     *
     * @param cont The controller instance.
     */
    public void setController(Controller cont) {
        this.cont = cont;
    }

    /**
     * Sets up the user profile.
     *
     * @param profile The logged in user's profile.
     */
    public void setUser(Profile profile) {
        this.user = profile;
    }

    /**
     * Sets up the main window's FXML controller.
     *
     * @param mainCont The main window's FXML controller.
     */
    public void setMainWindowController(MainWindowController mainCont) {
        this.mainCont = mainCont;
    }

    /**
     * Calls the database method to add a routine, with messages for success or failure. Method has checks for empty fields and duplicate prevention.
     *
     * @param event The button click event.
     */
    @FXML
    private void add(ActionEvent event) {
        String title = textFieldTitle.getText();
        String person = textFieldPerson.getText();
        String instruction = textAreaInstruction.getText();

        if (title == null || title.trim().isEmpty() || instruction == null || instruction.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Error de validacion", "Faltan campos", "Por favor, rellena todos los campos.");
        } else {
            if (cont.routineExists(title, person)) {
                showAlert(AlertType.WARNING, "Error de validacion", "", "Ya existe una rutina con el mismo titulo para la misma persona.");
            } else {
                if (cont.addRoutine(title, person, instruction, (User) user)) {
                    mainCont.loadAllRoutines();
                    Alert alert = showAlert(AlertType.CONFIRMATION, "Rutina añadida correctamente",
                            "La rutina con el titulo " + title + " ha sido añadida correctamente.", "¿Quieres añadir mas rutinas?");
                    if (alert.getResult().equals(ButtonType.OK)) {
                        textFieldTitle.clear();
                        textFieldPerson.clear();
                        textAreaInstruction.clear();
                    } else if (alert.getResult().equals(ButtonType.CANCEL)) {
                        Stage stage = (Stage) btnAdd.getScene().getWindow();
                        stage.close();
                    }
                } else {
                    showAlert(AlertType.ERROR, "ERROR", "", "Ha occurrido un error al añadir la rutina.");
                }
            }
        }
    }

    /**
     * Shows an alert based on the parameters given on method call.
     *
     * @param type The type of alert to be created.
     * @param title The title of the alert.
     * @param header The header of the alert.
     * @param content The content of the alert.
     * @return If the alert type is for confirmation, returns the alert, otherwise returns null.
     */
    private Alert showAlert(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);

        if (header == null || !header.isEmpty()) {
            alert.setHeaderText(header);
        }

        alert.setContentText(content);
        alert.showAndWait();
        if (type == AlertType.CONFIRMATION) {
            return alert;
        }

        return null;
    }

    /**
     * Closes the window.
     *
     * @param event The button click event.
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
