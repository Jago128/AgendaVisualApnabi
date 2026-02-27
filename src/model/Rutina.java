package model;

import java.io.Serializable;
import javafx.scene.image.Image;
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
    
    @Transient
    private Image picto;
}
