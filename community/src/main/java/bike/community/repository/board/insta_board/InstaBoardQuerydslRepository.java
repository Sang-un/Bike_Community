package bike.community.repository.board.insta_board;

import bike.community.model.entity.board.insta.InstaBoard;
import bike.community.model.network.response.post.board.insta.InstaBoardDetailResponse;
import bike.community.model.network.response.post.board.insta.InstaBoardPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InstaBoardQuerydslRepository {
    Page<InstaBoardPageResponse> searchPaging(Pageable pageable);

    Optional<InstaBoardDetailResponse> findInstaBoardById(Long boardId);

    Optional<InstaBoard> findInstaBoardOGById(Long boardId);
}
