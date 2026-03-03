package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "PROFILE_")
public abstract class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private int userCode;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "password", nullable = false, length = 50)
    private String password;
    
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    public Profile() {
        this.userCode = 0;
        this.username = "";
        this.surname = "";
        this.password = "";
        this.email = "";
    }

    public Profile(String username, String surname, String password, String email) {
        this.username = username;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Profile{" + "userCode=" + userCode + ", username=" + username + ", surname=" + surname + ", password=" + password + ", email=" + email + '}';
    }
}
