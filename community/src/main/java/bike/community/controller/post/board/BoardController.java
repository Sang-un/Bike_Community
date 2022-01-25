package bike.community.controller.post.board;

import bike.community.controller.CrudInterface;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.BoardApiRequest;
import bike.community.model.network.response.post.board.BoardApiResponse;


public class BoardController implements CrudInterface<BoardApiRequest, BoardApiResponse> {

    @Override
    public Header<BoardApiResponse> create(BoardApiRequest boardApiRequest) {
        return null;
    }

    @Override
    public Header<BoardApiResponse> read(BoardApiRequest boardApiRequest) {
        return null;
    }

    @Override
    public Header<BoardApiResponse> update(BoardApiRequest boardApiRequest) {
        return null;
    }

    @Override
    public Header delete(BoardApiRequest boardApiRequest) {
        return null;
    }
}
