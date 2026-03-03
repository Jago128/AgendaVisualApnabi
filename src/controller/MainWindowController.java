package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;
import model.*;

public class MainWindowController implements Initializable {

    @FXML
    private Button tempBtn;

    @FXML
    private TableView<Rutina> tableAgenda;

    @FXML
    private TableColumn<Rutina, String> colTitle;

    @FXML
    private TableColumn<Rutina, String> colPersona;

    @FXML
    private TableColumn<Rutina, String> colInstruction;

    @FXML
    private MenuButton userMenu;

    @FXML
    private MenuItem modifyUser;

    @FXML
    private MenuItem logOut;

    @FXML
    private MenuItem deleteAcc;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExit;

    private Controller cont;
    private Profile user;
    private ObservableList<Rutina> routines;
    private Rutina selected;

    public void setController(Controller cont) {
        this.cont = cont;
    }

    public void setUser(Profile profile) {
        this.user = profile;
        userMenu.setText(user.getUsername());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        routines = FXCollections.observableArrayList();
        routines.setAll(cont.getRoutines());

        tableAgenda.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> getSelectedTableItem()
        );
    }

    public void loadAllGames() {
        routines.setAll(cont.getRoutines());
        tableAgenda.setItems(routines);
    }

    private void getSelectedTableItem() {
        selected = selected = tableAgenda.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void debug(ActionEvent event) {
        //API check
    }

    @FXML
    private void modifyUser(ActionEvent event) {
        try {
            //Change to non-modal and add exit btn to other window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserConfigWindow.fxml"));
            Parent root = loader.load();
            UserConfigWindowController userConfigCont = loader.getController();
            userConfigCont.setController(cont);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Configuracion Usuario");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteAcc(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DeleteUserWindowController.fxml"));
            Parent root = loader.load();
            DeleteUserWindowController delCont = loader.getController();
            delCont.setController(cont);
            Stage current = (Stage) btnDelete.getScene().getWindow();
            delCont.setMainStage(current);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Borrar Cuenta");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logOut(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Login");
            stage.setScene(scene);
            stage.show();
            Stage current = (Stage) tableAgenda.getScene().getWindow();
            current.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddWindow.fxml"));
            Parent root = loader.load();
            AddWindowController addCont = loader.getController();
            addCont.setController(cont);
            addCont.setMainWindowController(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Añadir");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modify(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyWindow.fxml"));
            Parent root = loader.load();
            ModifyWindowController modifyCont = loader.getController();
            modifyCont.setController(cont);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana Modificar");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        if (selected == null) {
            showAlert(AlertType.WARNING, "ERROR!", "¡Sin seleccion!", "Selecciona una rutina antes de usar esta funcion.");
        } else {
            Alert warning = showAlert(AlertType.CONFIRMATION, "AVISO",
                    "¿Estas seguro de que quieres borrar la rutina con el titulo " + selected.getTitle() + "?", "Elija una opcion.");
            if (warning.getResult().equals(ButtonType.OK)) {
                if (cont.deleteRoutine(selected)) {
                    showAlert(AlertType.INFORMATION, "Rutina eliminada", "", "La rutina con titulo " + selected.getTitle() + " eliminada correctamente.");
                } else {
                    showAlert(AlertType.ERROR, "ERROR", "Rutina no encontrada", "No se ha encontrado la rutina.");
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
}
