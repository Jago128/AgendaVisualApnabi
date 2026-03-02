package controller;

import java.util.List;
import model.*;

public class Controller {
    
    AgendaDAO dao = new DBImplementation();

    public Profile login(String username, String password) {
        return dao.login(username, password);
    }

    public boolean signUp(String username, String surname, String password, String email, Gender gender) {
        return dao.signUp(username, surname, password, email, gender);
    }

    public boolean userExists(String username) {
        return dao.userExists(username);
    }

    public User getUser(String username) {
        return dao.getUser(username);
    }
    
    public boolean createAdmin(String username, String surname, String password, String email, String currentAccount) {
        return dao.createAdmin(username, surname, password, email, currentAccount);
    }

    public boolean modifyAcc(String username, String surname, String password, String email, Gender gender) {
        return dao.modifyAcc(username, surname, password, email, gender);
    }

    public boolean deleteAcc(String username, String password) {
        return dao.deleteAcc(username, password);
    }

    public List<Rutina> getRoutines() {
        return dao.getRoutines();
    }

    public boolean addRoutine(String title, String person, String instruction) {
        return dao.addRoutine(title, person, instruction);
    }

    public boolean modifyRoutine(Rutina routine) {
        return dao.modifyRoutine(routine);
    }

    public boolean deleteRoutine(Rutina routine) {
        return dao.deleteRoutine(routine);
    }
}
