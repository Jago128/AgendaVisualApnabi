package model;

import javax.persistence.*;

/**
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
     *
     */
    public Admin() {
        super();
        this.currentAcc = "";
    }

    /**
     *
     * @param username
     * @param surname
     * @param password
     * @param email
     * @param currentAcc
     */
    public Admin(String username, String surname, String password, String email, String currentAcc) {
        super(username, surname, password, email);
        this.currentAcc = currentAcc;
    }

    /**
     *
     * @return
     */
    public String getCurrentAcc() {
        return currentAcc;
    }

    /**
     *
     * @param currentAcc
     */
    public void setCurrentAcc(String currentAcc) {
        this.currentAcc = currentAcc;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + "Admin{" + "currentAcc=" + currentAcc + '}';
    }
}
