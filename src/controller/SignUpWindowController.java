package controller;

import exception.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;

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

    private Controller cont;
    @FXML
    private Button btnExit;

    public void setController(Controller cont) {
        this.cont = cont;
    }

    @FXML
    private void signUp(ActionEvent event) {
        String name = textFieldName.getText();
        String surname = textFieldSurname.getText();
        String email = textFieldEmail.getText();
        Gender gender = comboBoxGender.getSelectionModel().getSelectedItem();

        String pass = passField.getText();
        String passRepeat = passFieldRepeat.getText();

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
                        mainCont.setUser(new User(textFieldName.getText(), textFieldSurname.getText(), passField.getText(),
                                textFieldEmail.getText(), comboBoxGender.getSelectionModel().getSelectedItem()));
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

    private void emailFormatCheck(String email) throws EmailFormatException {
        Pattern modelo = Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = modelo.matcher(email);
        if (!matcher.matches()) {
            throw new EmailFormatException();
        }
    }

    private void passCheck(String pass, String passRepeat) throws PasswordCheckFailException {
        if (!pass.equals(passRepeat)) {
            throw new PasswordCheckFailException();
        } else if (pass.length() < 8) {
            throw new PasswordCheckFailException("La contraseña debe tener al menos 8 caracteres.");
        }
    }

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

    private void showAlert(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        if (header == null || !header.isEmpty()) {
            alert.setHeaderText(header);
        }
        alert.setContentText(content);
        alert.showAndWait();
    }

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
