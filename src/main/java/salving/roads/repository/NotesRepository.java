package salving.roads.repository;

import org.springframework.data.repository.CrudRepository;
import salving.roads.domain.Note;

import java.util.List;

public interface NotesRepository extends CrudRepository<Note, Long> {
    Note findNoteById(long id);
    Note findNoteByText(String text);
    List<Note> findNotesByPointId(long pointId);

    boolean existsNoteById(long id);
    boolean existsNoteByText(String text);
    boolean existsNoteByIdAndText(long id, String text);
}
