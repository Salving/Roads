package salving.roads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import salving.roads.domain.User;
import salving.roads.repository.AuthenticationTokenRepository;
import salving.roads.repository.NotesRepository;
import salving.roads.repository.ProblemPointRepository;
import salving.roads.service.MailService;
import salving.roads.utils.AuthUtils;

@Controller
public class CityServiceController {

    @Autowired
    private ProblemPointRepository pointRepository;

    @Autowired
    private AuthenticationTokenRepository authRepository;

    @Autowired
    private MailService mailService;

    @ResponseBody
    @RequestMapping("/city/send/{id}")
    public String sendComplaint(@RequestHeader(name = "authString") String auth,
                                @PathVariable("id") long id) {
        if (!pointRepository.existsById(id)) {
            return "Point_not_found";
        }
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User_not_authorized";
        }

        User user = authRepository.findAuthenticationTokenByAuthenticationString(auth).getUser();


        String message = mailService.buildUserMessage(id, user);
        if(!message.equals("Point not found")) {
            mailService.send(message, user.getEmail());
            return "Message_sent_successfully";
        } else {
            return "Point_not_found";
        }
    }

}
