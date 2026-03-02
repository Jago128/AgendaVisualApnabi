package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

public class MainWindowController implements Initializable {

    @FXML
    private Button tempBtn;

    @FXML
    private TableView<?> tableAgendas;

    @FXML
    private TableColumn<Rutina, String> colName;

    @FXML
    private TableColumn<Rutina, ?> colPH;

    @FXML
    private Button addBtn;

    @FXML
    private Button modifyBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private MenuButton userMenu;

    @FXML
    private MenuItem modifyUser;

    @FXML
    private MenuItem logOut;

    @FXML
    private MenuItem deleteAcc;

    private Controller cont;
    private Profile user;

    public void setController(Controller cont) {
        this.cont = cont;
        userMenu.setText(user.getUsername());
    }

    public void setProfile(Profile profile) {
        this.user = profile;

    }

    @FXML
    private void debug(ActionEvent event) {
        //API
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
            stage.setTitle("Ventana Añadir");
            stage.setScene(scene);
            stage.show();
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
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        //DB method
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
