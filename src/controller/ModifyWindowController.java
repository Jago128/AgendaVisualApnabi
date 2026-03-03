package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Rutina;

public class ModifyWindowController implements Initializable {

    @FXML
    private Button btnModify;

    @FXML
    private TextField textFieldTitle;

    @FXML
    private TextField textFieldPerson;

    @FXML
    private TextArea textAreaInstruction;

    private Controller cont;
    private Rutina routine;
    private MainWindowController mainCont;

    public void setController(Controller cont) {
        this.cont = cont;
    }

    public void setRoutineToModify(Rutina routine) {
        this.routine = routine;
        loadData();
    }

    public void setMainWindowController(MainWindowController mainCont) {
        this.mainCont = mainCont;
    }
    
    private void loadData() {
        textFieldTitle.setText(routine.getTitle());
        textFieldPerson.setText(routine.getPerson());
        textAreaInstruction.setText(routine.getInstruction());
    }

    @FXML
    private void modify(ActionEvent event) {
        String title = textFieldTitle.getText();
        String person = textFieldPerson.getText();
        String instruction = textAreaInstruction.getText();

        if (title == null || title.trim().isEmpty() || instruction == null || instruction.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Error de validacion", "Faltan campos", "Por favor, rellena todos los campos.");
        } else {
            if (routine.getTitle().equals(title) && routine.getPerson().equals(person) && routine.getInstruction().equals(instruction)) {
                showAlert(AlertType.ERROR, "Error de validacion", "Campos no cambiados", "Por favor, cambia algun campo.");
            } else {
                boolean result = cont.modifyRoutine(new Rutina(title, person, instruction));
                if (result) {
                    showAlert(AlertType.CONFIRMATION, "Rutina " + title + " modificada correctamente",
                            "", "La rutina con el titulo " + title + " ha sido modificada correctamente.");
                    Stage stage = (Stage) btnModify.getScene().getWindow();
                    stage.close();
                } else {
                    showAlert(AlertType.ERROR, "ERROR", "",
                            "Ha occurrido un error al modificar la rutina. Puede que ya exista en la base de datos una rutina con el mismo titulo para la misma persona.");
                }
            }
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
