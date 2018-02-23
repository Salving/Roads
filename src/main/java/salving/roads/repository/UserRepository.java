package salving.roads.repository;

import org.springframework.data.repository.CrudRepository;
import salving.roads.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByLogin(String login);
    User findUserByEmail(String email);
    boolean existsUserByLogin(String login);
    boolean existsUserByEmail(String email);
}
