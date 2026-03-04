package model;

import java.util.List;

/**
 * The interface used for the Data Access Object model.
 * Provides methods to interact with records in the database.
 * @author Jago128
 */
public interface AgendaDAO {

    /**
     * Logs an user into the application if the username and password are correct.
     * @param username The username of the profile.
     * @param password The password of the profile.
     * @return The profile if successful, null otherwise.
     */
    public Profile login(String username, String password);
    
    /**
     * Signs up an user to the application.
     * @param username The new user's username.
     * @param surname The new user's surname.
     * @param password The new user's password.
     * @param email The new user's email.
     * @param gender The new user's gender.
     * @return True if successful, false otherwise.
     */
    public boolean signUp(String username, String surname, String password, String email, Gender gender);
    
    /**
     * Searches for an user in the database, mainly for username duplicate checking.
     * @param username The username of the user to search for.
     * @return True if exists, false if it doesn't exist.
     */
    public boolean userExists(String username);
    
    /**
     * Creates an admin profile.
     * @param username The new user's username.
     * @param surname The new user's surname.
     * @param password The new user's password.
     * @param email The new user's email.
     * @param currentAccount The current account of the admin.
     * @return True if successful, false otherwise.
     */
    public boolean createAdmin(String username, String surname, String password, String email, String currentAccount);
    
    /**
     * Modifies an user based on the given parameters.
     * @param username The user's new username.
     * @param surname The user's new surname.
     * @param password The user's new password.
     * @param email The user's new email.
     * @param gender The user's new gender.
     * @return True if successful, false otherwise.
     */
    public boolean modifyAcc(String username, String surname, String password, String email, Gender gender);
    
    /**
     * Deletes an user from the database.
     * @param username The username of the user to be deleted.
     * @param password The password of the user to be deleted.
     * @return True if successful, false otherwise.
     */
    public boolean deleteAcc(String username, String password);
    
    /**
     * Gets all routines in the database.
     * @return The list of routines.
     */
    public List<Routine> getRoutines();
    
    /**
     * Checks if a routine associated to a person already exists.
     * @param title The title of the routine.
     * @param person The person associated to the routine.
     * @return True if exists, false if it doesn't exist.
     */
    public boolean routineExists(String title, String person);
    
    /**
     * Adds a new routine to the database.
     * @param title The title of the routine.
     * @param person The person associated to the routine.
     * @param instruction The instructions of the routine.
     * @param user The user who created the routine.
     * @return True if successful, false otherwise.
     */
    public boolean addRoutine(String title, String person, String instruction, User user);
    
    /**
     * Modifies a routine based off of the data in the given parameter.
     * @param routine The routine to be modified.
     * @return True if successful, false otherwise.
     */
    public boolean modifyRoutine(Routine routine);
    
    /**
     * Deletes a routine from the database.
     * @param routine The routine to be deleted.
     * @return True if successful, false otherwise.
     */
    public boolean deleteRoutine(Routine routine);
}
