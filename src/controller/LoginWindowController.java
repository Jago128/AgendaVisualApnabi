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
    private TextField nameField;

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

    private final Controller cont = new Controller();

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

    @FXML
    private void login(ActionEvent event) {
        //DB method
        Profile profile;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            Parent root = loader.load();
            MainWindowController mainCont = loader.getController();
            mainCont.setController(cont);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Principal");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
            stage.setTitle("Ventana Principal");
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
