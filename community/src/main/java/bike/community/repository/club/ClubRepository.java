package bike.community.repository.club;

import bike.community.model.entity.club.Club;
import bike.community.model.entity.user.User;
import bike.community.repository.user.UserQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubQuerydslRepository {
}
