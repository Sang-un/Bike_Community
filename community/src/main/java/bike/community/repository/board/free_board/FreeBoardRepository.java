package bike.community.repository.board.free_board;

import bike.community.model.entity.board.Free;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardRepository extends JpaRepository<Free, Long>, FreeBoardQuerydslRepository {

}
