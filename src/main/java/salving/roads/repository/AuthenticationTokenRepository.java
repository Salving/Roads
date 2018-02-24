package salving.roads.repository;

import org.springframework.data.repository.CrudRepository;
import salving.roads.domain.AuthenticationToken;

public interface AuthenticationTokenRepository extends CrudRepository<AuthenticationToken, Long> {
    AuthenticationToken findAuthenticationTokenByLogin(String login);
    AuthenticationToken findAuthenticationTokenByAuthenticationString(String string);
    boolean existsAuthenticationTokenByLogin(String login);
    boolean existsAuthenticationTokenByAuthenticationString(String string);
}
