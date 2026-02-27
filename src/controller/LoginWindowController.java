package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
