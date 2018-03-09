package salving.roads.repository;

import org.springframework.data.repository.CrudRepository;
import salving.roads.domain.ProblemPoint;

public interface ProblemPointRepository extends CrudRepository<ProblemPoint, Long> {
    ProblemPoint findById(long id);
    ProblemPoint findByLatitudeAndLongitude(double latitude, double longitude);
    boolean existsById(long id);
    boolean existsByLatitudeAndLongitude(double latitude, double longitude);
}
