package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

    private Controller cont;
    private Profile user;

    public void setController(Controller cont) {
        this.cont = cont;
    }

    public void setProfile(Profile profile) {
        this.user = profile;
        userMenu.setText(user.getUsername());
    }

    @FXML
    private void debug(ActionEvent event) {
        //API check
    }

    @FXML
    private void modifyUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserConfigWindow.fxml"));
            Parent root = loader.load();
            AddWindowController addCont = loader.getController();
            addCont.setController(cont);
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
    private void deleteAcc(ActionEvent event) {
        //Window here
    }

    @FXML
    private void logOut(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginWindow.fxml"));
            Parent root = loader.load();
            AddWindowController addCont = loader.getController();
            addCont.setController(cont);
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
        //DB method
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
