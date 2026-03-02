package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import model.HibernateSession;
import org.hibernate.*;

public class Main extends Application {

private static void CreateTableHibernate() {
        try {
            SessionFactory sessionFactory = HibernateSession.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.close();
            
            /*DBImplementation db = new DBImplementation();
            boolean adminCreated = db.createAdmin(
                "admin",           // username
                "admin",           // password
                "admin@store.com", // email
                "Admin",           // name
                "000000000",       // telephone
                "System",          // surname
                "CTA-ADMIN"        // currentAccount
            );
            
            if (adminCreated) {
                System.out.println("Admin por defecto creado/validado: admin");
            } else {
                System.out.println("Admin 'admin' ya existe o error al crearlo");
            }*/
            
        } catch (Exception e) {
            System.err.println("Error al crear tablas: " + e.getMessage());
            System.exit(1);  // Sale si no puede crear tablas
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Ventana Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        CreateTableHibernate();
        launch(args);
    }
}
