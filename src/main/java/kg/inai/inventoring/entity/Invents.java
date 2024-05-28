package kg.inai.inventoring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invents")
public class Invents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    @NotNull(message = "Name must not be null")
    private String name;

    @Column(name = "picture")
    private String picture;

    @Column(name = "qr")
    @NotNull(message = "Client must not be null")
    private String qr;

    @ManyToOne
    @JoinColumn(name = "category")
    @NotNull(message = "Client must not be null")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "quality")
    @NotNull(message = "Client must not be null")
    private Quality quality;

    @ManyToOne
    @JoinColumn(name = "location")
    @NotNull(message = "Client must not be null")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull(message = "Client must not be null")
    private Client client;

    public Invents() {
    }

    public Invents(String name, String picture, String qr, Category category, Quality quality, Location location, Client client) {
        this.name = name;
        this.picture = picture;
        this.qr = qr;
        this.category = category;
        this.quality = quality;
        this.location = location;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

