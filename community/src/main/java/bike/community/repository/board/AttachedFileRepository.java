package bike.community.repository.board;

import bike.community.model.entity.board.AttachedFile;
import bike.community.model.entity.board.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {

}
