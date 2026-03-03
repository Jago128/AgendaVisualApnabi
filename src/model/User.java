package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "USER_")
@PrimaryKeyJoinColumn(name = "user_code", referencedColumnName = "user_code")
public class User extends Profile {

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rutina> routines = new ArrayList<>();

    public User() {
        super();
        this.gender = Gender.NO_ESPECIFICAR;
    }

    public User(String username, String surname, String password, String email, Gender gender) {
        super(username, surname, password, email);
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Rutina> getRoutines() {
        return routines;
    }

    public void setRoutines(List<Rutina> routines) {
        this.routines = routines;
    }
    
    public void addRoutine(Rutina routine) {
        routines.add(routine);
        routine.setUser(this);
    }

    @Override
    public String toString() {
        return super.toString() + "User{" + "gender=" + gender + ", routines=" + routines + '}';
    }
}
