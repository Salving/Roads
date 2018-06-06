package salving.roads.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import salving.roads.domain.AuthenticationToken;
import salving.roads.domain.User;
import salving.roads.repository.AuthenticationTokenRepository;
import salving.roads.repository.UserRepository;
import salving.roads.service.AuthenticationTokenService;
import salving.roads.service.JsonParseService;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationTokenRepository authenticationTokenRepository;

    @Autowired
    private AuthenticationTokenService authTokenService;

    @Autowired
    private JsonParseService jsonParseService;

    @ResponseBody
    @RequestMapping("/user/register")
    public String registerUser(@RequestParam("login") String login,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email) {
        if(userRepository.existsUserByLogin(login)) {
            return "Login_already_registered";
        } else if(userRepository.existsUserByEmail(email)) {
            return "Email_already_registered";
        }

        userRepository.save(new User(login, password, email));

        return "User_registered";
    }

    @ResponseBody
    @RequestMapping("/user/get/{login}")
    public String getUser(@PathVariable("login") String login) {
        StringBuilder sb = new StringBuilder();

        if(userRepository.existsUserByLogin(login)) {
            try {
                return jsonParseService.serialize(userRepository.findUserByLogin(login));
            } catch (JsonProcessingException e) {
                return "Error";
            }
        }

        return "User_does_not_exists";
    }

    @ResponseBody
    @RequestMapping("/user/auth")
    public String authenticate(@RequestParam("login") String login,
                               @RequestParam("password") String password) {
        if (userRepository.existsUserByLogin(login)) {
            User user = userRepository.findUserByLogin(login);

            if (user.getPassword().equals(password)) {
                if (authenticationTokenRepository.existsAuthenticationTokenByUser(user)) {
                    authenticationTokenRepository.delete(authenticationTokenRepository.findAuthenticationTokenByUser(user));
                }

                AuthenticationToken token = authTokenService.getNewAuthenticationToken(user);
                authenticationTokenRepository.save(token);

                return token.getAuthenticationString();
            }
        }

        return "Authentication_failed";
    }
}
