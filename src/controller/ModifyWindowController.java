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
 * Controller for Modify Window. Handles modification of a chosen routine.
 *
 * @author Jago128
 */
public class ModifyWindowController implements Initializable {

    @FXML
    private Button btnModify;

    @FXML
    private TextField textFieldTitle;

    @FXML
    private TextField textFieldPerson;

    @FXML
    private TextArea textAreaInstruction;

    @FXML
    private Button btnExit;

    private Controller cont;
    private Routine routine;
    private Profile user;
    private MainWindowController mainCont;

    /**
     * Sets up the app controller instance.
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
     * Sets up the routine that is to be modified.
     *
     * @param routine The routine to be modified.
     */
    public void setRoutineToModify(Routine routine) {
        this.routine = routine;
        loadData();
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
     * Loads the routine's data into the fields.
     */
    private void loadData() {
        textFieldTitle.setText(routine.getTitle());
        textFieldPerson.setText(routine.getPerson());
        textAreaInstruction.setText(routine.getInstruction());
    }

    /**
     * Calls the database method to modify the routine. Method has checks for empty fields and duplicate prevention, as well as making sure there's a change in any field.
     *
     * @param event The button click event.
     */
    @FXML
    private void modify(ActionEvent event) {
        String title = textFieldTitle.getText();
        String person = textFieldPerson.getText();
        String instruction = textAreaInstruction.getText();

        if (title == null || title.trim().isEmpty() || instruction == null || instruction.trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Error de validacion", "Faltan campos", "Por favor, rellena todos los campos.");
        } else {
            if (routine.getTitle().equals(title) && routine.getPerson().equals(person) && routine.getInstruction().equals(instruction)) {
                showAlert(AlertType.WARNING, "Error de validacion", "Campos no cambiados", "Por favor, cambia algun campo.");
            } else {
                if (cont.routineExists(title, person)) {
                    showAlert(AlertType.WARNING, "Error de validacion", "", "Ya existe una rutina con el mismo titulo para la misma persona.");
                } else {
                    if (cont.modifyRoutine(new Routine(title, person, instruction, (User) user))) {
                        mainCont.loadAllRoutines();
                        showAlert(AlertType.INFORMATION, "Rutina " + title + " modificada correctamente",
                                "", "La rutina con el titulo " + title + " ha sido modificada correctamente.");
                        Stage stage = (Stage) btnModify.getScene().getWindow();
                        stage.close();
                    } else {
                        showAlert(AlertType.ERROR, "ERROR", "", "Ha occurrido un error al modificar la rutina.");
                    }
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
     */
    private void showAlert(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);

        if (header == null || !header.isEmpty()) {
            alert.setHeaderText(header);
        }

        alert.setContentText(content);
        alert.showAndWait();
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
