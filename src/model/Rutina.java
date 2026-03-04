package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Jago128
 */
@Entity
@Table(name = "ROUTINE_")
public class Rutina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_code")
    private int routineCode;

    @Column(name = "title", nullable = false, length = 50)
    private String title;
    
    @Column(name = "person", nullable = false, length = 100)
    private String person;

    @Column(name = "instruction", nullable = false, length = 500)
    private String instruction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "user_code")
    private User user;

    /**
     *
     */
    public Rutina() {
        this.title = "";
        this.person = "";
        this.instruction = "";
        this.user = null;
    }

    /**
     *
     * @param title
     * @param person
     * @param instruction
     * @param user
     */
    public Rutina(String title, String person, String instruction, User user) {
        this.title = title;
        this.person = person;
        this.instruction = instruction;
        this.user = user;
    }

    /**
     *
     * @return
     */
    public int getRoutineCode() {
        return routineCode;
    }

    /**
     *
     * @param routineCode
     */
    public void setRoutineCode(int routineCode) {
        this.routineCode = routineCode;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getPerson() {
        return person;
    }

    /**
     *
     * @param person
     */
    public void setPerson(String person) {
        this.person = person;
    }

    /**
     *
     * @return
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     *
     * @param instruction
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Rutina{" + "routineCode=" + routineCode + ", title=" + title + ", instruction=" + instruction + ", user=" + user + '}';
    }
}
