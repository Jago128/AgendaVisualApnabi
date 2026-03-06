package controller;

import exception.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.*;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;

/**
 *
 * @author Jago128
 */
public class SignUpWindowController implements Initializable {

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private ComboBox<Gender> comboBoxGender;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField passFieldVisible;

    @FXML
    private PasswordField passFieldRepeat;

    @FXML
    private TextField passFieldRepeatVisible;

    @FXML
    private CheckBox cbShowPass;

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnExit;

    private Controller cont;
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
     * Calls the database method to sign up to the application. Has checks for empty fields, incorrect email format, mismatched repeat password or short passwords, and duplicate prevention.
     *
     * @param event The button click event.
     */
    @FXML
    private void signUp(ActionEvent event) {
        String name = textFieldName.getText();
        String surname = textFieldSurname.getText();
        String email = textFieldEmail.getText();
        Gender gender = comboBoxGender.getSelectionModel().getSelectedItem();

        String pass = passField.getText();
        String passRepeat = passFieldRepeat.getText();

        if (name.trim().isEmpty() || surname.trim().isEmpty() || email.trim().isEmpty() || gender == null || pass.trim().isEmpty() || passRepeat.trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Error de validacion", "Faltan campos", "Por favor, rellena todos los campos.");
        } else {
            try {
                emailFormatCheck(email);
                passCheck(pass, passRepeat);
                if (cont.userExists(name)) {
                    showAlert(AlertType.WARNING, "Error de validacion", "", "Ya existe un usuario con el mismo nombre.");
                } else {
                    if (cont.signUp(name, surname, pass, email, gender)) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
                            Parent root = loader.load();
                            MainWindowController mainCont = loader.getController();
                            mainCont.setController(cont);
                            mainCont.setUser(cont.login(name, pass));
                            mainCont.setHostServices(hostServices);
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setTitle("Ventana Principal");
                            stage.setScene(scene);
                            stage.show();
                            Stage current = (Stage) btnSignUp.getScene().getWindow();
                            current.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showAlert(AlertType.ERROR, "ERROR!", "", "Ha ocurrido un error al intentar registrarse.");
                    }
                }
            } catch (EmailFormatException e) {
                showAlert(AlertType.WARNING, "Error de validacion", "Formato Email invalido!", e.getMessage());
            } catch (PasswordCheckFailException e1) {
                showAlert(AlertType.WARNING, "Error de validacion", "", e1.getMessage());
            }
        }
    }

    /**
     * Checks if an email complies with the format, otherwise throws an exception.
     *
     * @param email The email to be checked.
     * @throws EmailFormatException If the email doesn't comply with regex pattern.
     */
    private void emailFormatCheck(String email) throws EmailFormatException {
        Pattern modelo = Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = modelo.matcher(email);
        if (!matcher.matches()) {
            throw new EmailFormatException();
        }
    }

    /**
     * Checks if the password is lengthy enough and if the password and repeat password fields match, otherwise throws an exception.
     *
     * @param pass The password field password.
     * @param passRepeat The repeat password field password.
     * @throws PasswordCheckFailException If pass is too short or the pass fields don't match.
     */
    private void passCheck(String pass, String passRepeat) throws PasswordCheckFailException {
        if (pass.length() < 8) {
            throw new PasswordCheckFailException("La contraseña debe tener al menos 8 caracteres.");
        } else {
            if (!pass.equals(passRepeat)) {
                throw new PasswordCheckFailException();
            }
        }
    }

    /**
     * Changes states of the visible and obscured password fields.
     *
     * @param event The checkbox click event.
     */
    @FXML
    private void showPass(ActionEvent event) {
        if (cbShowPass.isSelected()) {
            passField.setVisible(false);
            passFieldVisible.setVisible(true);

            passFieldRepeat.setVisible(false);
            passFieldRepeatVisible.setVisible(true);
        } else {
            passFieldVisible.setVisible(false);
            passField.setVisible(true);

            passFieldRepeatVisible.setVisible(false);
            passFieldRepeat.setVisible(true);
        }
    }

    /**
     * Opens the Login Window.
     *
     * @param event The button click event.
     */
    @FXML
    private void loginWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Login");
            stage.setScene(scene);
            stage.show();
            Stage current = (Stage) btnLogin.getScene().getWindow();
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
     * @return If the alert type is for confirmation, returns the alert, otherwise returns null.
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
        comboBoxGender.getItems().addAll(Gender.values());

        // For syncing both passfield types
        passField.textProperty().addListener((observable, oldVal, newVal) -> passFieldVisible.setText(newVal));
        passFieldVisible.textProperty().addListener((observable, oldVal, newVal) -> passField.setText(newVal));
        passFieldRepeat.textProperty().addListener((observable, oldVal, newVal) -> passFieldRepeatVisible.setText(newVal));
        passFieldRepeatVisible.textProperty().addListener((observable, oldVal, newVal) -> passFieldRepeat.setText(newVal));
    }
}
