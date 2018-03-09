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

    @OneToOne(optional = false, targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true, nullable = false, name = "auth")
    private String authenticationString;

    @Column(nullable = false, columnDefinition = "DATETIME", name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate ;

    protected AuthenticationToken() { }

    public AuthenticationToken(User user, String authenticationString, Date expirationDate) {
        this.user = user;
        this.authenticationString = authenticationString;
        this.expirationDate = expirationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
