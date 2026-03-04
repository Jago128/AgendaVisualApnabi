package model;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

/**
 * Class for handling Hibernate Sessions.
 * @author Jago128
 */
public class HibernateSession {

    private static SessionFactory sessionFactory;
    
    /**
     * Nameless static constructor for the Hibernate session.
     */
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) {
            System.err.println("Error al intentar crear SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Static getter for the session factory.
     * @return The session factory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Static method to close Hibernate session.
     */
    public static void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            getSessionFactory().close();
        }
    }
}
