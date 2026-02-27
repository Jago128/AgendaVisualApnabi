package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "USER_")
@PrimaryKeyJoinColumn(name = "user_code", referencedColumnName = "user_code")
public class User extends Profile {

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    public User() {
        super();
        this.gender = Gender.NO_ESPECIFICAR;
    }

    public User(Gender gender, String username, String surname, String password, String email) {
        super(username, surname, password, email);
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return super.toString() + "User{" + "gender=" + gender + '}';
    }
}
