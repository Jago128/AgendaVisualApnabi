package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Profile;

public class DeleteUserWindowController implements Initializable {

    @FXML
    private Label labelUsername;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordFieldVisible;

    @FXML
    private CheckBox cbShowPass;

    @FXML
    private Button delete;

    private Controller cont;
    private Profile user;

    public void setController(Controller cont) {
        this.cont = cont;
    }

    public void setUser(Profile profile) {
        this.user = profile;
        labelUsername.setText(user.getUsername());
    }

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
    private void delete(ActionEvent event) {
        if (cont.login(user.getUsername(), passwordField.getText()) == null) {
            showAlert(AlertType.ERROR, "Error de validacion", "", "La contraseña introducida es incorrecta.");
        } else {
            Alert warning = showAlert(AlertType.CONFIRMATION, "AVISO", "¿Estas seguro de que quieres borrar tu cuenta?", "Elija una opcion.");
            if (warning.getResult().equals(ButtonType.OK)) {
                if (cont.deleteAcc(user.getUsername(), user.getPassword())) {
                    showAlert(AlertType.INFORMATION, "Cuenta eliminada correctamente", "", "Tu cuenta ha sido eliminada correctamente.");
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Ventana Principal");
                        stage.setScene(new Scene(root));
                        stage.show();
                        Stage currentStage = (Stage) delete.getScene().getWindow();
                        currentStage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlert(AlertType.ERROR, "ERROR", "Cuenta no encontrada", "No se ha encontrado la cuenta.");
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Ventana Principal");
                        stage.setScene(new Scene(root));
                        stage.show();
                        Stage currentStage = (Stage) delete.getScene().getWindow();
                        currentStage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
