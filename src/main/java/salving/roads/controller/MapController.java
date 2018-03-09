package salving.roads.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import salving.roads.domain.Note;
import salving.roads.domain.ProblemPoint;
import salving.roads.repository.AuthenticationTokenRepository;
import salving.roads.repository.NotesRepository;
import salving.roads.repository.ProblemPointRepository;
import salving.roads.utils.AuthUtils;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

@Controller
public class MapController {

    @Autowired
    public ProblemPointRepository pointRepository;

    @Autowired
    public NotesRepository notesRepository;

    @Autowired
    public AuthenticationTokenRepository authRepository;

    private String serializePoint(ProblemPoint point) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(point);
    }

    @ResponseBody
    @RequestMapping("/map/point/add")
    public String addPoint(@RequestHeader(name = "authString") String auth,
                           @RequestParam("latitude") double latitude,
                           @RequestParam("longitude") double longitude) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User not authorized";
        }

        if (!pointRepository.existsByLatitudeAndLongitude(latitude, longitude)) {
            pointRepository.save(new ProblemPoint(latitude, longitude));
            return ("Point saved | Id:" + pointRepository.findByLatitudeAndLongitude(latitude, longitude).getId());
        }

        return "0";
    }

    @ResponseBody
    @RequestMapping("/map/point/get")
    public String getPointByLatAndLong(@RequestHeader(name = "authString") String auth,
                                       @RequestParam("latitude") long latitude,
                                       @RequestParam("longitude") long longitude) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User not authorized";
        }

        if (pointRepository.existsByLatitudeAndLongitude(latitude, longitude)) {
            try {
                return serializePoint(pointRepository.findByLatitudeAndLongitude(latitude, longitude));
            } catch (JsonProcessingException e) {
                return "Error";
            }
        } else {
            return "Point not found";
        }


    }

    @ResponseBody
    @RequestMapping("/map/point/get/{id}")
    public String getPointById(@RequestHeader(name = "authString") String auth,
                               @PathVariable("id") long id) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User not authorized";
        }

        if (pointRepository.existsById(id)) {
            try {
                return serializePoint(pointRepository.findById(id));
            } catch (JsonProcessingException e) {
                return "Error";
            }
        } else {
            return "Point not Found";
        }


    }

    @ResponseBody
    @RequestMapping("/map/point/{id}/delete")
    public String removePoint(@RequestHeader(name = "authString") String auth,
                              @PathVariable("id") long id) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User not authorized";
        }

        if (!pointRepository.existsById(id)) {
            pointRepository.delete(id);
            return "Point deleted";
        }

        return "0";
    }

    @ResponseBody
    @RequestMapping("/map/point/{id}/note/add")
    public String addNote(@RequestHeader(name = "authString") String auth,
                          @PathVariable("id") long id,
                          @RequestParam("text") String text) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User not authorized";
        }

        if (!pointRepository.existsById(id)) {
            return "Point do not exist";
        }

        if (!notesRepository.existsNoteByText(text)) {
            Note note = new Note(text, pointRepository.findById(id));
            notesRepository.save(note);
            return "Note saved";

        } else {
            return "Note already exists";
        }
    }

    @ResponseBody
    @RequestMapping("/map/point/{id}/note/{noteId}/remove")
    public String removeNote(@RequestHeader(name = "authString") String auth,
                             @PathVariable("id") long id,
                             @PathVariable("noteId") long noteId) {
        if(!AuthUtils.authTokenIsValid(authRepository, auth)) {
            return "User not authorized";
        }

        if (!pointRepository.existsById(id)) {
            return "Point not found";
        }

        if (!notesRepository.existsNoteById(noteId)) {
            return "Can't find note";
        }

        notesRepository.delete(noteId);
        return "Note deleted";

    }


}














