package salving.roads.repository;

import org.springframework.data.repository.CrudRepository;
import salving.roads.domain.AuthenticationToken;
import salving.roads.domain.User;

public interface AuthenticationTokenRepository extends CrudRepository<AuthenticationToken, Long> {
    AuthenticationToken findAuthenticationTokenByUser(User user);
    AuthenticationToken findAuthenticationTokenByAuthenticationString(String string);
    boolean existsAuthenticationTokenByUser(User user);
    boolean existsAuthenticationTokenByAuthenticationString(String string);
}
