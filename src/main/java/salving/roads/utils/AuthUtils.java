package salving.roads.utils;

import salving.roads.domain.AuthenticationToken;
import salving.roads.repository.AuthenticationTokenRepository;

import java.util.Date;

public abstract class AuthUtils {
    public static boolean authTokenIsValid(AuthenticationTokenRepository repository, String authString) {
        if (repository.existsAuthenticationTokenByAuthenticationString(authString)) {
            AuthenticationToken token = repository.findAuthenticationTokenByAuthenticationString(authString);
            return token.getExpirationDate().after(new Date());
        }

        return false;
    }
}
