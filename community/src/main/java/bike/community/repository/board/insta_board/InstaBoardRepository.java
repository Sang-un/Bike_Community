package bike.community.repository.board.insta_board;

import bike.community.model.entity.board.insta.InstaBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstaBoardRepository extends JpaRepository<InstaBoard, Long>, InstaBoardQuerydslRepository {
}
