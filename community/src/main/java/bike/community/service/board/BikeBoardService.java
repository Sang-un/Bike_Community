package bike.community.service.board;

import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.BoardApiRequest;
import bike.community.model.network.response.post.board.BoardApiResponse;
import bike.community.model.network.response.post.board.BoardResponseDTO;
import bike.community.service.CrudServiceInterface;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BikeBoardService extends BoardAbstractService<BoardResponseDTO> implements CrudServiceInterface<BoardApiRequest,BoardApiResponse> {

    @Override
    public Header<BoardApiResponse> create(User user, BoardApiRequest boardApiRequest, MultipartFile[] files) {
        return null;
    }

    @Override
    public Header<BoardApiResponse> update(User user, BoardApiRequest boardApiRequest, MultipartFile[] files, List<Long> delFileIdList) {
        return null;
    }

    @Override
    public Header delete(User user, Long id) {
        return null;
    }

    @Override
    Header<BoardResponseDTO> readAll(Pageable pageable) {
        return null;
    }

    @Override
    Header<BoardResponseDTO> searchByWriter(String writer, Pageable pageable) {
        return null;
    }

    @Override
    Header<BoardResponseDTO> searchByTitle(String title, Pageable pageable) {
        return null;
    }

    @Override
    Header<BoardResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        return null;
    }

    // create, update response
//    public Header<BoardApiResponse> response(){
//        return ;
//    }
}

