package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "IMAGE_PATH")
public class ImagePaths implements Serializable {
    
    @Id
    private int pathId;
    
    @Column(name = "filePath", nullable = false, length = 999)
    private String filePath;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_routine", nullable = false, referencedColumnName = "routine_code")
    private Routine routine;
    
    public ImagePaths() {
        this.filePath = "";
        this.routine = null;
    }
    
    public ImagePaths(String filePath, Routine routine) {
        this.filePath = filePath;
        this.routine = routine;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }
}
