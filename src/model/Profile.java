package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Jago128
 */
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

    /**
     *
     */
    public Profile() {
        this.userCode = 0;
        this.username = "";
        this.surname = "";
        this.password = "";
        this.email = "";
    }

    /**
     *
     * @param username
     * @param surname
     * @param password
     * @param email
     */
    public Profile(String username, String surname, String password, String email) {
        this.username = username;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    /**
     *
     * @return
     */
    public int getUserCode() {
        return userCode;
    }

    /**
     *
     * @param userCode
     */
    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Profile{" + "userCode=" + userCode + ", username=" + username + ", surname=" + surname + ", password=" + password + ", email=" + email + '}';
    }
}
