package kg.inai.inventoring.entity;

import lombok.NonNull;
import lombok.experimental.NonFinal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="location")

public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "location_name")
    private String location_name;

    public Location(Long id, String location_name) {
        this.id = id;
        this.location_name = location_name;
    }

    public Location() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location_name;
    }

    public void setLocation(String location_name) {
        this.location_name = location_name;
    }
}
