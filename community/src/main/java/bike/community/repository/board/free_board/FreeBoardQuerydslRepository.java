package bike.community.repository.board.free_board;

import bike.community.model.network.response.post.board.free.FreeBoardPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FreeBoardQuerydslRepository {

    Page<FreeBoardPageResponse> searchPaging(Pageable pageable);
}
