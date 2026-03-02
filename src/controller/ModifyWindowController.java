package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class ModifyWindowController implements Initializable {

    @FXML
    private TextField textFieldTitle;

    @FXML
    private TextArea textAreaInstruction;

    @FXML
    private Button btnModify;

    private Controller cont;

    public void setController(Controller cont) {
        this.cont = cont;
    }

    @FXML
    private void modify(ActionEvent event) {
        //DB
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
