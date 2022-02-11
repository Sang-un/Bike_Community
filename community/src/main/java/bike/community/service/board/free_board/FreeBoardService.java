package bike.community.service.board.free_board;

import bike.community.model.entity.board.Free;
import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.free.FreeBoardRequest;
import bike.community.model.network.response.post.board.free.FreeBoardPageResponse;
import bike.community.model.network.response.post.board.free.FreeBoardResponse;
import bike.community.model.network.response.user.UserResponse;
import bike.community.model.network.response.user.UserWriterResponse;
import bike.community.repository.board.free_board.FreeBoardRepository;
import bike.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final UserRepository userRepository;

    public Header<FreeBoardResponse> create(FreeBoardRequest freeBoardRequest) {
        User user = userRepository.findUserByNickname(freeBoardRequest.getUserNickname());
        Free freeBoard = Free.create(freeBoardRequest.getTitle(), freeBoardRequest.getContent(), user);
        freeBoardRepository.save(freeBoard);
        FreeBoardResponse freeBoardResponse = FreeBoardResponse.builder()
                .id(freeBoard.getId())
                .title(freeBoard.getTitle())
                .content(freeBoard.getContent())
                .user(
                        UserWriterResponse.builder().email(user.getEmail()).nickname(user.getNickname()).build()
                )
                .build();
        return Header.OK(freeBoardResponse);
    }

    public Header<Page<FreeBoardPageResponse>> searchPaging(Pageable pageable) {
        return Header.OK(freeBoardRepository.searchPaging(pageable));
    }
}
