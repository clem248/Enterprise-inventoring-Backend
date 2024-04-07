package kg.inai.inventoring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true)
    @NotNull(message = "Login must not be null")
    private String login;

    @Column(name = "password")
    @NotNull(message = "Password must not be null")
    private String password;

    @Column(name = "logo")
    private String logo;

    public Client(){
    }

    public Client(String login, String password, String logo) {
        this.login = login;
        this.password = password;
        this.logo = logo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
