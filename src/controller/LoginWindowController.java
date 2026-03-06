package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Profile;

/**
 * Controller for Login Window. Handles login to application.
 *
 * @author Jago128
 */
public class LoginWindowController implements Initializable {

    @FXML
    private TextField textfieldName;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField passFieldVisible;

    @FXML
    private CheckBox cbShowPass;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnExit;

    private final Controller cont = new Controller();
    private HostServices hostServices;

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    /**
     * Changes states of the visible and obscured password fields.
     *
     * @param event The checkbox toggle event.
     */
    @FXML
    private void showPass(ActionEvent event) {
        if (cbShowPass.isSelected()) {
            passField.setVisible(false);
            passFieldVisible.setVisible(true);
        } else {
            passFieldVisible.setVisible(false);
            passField.setVisible(true);
        }
    }

    /**
     * Handles login to application. Opens Main window if successful, shows an error on fail.
     *
     * @param event The button click event.
     */
    @FXML
    private void login(ActionEvent event) {
        String username = textfieldName.getText();
        String password = passField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Error de validacion", "Faltan campos", "Por favor, rellena todos los campos.");
        } else {
            Profile profile = cont.login(username, password);
            if (profile != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
                    Parent root = loader.load();
                    MainWindowController mainCont = loader.getController();
                    mainCont.setController(cont);
                    mainCont.setUser(profile);
                    mainCont.setHostServices(hostServices);
                    mainCont.setUpList();
                    Stage stage = new Stage();
                    stage.setTitle("Ventana Principal");
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert(AlertType.ERROR, "Error de validacion", "Login fallido", "El nombre o contraseña son incorrectos.");
            }
        }
    }

    /**
     * Opens the Sign Up window.
     *
     * @param event The button click event.
     */
    @FXML
    private void signUpWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpWindow.fxml"));
            Parent root = loader.load();
            SignUpWindowController signUpCont = loader.getController();
            signUpCont.setController(cont);
            signUpCont.setHostServices(hostServices);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Registro");
            stage.setScene(scene);
            stage.show();
            Stage current = (Stage) btnSignUp.getScene().getWindow();
            current.close();
        } catch (IOException e) {
            e.printStackTrace();
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
     * Closes the application by closing the window.
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
        passField.textProperty().addListener((observable, oldVal, newVal) -> passFieldVisible.setText(newVal));
        passFieldVisible.textProperty().addListener((observable, oldVal, newVal) -> passField.setText(newVal));
    }
}
