package salving.roads.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties({"password"})
@Entity
@Table(name="users")
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, name = "id")
    private long id;

    @Column(unique = true, nullable = false, name = "login")
    private String login;

    @Column(unique = true, nullable = false, name = "password")
    private String password;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "points", columnDefinition = "long default '0'")
    private long points;

    @OneToMany(mappedBy = "author")
    private List<Note> notes;

    protected  User() {}

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
