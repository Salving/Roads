package salving.roads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
            return "Point not found";
        }
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User not authorized";
        }

        String message = mailService.buildMessage(id);
        if(!message.equals("Point not found")) {
            mailService.send(message, "pmfr@yandex.ru");
            mailService.send(message, "sergey.2123@yandex.ru");
            return "mMessage sent successfully";
        } else {
            return "Point not found";
        }
    }

}
