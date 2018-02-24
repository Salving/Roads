package salving.roads.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "auth_tokens")
public class AuthenticationToken implements Serializable {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, name = "id")
    private long id;

    @Column(unique = true, nullable = false, name = "login")
    private String login;

    @Column(unique = true, nullable = false, name = "auth")
    private String authenticationString;

    @Column(nullable = false, columnDefinition = "DATETIME", name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate ;

    protected AuthenticationToken() { }

    public AuthenticationToken(String login, String authenticationString, Date expirationDate) {
        this.login = login;
        this.authenticationString = authenticationString;
        this.expirationDate = expirationDate;
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

    public String getAuthenticationString() {
        return authenticationString;
    }

    public void setAuthenticationString(String authenticationString) {
        this.authenticationString = authenticationString;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
