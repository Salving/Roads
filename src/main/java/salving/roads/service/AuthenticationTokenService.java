package salving.roads.service;

import org.springframework.stereotype.Service;
import salving.roads.domain.AuthenticationToken;
import salving.roads.domain.User;
import salving.roads.utils.HashUtils;
import salving.roads.utils.TimeUtils;

@Service
public class AuthenticationTokenService {

    public AuthenticationToken getNewAuthenticationToken(User user){
        String authString = HashUtils.Hash(user.getLogin(), String.valueOf(System.currentTimeMillis()).getBytes());
        return new AuthenticationToken(user, authString, TimeUtils.tomorrow());
    }

}
