package kg.inai.inventoring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "clients")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true)
    @NotNull(message = "Login must not be null")
    private String login;

    @Column(name = "full_name")
    @NotNull(message = "Full name must not be null")
    private String fullName;

    @Column(name = "password")
    @NotNull(message = "Password must not be null")
    private String password;

    @Column(name = "ip_address")
    @NotNull(message = "IP Address must not be null")
    private String ipAddresses;


    public Client() {
    }

    public Client(String login, String fullName, String password, String ipAddresses) {
        this.login = login;
        this.fullName = fullName;
        this.password = password;
        this.ipAddresses = ipAddresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(String ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", ipAddresses='" + ipAddresses + '\'' +
                '}';
    }
}