package kg.inai.inventoring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invents")
public class Invents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Name must not be null")
    private String name;

    @Column(name = "picture")
    private String picture;

    @Column(name = "qr")
    @NotNull(message = "QR must not be null")
    private String qr;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @NotNull(message = "Category must not be null")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "quality_id", referencedColumnName = "id")
    @NotNull(message = "Quality must not be null")
    private Quality quality;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @NotNull(message = "Location must not be null")
    private Location location;

    @Column(name = "client")
    @NotNull(message = "Client must not be null")
    private String client;

    @Column(name = "status")
    private String status;

    public Invents() {
    }

    public Invents(String name, String picture, String qr, Category category, Quality quality, Location location, String client, String status) {
        this.name = name;
        this.picture = picture;
        this.qr = qr;
        this.category = category;
        this.quality = quality;
        this.location = location;
        this.client = client;
        this.status = status;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invents{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", qr='" + qr + '\'' +
                ", category='" + category + '\'' +
                ", quality='" + quality + '\'' +
                ", location='" + location + '\'' +
                ", client='" + client + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

