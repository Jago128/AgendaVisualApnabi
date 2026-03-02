package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
