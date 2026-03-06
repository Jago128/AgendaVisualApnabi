package controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.*;
import javax.imageio.ImageIO;
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

    @FXML
    private Button imageSelector;

    @FXML
    private Label labelFiles;

    @FXML
    private Hyperlink link;
    
    @FXML
    private ListView<Image> listImages;


    private Controller cont;
    private Routine routine;
    private MainWindowController mainCont;
    private HostServices hostServices;
    private List<File> savedFiles;
    
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    /**
     * Sets up the app controller instance.
     *
     * @param cont The controller instance.
     */
    public void setController(Controller cont) {
        this.cont = cont;
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

    @FXML
    private void selectImages(ActionEvent event) {
        savedFiles.clear();
        FileChooser selection = new FileChooser();
        selection.setInitialDirectory(new File(System.getProperty("user.home")));
        selection.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpeg;*.jpg;*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        List<File> files = selection.showOpenMultipleDialog(imageSelector.getScene().getWindow());
        if (files != null) {
            StringBuilder fileString = new StringBuilder("Imagenes seleccionadas: ");
            BufferedImage image;
            for (int i = 0; i < files.size(); i++) {
                File img = new File("./src/img", files.get(i).getName());
                if (!img.exists()) {
                    img.getParentFile().mkdirs();
                }

                try {
                    if (i != files.size() - 1) {
                        image = ImageIO.read(files.get(i).getAbsoluteFile());
                        ImageIO.write(image, "png", img);
                        fileString.append(files.get(i).getName()).append(", ");
                        files.add(img);
                    } else {
                        image = ImageIO.read(files.get(i));
                        ImageIO.write(image, "png", img);
                        fileString.append(files.get(i).getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(AlertType.ERROR, "ERROR", "", "Ha ocurrido un error al leer las imagenes.");
                    files.clear();
                }
            }
            labelFiles.setText(fileString.toString());
        }
    }

    @FXML
    private void openBrowser(ActionEvent event) {
        hostServices.showDocument("https://beta.arasaac.org/pictograms/search");
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
                    routine.setTitle(title);
                    routine.setPerson(person);
                    routine.setInstruction(instruction);
                    if (cont.modifyRoutine(routine)) {
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
        savedFiles = new ArrayList<>();
        
    }
}
