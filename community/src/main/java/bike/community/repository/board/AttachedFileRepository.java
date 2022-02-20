package bike.community.repository.board;

import bike.community.model.entity.board.image.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {

}
