package bike.community.repository.user;

import bike.community.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQuerydslRepository {
    Optional<User> findOptionalByEmail(String email);
    Optional<User> findOptionalByNickname(String nickname);
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
}
