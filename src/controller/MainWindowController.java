package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import model.*;

/**
 * Controller for Main Window. Handles showing all available routines, navigation to other windows, and deletion of routines.
 *
 * @author Jago128
 */
public class MainWindowController implements Initializable {

    @FXML
    private Button tempBtn;

    @FXML
    private TableView<Routine> tableAgenda;

    @FXML
    private TableColumn<Routine, String> colTitle;

    @FXML
    private TableColumn<Routine, String> colPersona;

    @FXML
    private TableColumn<Routine, String> colInstruction;

    @FXML
    private MenuButton userMenu;

    @FXML
    private MenuItem modifyUser;

    @FXML
    private MenuItem logOut;

    @FXML
    private MenuItem deleteAcc;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExit;

    private Controller cont;
    private Profile user;
    private ObservableList<Routine> routines;
    private Routine selected;
    private HostServices hostServices;

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
     * Sets up the user profile and username on the user menu button.
     *
     * @param profile The logged in user's profile.
     */
    public void setUser(Profile profile) {
        this.user = profile;
        userMenu.setText(user.getUsername());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cont = new Controller();
        configureTableColumns();
        setUpList();
        tableAgenda.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> getSelectedTableItem()
        );
    }

    /**
     * Sets up the list for the routines table.
     */
    public void setUpList() {
        routines = FXCollections.observableArrayList();
        routines.setAll(cont.getRoutines());
        tableAgenda.setItems(routines);
    }

    /**
     * Configures all table columns with the respective parameters.
     */
    private void configureTableColumns() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPersona.setCellValueFactory(new PropertyValueFactory<>("person"));
        colInstruction.setCellValueFactory(new PropertyValueFactory<>("instruction"));
    }

    /**
     * Gets the currently selected table item.
     */
    private void getSelectedTableItem() {
        selected = selected = tableAgenda.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void debug(ActionEvent event) {
        //API check
    }

    /**
     * Reloads all routines into the table.
     */
    public void loadAllRoutines() {
        routines.setAll(cont.getRoutines());
    }

    /**
     * Opens the modify user window.
     *
     * @param event The button click event.
     */
    @FXML
    private void modifyUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserConfigWindow.fxml"));
            Parent root = loader.load();
            UserConfigWindowController userConfigCont = loader.getController();
            userConfigCont.setController(cont);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Configuracion Usuario");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnModify.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the delete user account window.
     *
     * @param event The button click event.
     */
    @FXML
    private void deleteAcc(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DeleteUserWindow.fxml"));
            Parent root = loader.load();
            DeleteUserWindowController delCont = loader.getController();
            delCont.setController(cont);
            delCont.setUser(user);
            Stage current = (Stage) btnDelete.getScene().getWindow();
            delCont.setMainStage(current);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Borrar Cuenta");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnDelete.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs the user out by sending them back to the Login window.
     *
     * @param event The button click event.
     */
    @FXML
    private void logOut(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Login");
            stage.setScene(scene);
            stage.show();
            Stage current = (Stage) tableAgenda.getScene().getWindow();
            current.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the add routine window.
     *
     * @param event The button click event.
     */
    @FXML
    private void add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddWindow.fxml"));
            Parent root = loader.load();
            AddWindowController addCont = loader.getController();
            addCont.setController(cont);
            addCont.setUser(user);
            addCont.setMainWindowController(this);
            addCont.setHostServices(hostServices);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Añadir");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the modify routine window if a routine is selected, otherwise shows a warning message.
     *
     * @param event The button click event.
     */
    @FXML
    private void modify(ActionEvent event) {
        if (selected == null) {
            showAlert(AlertType.WARNING, "ERROR!", "¡Sin seleccion!", "Selecciona una rutina antes de usar esta funcion.");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyWindow.fxml"));
                Parent root = loader.load();
                ModifyWindowController modifyCont = loader.getController();
                modifyCont.setController(cont);
                modifyCont.setMainWindowController(this);
                modifyCont.setRoutineToModify(selected);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Ventana Modificar");
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) event.getSource()).getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Attempts to delete a routine if one is selected, otherwise shows a warning message.
     *
     * @param event The button click event.
     */
    @FXML
    private void delete(ActionEvent event) {
        if (selected == null) {
            showAlert(AlertType.WARNING, "ERROR!", "¡Sin seleccion!", "Selecciona una rutina antes de usar esta funcion.");
        } else {
            Alert warning = showAlert(AlertType.CONFIRMATION, "AVISO",
                    "¿Estas seguro de que quieres borrar la rutina con el titulo " + selected.getTitle() + "?", "Elija una opcion.");
            if (warning.getResult().equals(ButtonType.OK)) {
                if (cont.deleteRoutine(selected)) {
                    showAlert(AlertType.INFORMATION, "Rutina eliminada", "", "La rutina con titulo " + selected.getTitle() + " eliminada correctamente.");
                    loadAllRoutines();
                } else {
                    showAlert(AlertType.ERROR, "ERROR", "Rutina no encontrada", "No se ha encontrado la rutina.");
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
     * Closes the application by closing the window.
     *
     * @param event The button click event.
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
}
