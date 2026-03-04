package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * User object class, extending from Profile.
 *
 * @author Jago128
 */
@Entity
@Table(name = "USER_")
@PrimaryKeyJoinColumn(name = "user_code", referencedColumnName = "user_code")
public class User extends Profile {

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Routine> routines = new ArrayList<>();

    /**
     * Empty User constructor.
     */
    public User() {
        super();
        this.gender = Gender.NO_ESPECIFICAR;
    }

    /**
     * Parametrized User constructor.
     *
     * @param username User name.
     * @param surname User surname.
     * @param password User password.
     * @param email User email.
     * @param gender User gender.
     */
    public User(String username, String surname, String password, String email, Gender gender) {
        super(username, surname, password, email);
        this.gender = gender;
    }

    /**
     * Getter for user gender.
     * @return The user gender.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Setter for user gender.
     * @param gender The new user gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Getter for Routine list.
     * @return The routine list.
     */
    public List<Routine> getRoutines() {
        return routines;
    }

    /**
     * Setter for Routine list.
     * @param routines The new routine list.
     */
    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }

    /**
     * Hibernate method to add new routine to list.
     * @param routine The new routine to add.
     */
    public void addRoutine(Routine routine) {
        routines.add(routine);
        routine.setUser(this);
    }

    /**
     * ToString method.
     * @return User info.
     */
    @Override
    public String toString() {
        return super.toString() + "User{" + "gender=" + gender + ", routines=" + routines + '}';
    }
}
