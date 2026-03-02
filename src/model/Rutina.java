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

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "instruction", nullable = false, length = 500)
    private String instruction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "user_code")
    private User user;

    public Rutina() {
        this.name = "";
        this.instruction = "";
    }

    public Rutina(String name, String instruction) {
        this.name = name;
        this.instruction = instruction;
    }

    public int getRoutineCode() {
        return routineCode;
    }

    public void setRoutineCode(int routineCode) {
        this.routineCode = routineCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Rutina{" + "routineCode=" + routineCode + ", name=" + name + ", instruction=" + instruction + ", user=" + user + '}';
    }
}
