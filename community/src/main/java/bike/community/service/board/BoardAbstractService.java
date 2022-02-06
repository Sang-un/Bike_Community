package bike.community.service.board;

import bike.community.model.network.Header;
import org.springframework.data.domain.Pageable;

public abstract class BoardAbstractService<ListResponse> {
    abstract Header<ListResponse> readAll(Pageable pageable);

    abstract Header<ListResponse> searchByWriter(String writer, Pageable pageable);

    abstract Header<ListResponse> searchByTitle(String title, Pageable pageable);

    abstract Header<ListResponse> searchByTitleOrContent(String title, String content, Pageable pageable);
}
