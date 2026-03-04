package model;

import java.util.List;

/**
 *
 * @author Jago128
 */
public interface AgendaDAO {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public Profile login(String username, String password);
    
    /**
     *
     * @param username
     * @param surname
     * @param password
     * @param email
     * @param gender
     * @return
     */
    public boolean signUp(String username, String surname, String password, String email, Gender gender);
    
    /**
     *
     * @param username
     * @return
     */
    public boolean userExists(String username);
    
    /**
     *
     * @param username
     * @return
     */
    public User getUser(String username);
    
    /**
     *
     * @param username
     * @param surname
     * @param password
     * @param email
     * @param currentAccount
     * @return
     */
    public boolean createAdmin(String username, String surname, String password, String email, String currentAccount);
    
    /**
     *
     * @param username
     * @param surname
     * @param password
     * @param email
     * @param gender
     * @return
     */
    public boolean modifyAcc(String username, String surname, String password, String email, Gender gender);
    
    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean deleteAcc(String username, String password);
    
    /**
     *
     * @return
     */
    public List<Rutina> getRoutines();
    
    /**
     *
     * @param title
     * @param person
     * @return
     */
    public boolean routineExists(String title, String person);
    
    /**
     *
     * @param title
     * @param person
     * @param instruction
     * @param user
     * @return
     */
    public boolean addRoutine(String title, String person, String instruction, User user);
    
    /**
     *
     * @param routine
     * @return
     */
    public boolean modifyRoutine(Rutina routine);
    
    /**
     *
     * @param routine
     * @return
     */
    public boolean deleteRoutine(Rutina routine);
}
