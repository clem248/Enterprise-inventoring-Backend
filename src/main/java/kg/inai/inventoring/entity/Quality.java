package kg.inai.inventoring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="quality")

public class Quality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quality_name")
    private String name;

    public Quality(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Quality() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
