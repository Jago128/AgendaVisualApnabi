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
    private Label labelFiles;

    @FXML
    private Button imageSelector;

    @FXML
    private Hyperlink link;

    @FXML
    private TextArea textAreaInstruction;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnExit;

    @FXML
    private ListView<Image> listImages;

    private Controller cont;
    private Profile user;
    private MainWindowController mainCont;
    private HostServices hostServices;
    private List<File> savedFiles;

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

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
        List <File> files = selection.showOpenMultipleDialog(imageSelector.getScene().getWindow());
        if (!files.isEmpty()) {
            StringBuilder fileString = new StringBuilder("Imagenes seleccionadas: ");
            BufferedImage image;
            for (int i = 0; i < files.size(); i++) {
                File img = new File("./src/img", files.get(i).getName());
                if (!img.exists()) {
                    img.getParentFile().mkdirs();
                }

                try {
                    if (i != files.size() - 1) {
                        image = ImageIO.read(files.get(i));
                        ImageIO.write(image, "png", img);
                        fileString.append(files.get(i).getName()).append(", ");
                        files.add(img);

                    } else {
                        image = ImageIO.read(files.get(i));
                        ImageIO.write(image, "png", img);
                        fileString.append(files.get(i).getName());
                        files.add(img);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(AlertType.ERROR, "ERROR", "", "Ha ocurrido un error al leer las imagenes.");
                    files.clear();
                }
            }
            savedFiles.addAll(files);
            labelFiles.setText(fileString.toString());
        }
    }

    @FXML
    private void openBrowser(ActionEvent event) {
        hostServices.showDocument("https://beta.arasaac.org/pictograms/search");
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
                if (cont.addRoutine(title, person, instruction, (User) user, savedFiles)) {
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
        savedFiles = new ArrayList<>();
    }
}
