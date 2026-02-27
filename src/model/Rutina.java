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
    
    @Column(name = "desc", nullable = false, length = 500)
    private String desc;

    public Rutina() {
        this.name = "";
        this.desc = "";
    }

    public Rutina(String name, String desc) {
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Rutina{" + "routineCode=" + routineCode + ", name=" + name + ", desc=" + desc + '}';
    }
}
