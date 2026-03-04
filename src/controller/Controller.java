package controller;

import java.util.List;
import model.*;

/**
 *
 * @author Jago128
 */
public class Controller {
    
    AgendaDAO dao = new DBImplementation();

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public Profile login(String username, String password) {
        return dao.login(username, password);
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
    public boolean signUp(String username, String surname, String password, String email, Gender gender) {
        return dao.signUp(username, surname, password, email, gender);
    }

    /**
     *
     * @param username
     * @return
     */
    public boolean userExists(String username) {
        return dao.userExists(username);
    }

    /**
     *
     * @param username
     * @return
     */
    public User getUser(String username) {
        return dao.getUser(username);
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
    public boolean createAdmin(String username, String surname, String password, String email, String currentAccount) {
        return dao.createAdmin(username, surname, password, email, currentAccount);
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
    public boolean modifyAcc(String username, String surname, String password, String email, Gender gender) {
        return dao.modifyAcc(username, surname, password, email, gender);
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean deleteAcc(String username, String password) {
        return dao.deleteAcc(username, password);
    }

    /**
     *
     * @return
     */
    public List<Rutina> getRoutines() {
        return dao.getRoutines();
    }
    
    /**
     *
     * @param title
     * @param person
     * @return
     */
    public boolean routineExists(String title, String person) {
        return dao.routineExists(title, person);
    }

    /**
     *
     * @param title
     * @param person
     * @param instruction
     * @param user
     * @return
     */
    public boolean addRoutine(String title, String person, String instruction, User user) {
        return dao.addRoutine(title, person, instruction, user);
    }

    /**
     *
     * @param routine
     * @return
     */
    public boolean modifyRoutine(Rutina routine) {
        return dao.modifyRoutine(routine);
    }

    /**
     *
     * @param routine
     * @return
     */
    public boolean deleteRoutine(Rutina routine) {
        return dao.deleteRoutine(routine);
    }
}
