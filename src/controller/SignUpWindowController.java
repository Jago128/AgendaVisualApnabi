package controller;

import exception.EmailFormatException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
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

    public void setController(Controller cont) {
        this.cont = cont;
    }

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
    private void signUp(ActionEvent event) {
        //DB method
    }

    public void emailFormatCheck(String email) throws EmailFormatException {
        Pattern modelo = Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = modelo.matcher(email);
        if (!matcher.matches()) {
            throw new EmailFormatException();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxGender.getItems().addAll(Gender.values());
    }
}
