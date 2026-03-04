package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Profile abstract object class. User and Admin extend from this class.
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
     * Empty Profile constructor.
     */
    public Profile() {
        this.userCode = 0;
        this.username = "";
        this.surname = "";
        this.password = "";
        this.email = "";
    }

    /**
     * Parametrized Profile constructor.
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
     * Getter for profile ID.
     *
     * @return The profile ID.
     */
    public int getUserCode() {
        return userCode;
    }

    /**
     * Setter for profile ID.
     *
     * @param userCode The profile ID.
     */
    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    /**
     * Getter for profile username.
     *
     * @return The profile username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for profile username.
     *
     * @param username The profile new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for profile surname.
     *
     * @return The profile surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter for profile surname.
     *
     * @param surname The profile new surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter for profile password.
     *
     * @return Setter for profile password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for profile password.
     *
     * @param password The profile new pasword.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for profile email.
     *
     * @return The profile email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for profile email.
     *
     * @param email The profile new email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * ToString method.
     *
     * @return Profile info.
     */
    @Override
    public String toString() {
        return "Profile{" + "userCode=" + userCode + ", username=" + username + ", surname=" + surname + ", password=" + password + ", email=" + email + '}';
    }
}
