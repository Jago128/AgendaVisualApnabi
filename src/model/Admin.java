package model;

import javax.persistence.*;

/**
 * Admin object class, extending from Profile.
 *
 * @author Jago128
 */
@Entity
@Table(name = "ADMIN_")
@PrimaryKeyJoinColumn(name = "user_code", referencedColumnName = "user_code")
public class Admin extends Profile {

    @Column(name = "currentAcc", nullable = false, length = 40)
    private String currentAcc;

    /**
     * Empty Admin constructor.
     */
    public Admin() {
        super();
        this.currentAcc = "";
    }

    /**
     * Parametrized Admin constructor.
     *
     * @param username Admin username.
     * @param surname Admin surname.
     * @param password Admin password.
     * @param email Admin email.
     * @param currentAcc Admin current account.
     */
    public Admin(String username, String surname, String password, String email, String currentAcc) {
        super(username, surname, password, email);
        this.currentAcc = currentAcc;
    }

    /**
     * Getter for current account.
     *
     * @return The Admin current account.
     */
    public String getCurrentAcc() {
        return currentAcc;
    }

    /**
     * Setter for current account.
     *
     * @param currentAcc The new Admin current account.
     */
    public void setCurrentAcc(String currentAcc) {
        this.currentAcc = currentAcc;
    }

    /**
     * ToString method.
     *
     * @return Admin info.
     */
    @Override
    public String toString() {
        return super.toString() + "Admin{" + "currentAcc=" + currentAcc + '}';
    }
}
