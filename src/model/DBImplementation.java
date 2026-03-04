package model;

import java.util.ArrayList;
import java.util.List;
import static model.HibernateSession.getSessionFactory;
import org.hibernate.*;
import org.hibernate.query.Query;

/**
 *
 * @author Jago128
 */
public class DBImplementation implements AgendaDAO {

    /**
     *
     * @param username
     * @param password
     * @return
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
     *
     * @param username
     * @param surname
     * @param password
     * @param email
     * @param gender
     * @return
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
     *
     * @param username
     * @return
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
     *
     * @param username
     * @return
     */
    @Override
    public User getUser(String username) {
        Session session = getSessionFactory().openSession();

        try {
            String hql = "FROM User u WHERE u.username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);

            return query.uniqueResult();

        } catch (Exception e) {
            System.out.println("Error de base de datos al intentar obtener un usuario: " + e.getMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     *
     * @param username
     * @param surname
     * @param password
     * @param email
     * @param currentAccount
     * @return
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
     *
     * @param username
     * @param surname
     * @param password
     * @param email
     * @param gender
     * @return
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
     *
     * @param username
     * @param password
     * @return
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
     *
     * @return
     */
    @Override
    public List<Rutina> getRoutines() {
        Session session = getSessionFactory().openSession();
        List<Rutina> routines = new ArrayList<>();

        try {
            String hql = "FROM Rutina r ORDER BY r.title ASC";
            Query<Rutina> query = session.createQuery(hql, Rutina.class);

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
     *
     * @param title
     * @param person
     * @return
     */
    @Override
    public boolean routineExists(String title, String person) {
        Session session = getSessionFactory().openSession();

        try {
            String hql = "SELECT COUNT(p) FROM Rutina r WHERE r.title = :title AND r.person = :person";
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
     *
     * @param title
     * @param person
     * @param instruction
     * @param user
     * @return
     */
    @Override
    public boolean addRoutine(String title, String person, String instruction, User user) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String checkHql = "SELECT COUNT(r) FROM Rutina r WHERE r.title = :title AND r.person = :person";
            Query<Long> checkQuery = session.createQuery(checkHql, Long.class);
            checkQuery.setParameter("title", title);
            checkQuery.setParameter("person", person);
            Long count = checkQuery.uniqueResult();

            if (count > 0) {
                System.out.println("Hay una rutina con el mismo titulo y persona asociada.");
                return false;
            }

            Rutina routine = new Rutina(title, person, instruction, user);

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
     *
     * @param routine
     * @return
     */
    @Override
    public boolean modifyRoutine(Rutina routine) {
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
     *
     * @param routine
     * @return
     */
    @Override
    public boolean deleteRoutine(Rutina routine) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Rutina routineToDelete = session.get(Rutina.class, routine.getRoutineCode());

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
