package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
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
    private List<Rutina> routines = new ArrayList<>();

    /**
     *
     */
    public User() {
        super();
        this.gender = Gender.NO_ESPECIFICAR;
    }

    /**
     *
     * @param username
     * @param surname
     * @param password
     * @param email
     * @param gender
     */
    public User(String username, String surname, String password, String email, Gender gender) {
        super(username, surname, password, email);
        this.gender = gender;
    }

    /**
     *
     * @return
     */
    public Gender getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     */
    public List<Rutina> getRoutines() {
        return routines;
    }

    /**
     *
     * @param routines
     */
    public void setRoutines(List<Rutina> routines) {
        this.routines = routines;
    }
    
    /**
     *
     * @param routine
     */
    public void addRoutine(Rutina routine) {
        routines.add(routine);
        routine.setUser(this);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + "User{" + "gender=" + gender + ", routines=" + routines + '}';
    }
}
