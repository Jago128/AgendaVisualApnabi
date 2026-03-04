package controller;

import exception.EmailFormatException;
import exception.PasswordCheckFailException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;

/**
 * Controller for User Config Window. Handles modifications of the user's account.
 *
 * @author Jago128
 */
public class UserConfigWindowController implements Initializable {

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
    private Button btnModify;

    @FXML
    private Button btnExit;

    private Controller cont;
    private User user;

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
    public void setUser(User profile) {
        this.user = profile;
        textFieldName.setText(user.getUsername());
    }

    /**
     * Changes states of the visible and obscured password fields.
     *
     * @param event The checkbox click event.
     */
    @FXML
    private void showPass(ActionEvent event) {
        if (cbShowPass.isSelected()) {
            passFieldVisible.setText(passField.getText());
            passField.setVisible(false);
            passFieldVisible.setVisible(true);
        } else {
            passField.setText(passFieldVisible.getText());
            passFieldVisible.setVisible(false);
            passField.setVisible(true);
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
     * Calls database method to modify the user account. Has checks for empty fields, if all fields are unchanged, incorrect email format, mismatched repeat password or short passwords, and duplicate prevention.
     *
     * @param event The button click event.
     */
    @FXML
    public void modify(ActionEvent event) {
        String name = textFieldName.getText();
        String surname = textFieldSurname.getText();
        String email = textFieldEmail.getText();
        Gender gender = comboBoxGender.getSelectionModel().getSelectedItem();
        String pass = passField.getText();
        String passRepeat = passFieldRepeat.getText();

        if (name.trim().isEmpty() || surname.trim().isEmpty() || email.trim().isEmpty() || gender == null || pass.trim().isEmpty() || passRepeat.trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Error de validacion", "Faltan campos", "Por favor, rellena todos los campos.");
        } else {
            if (name.equals(user.getUsername()) && surname.equals(user.getSurname()) && email.equals(user.getEmail())
                    && gender.equals(user.getGender()) && pass.equals(user.getPassword())) {
                showAlert(AlertType.WARNING, "Sin cambios", "No hay ningun cambio", "Se debe hacer al menos un cambio antes de modificar tu cuenta.");
            } else {
                try {
                    if (!email.equals(user.getEmail())) {
                        emailFormatCheck(email);
                    }

                    if (!pass.equals(user.getPassword())) {
                        passCheck(pass, passRepeat);
                    }

                    if (cont.userExists(name)) {
                        showAlert(AlertType.WARNING, "Error de validacion", "", "Ya existe un usuario con el mismo nombre.");
                    } else {
                        if (cont.modifyAcc(name, surname, pass, email, gender)) {
                            showAlert(AlertType.INFORMATION, "Cuenta modificada", "Modificacion exitosa!", "Tu cuenta ha sido modificada correctamente.");
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
        Stage current = (Stage) btnExit.getScene().getWindow();
        current.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxGender.getItems().addAll(Gender.values());
        passField.textProperty().addListener((observable, oldVal, newVal) -> passFieldVisible.setText(newVal));
        passFieldVisible.textProperty().addListener((observable, oldVal, newVal) -> passField.setText(newVal));
    }
}
