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

/**
 *
 * @author Jago128
 */
public class DeleteUserWindowController implements Initializable {

    @FXML
    private Label labelUsername;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField passFieldVisible;

    @FXML
    private CheckBox cbShowPass;

    @FXML
    private Button delete;

    @FXML
    private Button btnExit;

    private Controller cont;
    private Profile user;
    private Stage main;

    /**
     *
     * @param cont
     */
    public void setController(Controller cont) {
        this.cont = cont;
    }

    /**
     *
     * @param profile
     */
    public void setUser(Profile profile) {
        this.user = profile;
        labelUsername.setText(user.getUsername());
    }

    /**
     *
     * @param main
     */
    public void setMainStage(Stage main) {
        this.main = main;
    }

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

    @FXML
    private void delete(ActionEvent event) {
        String username = labelUsername.getText();
        String password = passField.getText();

        if (passField.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "ERROR", "Conreseña requerida", "Introduce tu contraseña para borrar tu cuenta.");
        } else {
            Alert warning = showAlert(AlertType.CONFIRMATION, "AVISO", "¿Estas seguro de que quieres borrar tu cuenta?", "Elija una opcion.");
            if (warning.getResult().equals(ButtonType.OK)) {
                if (cont.deleteAcc(username, password)) {
                    showAlert(AlertType.INFORMATION, "Cuenta eliminada correctamente", "", "Tu cuenta ha sido eliminada correctamente.");
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Ventana Login");
                        stage.setScene(new Scene(root));
                        stage.show();
                        Stage currentStage = (Stage) delete.getScene().getWindow();
                        main.close();
                        currentStage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlert(AlertType.ERROR, "Error de validacion", "", "La contraseña introducida es incorrecta.");
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
