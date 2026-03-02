package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Profile;

public class LoginWindowController implements Initializable {

    @FXML
    private TextField textfieldName;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordFieldVisible;

    @FXML
    private CheckBox cbShowPass;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    private final Controller cont = new Controller();

    @FXML
    private void showPass(ActionEvent event) {
        if (cbShowPass.isSelected()) {
            passwordFieldVisible.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordFieldVisible.setVisible(true);
        } else {
            passwordField.setText(passwordFieldVisible.getText());
            passwordFieldVisible.setVisible(false);
            passwordField.setVisible(true);
        }
    }

    @FXML
    private void login(ActionEvent event) {
        String username = textfieldName.getText();
        String password = passwordField.getText();
        if (username.equals("") || password.equals("")) {
            showAlert(AlertType.ERROR, "Error de validacion", "Faltan campos", "Por favor, rellena todos los campos.");
        } else {
            Profile profile = cont.login(username, password);
            if (profile != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
                    Parent root = fxmlLoader.load();
                    MainWindowController controllerWindow = fxmlLoader.getController();
                    controllerWindow.setUser(profile);
                    controllerWindow.setController(cont);
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

    @FXML
    private void signUpWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpWindow.fxml"));
            Parent root = loader.load();
            MainWindowController mainCont = loader.getController();
            mainCont.setController(cont);
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

    private void showAlert(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);

        if (header == null || !header.isEmpty()) {
            alert.setHeaderText(header);
        }

        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
