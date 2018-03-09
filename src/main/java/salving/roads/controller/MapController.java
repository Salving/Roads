package salving.roads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import salving.roads.domain.Note;
import salving.roads.domain.ProblemPoint;
import salving.roads.repository.AuthenticationTokenRepository;
import salving.roads.repository.NotesRepository;
import salving.roads.repository.ProblemPointRepository;
import salving.roads.utils.AuthUtils;

@Controller
public class MapController {

    @Autowired
    public ProblemPointRepository pointRepository;

    @Autowired
    public NotesRepository notesRepository;

    @Autowired
    public AuthenticationTokenRepository authRepository;

    @ResponseBody
    @RequestMapping("/map/points/add")
    public String addPoint(@RequestHeader(name = "authToken") String auth,
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
    @RequestMapping("/map/points/{id}/delete")
    public String removePoint(@RequestHeader(name = "authToken") String auth,
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
    @RequestMapping("/map/points/{id}/notes/add")
    public String addNote(@RequestHeader(name = "authToken") String auth,
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
    @RequestMapping("/map/points/{id}/notes/{noteId}/remove")
    public String removeNote(@RequestHeader(name = "authToken") String auth,
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














