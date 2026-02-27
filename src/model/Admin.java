package model;

import javax.persistence.*;

@Entity
@Table(name = "ADMIN_")
@PrimaryKeyJoinColumn(name = "user_code", referencedColumnName = "user_code")
public class Admin extends Profile {

    @Column(name = "name", nullable = false, length = 40)
    private String currentAgenda;

    public Admin() {
        super();
        this.currentAgenda = "";
    }

    public Admin(String currentAgenda, String username, String surname, String password, String email) {
        super(username, surname, password, email);
        this.currentAgenda = currentAgenda;
    }

    public String getCurrentAgenda() {
        return currentAgenda;
    }

    public void setCurrentAgenda(String currentAgenda) {
        this.currentAgenda = currentAgenda;
    }

    @Override
    public String toString() {
        return super.toString() + "Admin{" + "currentAgenda=" + currentAgenda + '}';
    }
}
