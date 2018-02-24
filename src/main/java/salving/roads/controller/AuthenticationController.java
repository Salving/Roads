package salving.roads.controller;

import org.h2.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import salving.roads.domain.AuthenticationToken;
import salving.roads.domain.User;
import salving.roads.repository.AuthenticationTokenRepository;
import salving.roads.repository.UserRepository;
import salving.roads.utils.HashUtils;
import salving.roads.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;

@Controller
public class AuthenticationController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public AuthenticationTokenRepository authenticationTokenRepository;

    @ResponseBody
    @RequestMapping("/user/register")
    public String registerUser(@RequestParam("login") String login,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email) {
        if(userRepository.existsUserByLogin(login)) {
            return "Login already registered";
        } else if(userRepository.existsUserByEmail(email)) {
            return "Email already registered";
        }

        userRepository.save(new User(login, password, email));

        return "User registered";
    }

    @ResponseBody
    @RequestMapping("/user/get/{login}")
    public String getUser(@PathVariable("login") String login) {
        StringBuilder sb = new StringBuilder();

        if(userRepository.existsUserByLogin(login)) {
            User user = userRepository.findUserByLogin(login);

            sb.append(String.format("Login - %s | ", user.getLogin()));
            sb.append(String.format("Email - %s | ", user.getEmail()));

            return sb.toString();
        }

        return "User does not exists";
    }

    @ResponseBody
    @RequestMapping("/user/auth")
    public String authenticate(@RequestParam("login") String login,
                               @RequestParam("password") String password) {
        if (userRepository.existsUserByLogin(login)) {
            User user = userRepository.findUserByLogin(login);

            if (user.getPassword().equals(password)) {
                if (authenticationTokenRepository.existsAuthenticationTokenByLogin(login)) {
                    authenticationTokenRepository.delete(authenticationTokenRepository.findAuthenticationTokenByLogin(login));
                }
                String authString = HashUtils.Hash(login, String.valueOf(System.currentTimeMillis()).getBytes());

                AuthenticationToken token = new AuthenticationToken(login, authString, TimeUtils.tomorrow());
                authenticationTokenRepository.save(token);

                return token.getAuthenticationString();
            }
        }

        return "Authentication failed";
    }
}
