package salving.roads.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import salving.roads.domain.Note;
import salving.roads.domain.ProblemPoint;
import salving.roads.domain.User;
import salving.roads.repository.AuthenticationTokenRepository;
import salving.roads.repository.NotesRepository;
import salving.roads.repository.ProblemPointRepository;
import salving.roads.service.JsonParseService;
import salving.roads.utils.AuthUtils;
import salving.roads.utils.TimeUtils;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MapController {

    @Autowired
    private ProblemPointRepository pointRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private AuthenticationTokenRepository authRepository;

    @Autowired
    private JsonParseService jsonParseService;

    @ResponseBody
    @RequestMapping("/map/point/add")
    public String addPoint(@RequestHeader(name = "authString") String auth,
                           @RequestParam("latitude") double latitude,
                           @RequestParam("longitude") double longitude) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User_not_authorized";
        }

        if (!pointRepository.existsByLatitudeAndLongitude(latitude, longitude)) {
            pointRepository.save(new ProblemPoint(latitude, longitude));
            return String.valueOf(pointRepository.findByLatitudeAndLongitude(latitude, longitude).getId());
        }


        return "0";
    }

    @ResponseBody
    @RequestMapping("/map/point/get")
    public String getPointByLatAndLong(@RequestHeader(name = "authString") String auth,
                                       @RequestParam("latitude") double latitude,
                                       @RequestParam("longitude") double longitude) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User_not_authorized";
        }

        if (pointRepository.existsByLatitudeAndLongitude(latitude, longitude)) {
            try {
                return jsonParseService.serialize(pointRepository.findByLatitudeAndLongitude(latitude, longitude));
            } catch (JsonProcessingException e) {
                return "Error";
            }
        } else {
            return "Point_not_found";
        }

    }

    @ResponseBody
    @RequestMapping("/map/point/get/{id}")
    public String getPointById(@RequestHeader(name = "authString") String auth,
                               @PathVariable("id") long id) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User_not_authorized";
        }

        if (pointRepository.existsById(id)) {
            try {
                return jsonParseService.serialize(pointRepository.findById(id));
            } catch (JsonProcessingException e) {
                return "Error";
            }
        } else {
            return "Point_not_Found";
        }

    }

    @ResponseBody
    @RequestMapping("/map/point/get/after")
    public String getAllPointsAfterDate(@RequestHeader(name = "authString") String auth,
                                        @RequestParam("date") String date) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User_not_authorized";
        }

        Date after;
        try {
            after = new SimpleDateFormat(TimeUtils.DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            return "Error";
        }
        List<ProblemPoint> points = pointRepository.findAllByCreationDateAfter(after);

        if (points.isEmpty()) {
            return "Points_not_found";
        } else {
            try {
                return jsonParseService.serialize(points);
            } catch (JsonProcessingException e) {
                return "Error";
            }
        }
    }

    @ResponseBody
    @RequestMapping("/map/point/{id}/delete")
    public String removePoint(@RequestHeader(name = "authString") String auth,
                              @PathVariable("id") long id) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User_not_authorized";
        }

        if (pointRepository.existsById(id)) {
            ProblemPoint point = pointRepository.findById(id);
            if (point.getNotes().isEmpty()) {
                pointRepository.delete(id);
                return "Point_deleted";
            } else {
                return "Point_has_Notes";
            }

        }

        return "0";
    }

    @ResponseBody
    @RequestMapping("/map/point/{id}/note/add")
    public String addNote(@RequestHeader(name = "authString") String auth,
                          @PathVariable("id") long id,
                          @RequestParam("text") String text) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User_not_authorized";
        }

        if (!pointRepository.existsById(id)) {
            return "Point_do_not_exist";
        }

        User user = authRepository.findAuthenticationTokenByAuthenticationString(auth).getUser();

        if (!notesRepository.existsNoteByPointAndText(pointRepository.findById(id), text)) {
            Note note = new Note(text, pointRepository.findById(id), user);
            notesRepository.save(note);
            return "Note_saved";
        } else {
            return "Note_already_exists";
        }
    }

    @ResponseBody
    @RequestMapping("/map/point/{id}/note/{noteId}/remove")
    public String removeNote(@RequestHeader(name = "authString") String auth,
                             @PathVariable("id") long id,
                             @PathVariable("noteId") long noteId) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User_not_authorized";
        }

        if (!pointRepository.existsById(id)) {
            return "Point_not_found";
        }

        if (!notesRepository.existsNoteById(noteId)) {
            return "Can't_find_note";
        }

        notesRepository.delete(noteId);
        return "Note_deleted";

    }


}














