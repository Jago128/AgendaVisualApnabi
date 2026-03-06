package model;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * Routine object class.
 *
 * @author Jago128
 */
@Entity
@Table(name = "ROUTINE_")
@PrimaryKeyJoinColumn(name = "image_path", referencedColumnName = "routine_code")
public class Routine implements Serializable {

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

    @Transient
    private List<File> images;

    @OneToMany(mappedBy = "imagePaths", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagePaths> imagePaths;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "user_code")
    private User user;

    /**
     * Empty Routine constructor.
     */
    public Routine() {
        this.title = "";
        this.person = "";
        this.instruction = "";
        this.user = null;
    }

    /**
     * Parametrized Routine constructor.
     *
     * @param title Routine title.
     * @param person Person associated with routine.
     * @param instruction Routine instructions.
     * @param images Image file list.
     * @param user The user that created the routine.
     */
    public Routine(String title, String person, String instruction, List<File> images, User user) {
        this.title = title;
        this.person = person;
        this.instruction = instruction;
        this.images = images;
        this.user = user;
    }

    /**
     * Getter for routine ID.
     *
     * @return The routine ID.
     */
    public int getRoutineCode() {
        return routineCode;
    }

    /**
     * Setter for routine ID.
     *
     * @param routineCode The new routine ID.
     */
    public void setRoutineCode(int routineCode) {
        this.routineCode = routineCode;
    }

    /**
     * Getter for routine title.
     *
     * @return The routine title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for routine title.
     *
     * @param title The new routine title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The getter for the person associated with the routine.
     *
     * @return The person associated with the routine.
     */
    public String getPerson() {
        return person;
    }

    /**
     * The setter for the person associated with the routine.
     *
     * @param person The new person associated with the routine.
     */
    public void setPerson(String person) {
        this.person = person;
    }

    /**
     * The getter for the routine instruction.
     *
     * @return The routine instruction.
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * The setter for the routine instruction.
     *
     * @param instruction The new routine instruction.
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * Getter for the user that created the routine.
     *
     * @return The user that created the routine.
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the user that created the routine.
     *
     * @param user The new user that created the routine.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter for image list.
     *
     * @return The image list.
     */
    public List<File> getImages() {
        return images;
    }

    /**
     * Setter for image list.
     *
     * @param images The new image list.
     */
    public void setImages(List<File> images) {
        this.images = images;
    }

    public List<ImagePaths> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<ImagePaths> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public void addFilePath(ImagePaths path) {
        imagePaths.add(path);
        path.setRoutine(this);
    }

    /**
     * ToString Method.
     *
     * @return Routine info.
     */
    @Override
    public String toString() {
        return "Rutina{" + "routineCode=" + routineCode + ", title=" + title + ", instruction=" + instruction + ", user=" + user + '}';
    }
}
