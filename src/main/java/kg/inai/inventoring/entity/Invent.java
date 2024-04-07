package kg.inai.inventoring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invents")
public class Invent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    @NotNull(message = "Name must not be null")
    private String name;

    @Column(name = "picture")
    private String picture;

    // TODO: 07.04.2024 как хранить qr код, или штрих код, разобраться.
    @Column(name = "qr")
    private String qr;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull(message = "Client must not be null")
    private Client client;

    public Invent(){
    }
    public Invent(String name, String picture, String qr, Client client) {
        this.name = name;
        this.picture = picture;
        this.qr = qr;
        this.client = client;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
