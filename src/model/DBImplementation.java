package model;

import java.util.ArrayList;
import java.util.List;
import static model.HibernateSession.getSessionFactory;
import org.hibernate.*;
import org.hibernate.query.Query;

/**
 * Implementation of the AgendaDAO interface, using the Hibernate ORM for all database operations.
 * @author Jago128
 */
public class DBImplementation implements AgendaDAO {

    /**
     * Logs an user into the application if the username and password are correct.
     * @param username The username of the profile.
     * @param password The password of the profile.
     * @return The profile if successful, null otherwise.
     */
    @Override
    public Profile login(String username, String password) {
        Session session = getSessionFactory().openSession();

        try {
            String hqlUser = "FROM User u WHERE u.username = :username AND u.password = :password";
            Query<User> queryUser = session.createQuery(hqlUser, User.class);
            queryUser.setParameter("username", username);
            queryUser.setParameter("password", password);

            User user = queryUser.uniqueResult();
            if (user != null) {
                return user;
            }

            String hqlAdmin = "FROM Admin a WHERE a.username = :username AND a.password = :password";
            Query<Admin> queryAdmin = session.createQuery(hqlAdmin, Admin.class);
            queryAdmin.setParameter("username", username);
            queryAdmin.setParameter("password", password);

            Admin admin = queryAdmin.uniqueResult();
            if (admin != null) {
                return admin;
            }

            System.out.println("El usuario especificado no existe.");

        } catch (Exception e) {
            System.out.println("Error de base de datos al intentar el inicio de sesion: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    /**
     * Signs up an user to the application.
     * @param username The new user's username.
     * @param surname The new user's surname.
     * @param password The new user's password.
     * @param email The new user's email.
     * @param gender The new user's gender.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean signUp(String username, String surname, String password, String email, Gender gender) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String checkHql = "SELECT COUNT(u) FROM User u WHERE u.username = :username AND u.surname = :surname";
            Query<Long> checkQuery = session.createQuery(checkHql, Long.class);
            checkQuery.setParameter("username", username);
            checkQuery.setParameter("surname", surname);
            Long count = checkQuery.uniqueResult();

            if (count > 0) {
                System.out.println("Ya existe un usuario con el mismo nombre y apellidos.");
                return false;
            }

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setSurname(surname);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setGender(gender);

            session.save(newUser);
            transaction.commit();

            System.out.println("El usuario " + username + " se ha registrado correctamente.");
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error de base de datos al intentar registrar un usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Searches for an user in the database, mainly for username duplicate checking.
     * @param username The username of the user to search for.
     * @return True if exists, false if it doesn't exist.
     */
    @Override
    public boolean userExists(String username) {
        Session session = getSessionFactory().openSession();

        try {
            String hql = "SELECT COUNT(p) FROM Profile p WHERE p.username = :username";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);

            Long count = query.uniqueResult();
            return count > 0;

        } catch (Exception e) {
            System.out.println("Error de base de datos al intentar comprobar la existencia un usuario: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Creates an admin profile.
     * @param username The new user's username.
     * @param surname The new user's surname.
     * @param password The new user's password.
     * @param email The new user's email.
     * @param currentAccount The current account of the admin.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean createAdmin(String username, String surname, String password, String email, String currentAccount) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String checkHql = "SELECT COUNT(p) FROM Profile p WHERE p.username = :username";
            Query<Long> checkQuery = session.createQuery(checkHql, Long.class);
            checkQuery.setParameter("username", username);
            Long count = checkQuery.uniqueResult();

            if (count > 0) {
                System.out.println("Ya existe un admin con el mismo nombre.");
                return false;
            }

            Admin newAdmin = new Admin();
            newAdmin.setUsername(username);
            newAdmin.setPassword(password);
            newAdmin.setEmail(email);
            newAdmin.setSurname(surname);
            newAdmin.setCurrentAcc(currentAccount);

            session.save(newAdmin);
            transaction.commit();

            System.out.println("El admin " + username + " ha sido registrado correctamente.");
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error de base de datos al intentar crear un admin: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Modifies an user based on the given parameters.
     * @param username The user's new username.
     * @param surname The user's new surname.
     * @param password The user's new password.
     * @param email The user's new email.
     * @param gender The user's new gender.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean modifyAcc(String username, String surname, String password, String email, Gender gender) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hql = "FROM User u WHERE u.username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);

            User user = query.uniqueResult();

            if (user == null) {
                System.out.println("El usuario proveeido no se ha encontrado.");
                return false;
            }

            user.setUsername(username);
            user.setSurname(surname);
            user.setEmail(email);
            user.setGender(gender);
            user.setPassword(password);

            session.update(user);
            transaction.commit();

            System.out.println("El usuario " + username + " ha sido modificado correctamente.");
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error de base de datos al intentar modificar un usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Deletes an user from the database.
     * @param username The username of the user to be deleted.
     * @param password The password of the user to be deleted.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean deleteAcc(String username, String password) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hql = "FROM User u WHERE u.username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);

            User user = query.uniqueResult();

            if (user == null) {
                System.out.println("El usuario proveeido no se ha encontrado.");
                return false;
            }

            if (!user.getPassword().equals(password)) {
                System.out.println("La contraseña introducida es incorrecta.");
                return false;
            }

            session.delete(user);
            transaction.commit();

            System.out.println("El usuario " + username + " ha sido eliminado correctamente.");
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error de base de datos al intentar borrar un usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
    /**
     * Gets all routines in the database.
     * @return The list of routines.
     */
    @Override
    public List<Routine> getRoutines() {
        Session session = getSessionFactory().openSession();
        List<Routine> routines = new ArrayList<>();

        try {
            String hql = "FROM Routine r ORDER BY r.title ASC";
            Query<Routine> query = session.createQuery(hql, Routine.class);

            routines = query.getResultList();
            System.out.println("Total de rutinas encontradas: " + routines.size());

        } catch (Exception e) {
            System.out.println("Error de base de datos al intentar obtener las rutinas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return routines;
    }
    
    /**
     * Checks if a routine associated to a person already exists.
     * @param title The title of the routine.
     * @param person The person associated to the routine.
     * @return True if exists, false if it doesn't exist.
     */
    @Override
    public boolean routineExists(String title, String person) {
        Session session = getSessionFactory().openSession();

        try {
            String hql = "SELECT COUNT(p) FROM Routine r WHERE r.title = :title AND r.person = :person";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("title", title);
            query.setParameter("person", person);

            Long count = query.uniqueResult();
            return count > 0;

        } catch (Exception e) {
            System.out.println("Error de base de datos al intentar comprobar la existencia una rutina: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Adds a new routine to the database.
     * @param title The title of the routine.
     * @param person The person associated to the routine.
     * @param instruction The instructions of the routine.
     * @param user The user who created the routine.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean addRoutine(String title, String person, String instruction, User user) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String checkHql = "SELECT COUNT(r) FROM Routine r WHERE r.title = :title AND r.person = :person";
            Query<Long> checkQuery = session.createQuery(checkHql, Long.class);
            checkQuery.setParameter("title", title);
            checkQuery.setParameter("person", person);
            Long count = checkQuery.uniqueResult();

            if (count > 0) {
                System.out.println("Hay una rutina con el mismo titulo y persona asociada.");
                return false;
            }

            Routine routine = new Routine(title, person, instruction, user);

            session.save(routine);
            transaction.commit();

            System.out.println("La rutina con el titulo " + routine.getTitle() + " ha sido añadida correctamente.");
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error de base de datos al intentar añadir una rutina: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Modifies a routine based off of the data in the given parameter.
     * @param routine The routine to be modified.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean modifyRoutine(Routine routine) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(routine);
            transaction.commit();

            System.out.println("La rutina con el titulo " + routine.getTitle() + " ha sido modificada correctamente.");
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error de base de datos al intentar modificar una rutina: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Deletes a routine from the database.
     * @param routine The routine to be deleted.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean deleteRoutine(Routine routine) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Routine routineToDelete = session.get(Routine.class, routine.getRoutineCode());

            if (routineToDelete != null) {
                session.delete(routineToDelete);
                transaction.commit();
                System.out.println("La rutina " + routineToDelete.getTitle() + " ha sido eliminada correctamente.");
                return true;
            } else {
                System.out.println("La rutina proveeida no existe.");
                return false;
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error de base de datos al intentar borrar rutina: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
