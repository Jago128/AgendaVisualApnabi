package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import model.*;
import org.hibernate.*;

/**
 * The application Main class.
 *
 * @author Jago128
 */
public class Main extends Application {

    /**
     * Static method to create all tables in the database via the Hibernate ORM and to also set up an Admin account.
     */
    private static void CreateTableHibernate() {
        try {
            SessionFactory sessionFactory = HibernateSession.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.close();

            DBImplementation db = new DBImplementation();
            boolean adminCreated = db.createAdmin(
                    "admin", // username
                    "System", // surname
                    "admin", // password
                    "admin@store.com", // email
                    "CTA-ADMIN" // currentAccount
            );

            if (adminCreated) {
                System.out.println("Admin por defecto creado: admin");
            } else {
                System.out.println("Admin 'admin' ya existe");
            }
        } catch (HibernateException e) {
            System.err.println("Error al crear tablas o admin: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Starts the JavaFX application by opening the Login window.
     *
     * @param stage The starting stage for this application.
     * @throws Exception If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Ventana Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to launch the application.
     *
     * @param args command-line arguments (Unused).
     */
    public static void main(String[] args) {
        CreateTableHibernate();
        launch(args);
    }
}
