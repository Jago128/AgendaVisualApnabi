package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "ROUTINE_")
public class Rutina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_code")
    private int routineCode;

    @Column(name = "title", nullable = false, unique = true, length = 50)
    private String title;
    
    @Column(name = "person", nullable = false, unique = true, length = 100)
    private String person;

    @Column(name = "instruction", nullable = false, length = 500)
    private String instruction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "user_code")
    private User user;

    public Rutina() {
        this.title = "";
        this.person = "";
        this.instruction = "";
    }

    public Rutina(String title, String person, String instruction) {
        this.title = title;
        this.person = person;
        this.instruction = instruction;
    }

    public int getRoutineCode() {
        return routineCode;
    }

    public void setRoutineCode(int routineCode) {
        this.routineCode = routineCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Rutina{" + "routineCode=" + routineCode + ", title=" + title + ", instruction=" + instruction + ", user=" + user + '}';
    }
}
