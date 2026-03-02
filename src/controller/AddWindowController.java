package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

public class AddWindowController implements Initializable {

    @FXML
    private TextField textFieldTitle;

    @FXML
    private TextArea textAreaInstruction;

    @FXML
    private Button btnAdd;

    private Controller cont;

    public void setController(Controller cont) {
        this.cont = cont;
    }

    @FXML
    private void add(ActionEvent event) {
        //DB
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
