package model;

import javax.persistence.*;

@Entity
@Table(name = "ADMIN_")
@PrimaryKeyJoinColumn(name = "user_code", referencedColumnName = "user_code")
public class Admin extends Profile {

    @Column(name = "currentAcc", nullable = false, length = 40)
    private String currentAcc;

    public Admin() {
        super();
        this.currentAcc = "";
    }

    public Admin(String username, String surname, String password, String email, String currentAcc) {
        super(username, surname, password, email);
        this.currentAcc = currentAcc;
    }

    public String getCurrentAcc() {
        return currentAcc;
    }

    public void setCurrentAcc(String currentAcc) {
        this.currentAcc = currentAcc;
    }

    @Override
    public String toString() {
        return super.toString() + "Admin{" + "currentAcc=" + currentAcc + '}';
    }
}
