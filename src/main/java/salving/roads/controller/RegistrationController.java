package salving.roads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import salving.roads.domain.User;
import salving.roads.repository.UserRepository;

@Controller
public class RegistrationController {

    @Autowired
    public UserRepository userRepository;

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
}
