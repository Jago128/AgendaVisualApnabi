package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddWindowController implements Initializable {

    @FXML
    private TextField textFieldTitle;

    @FXML
    private TextField textFieldPerson;

    @FXML
    private TextArea textAreaInstruction;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnExit;

    private Controller cont;
    private MainWindowController mainCont;

    public void setController(Controller cont) {
        this.cont = cont;
    }

    public void setMainWindowController(MainWindowController mainCont) {
        this.mainCont = mainCont;
    }

    @FXML
    private void add(ActionEvent event) {
        String title = textFieldTitle.getText();
        String person = textFieldPerson.getText();
        String instruction = textAreaInstruction.getText();

        if (title == null || title.trim().isEmpty() || instruction == null || instruction.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Error de validacion", "Faltan campos", "Por favor, rellena todos los campos.");
        } else {
            boolean result = cont.addRoutine(title, person, instruction);
            if (result) {
                Alert alert = showAlert(AlertType.CONFIRMATION, "Rutina añadida correctamente",
                        "La rutina con el titulo " + title + " ha sido añadida correctamente.", "¿Quieres añadir mas rutinas?");
                mainCont.loadAllGames();
                if (alert.getResult().equals(ButtonType.OK)) {
                    textFieldTitle.clear();
                    textFieldPerson.clear();
                    textAreaInstruction.clear();
                } else if (alert.getResult().equals(ButtonType.CANCEL)) {
                    Stage stage = (Stage) btnAdd.getScene().getWindow();
                    stage.close();
                }

            } else {
                showAlert(AlertType.ERROR, "ERROR", "",
                        "Ha occurrido un error al añadir la rutina. Puede que ya exista en la base de datos una rutina con el mismo titulo para la misma persona.");
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
    }

}
