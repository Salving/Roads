package salving.roads.repository;

import org.springframework.data.repository.CrudRepository;
import salving.roads.domain.ProblemPoint;

import java.util.Date;
import java.util.List;

public interface ProblemPointRepository extends CrudRepository<ProblemPoint, Long> {
    ProblemPoint findById(long id);
    ProblemPoint findByLatitudeAndLongitude(double latitude, double longitude);
    List<ProblemPoint> findAllByCreationDateAfter(Date date);
    boolean existsById(long id);
    boolean existsByLatitudeAndLongitude(double latitude, double longitude);
}
