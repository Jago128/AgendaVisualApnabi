package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Rutina;

public class MainWindowController implements Initializable {

    @FXML
    private Button logOutBtn;

    @FXML
    private Button tempBtn;

    @FXML
    private TableView<Rutina> tableAgendas;

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
    
    private Controller cont;

    public void setController(Controller cont) {
        this.cont = cont;
    }

    @FXML
    private void debug(ActionEvent event) {

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

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
