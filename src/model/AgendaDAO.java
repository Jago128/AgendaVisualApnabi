package model;

import java.util.List;

public interface AgendaDAO {

    public Profile login(String username, String password);
    
    public boolean signUp(String username, String surname, String password, String email, Gender gender);
    
    public boolean userExists(String username);
    
    public User getUser(String username);
    
    public boolean createAdmin(String username, String surname, String password, String email, String currentAccount);
    
    public boolean modifyAcc(String username, String surname, String password, String email, Gender gender);
    
    public boolean deleteAcc(String username, String password);
    
    public List<Rutina> getRoutines();
    
    public boolean addRoutine(String name, String instruction);
    
    public boolean modifyRoutine(Rutina routine);
    
    public boolean deleteRoutine(Rutina routine);
}
