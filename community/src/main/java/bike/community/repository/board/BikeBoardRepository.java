package bike.community.repository.board;

import bike.community.model.entity.board.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeBoardRepository extends JpaRepository<Bike, Long> {

}
