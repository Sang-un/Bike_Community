package bike.community.service.board.free_board;

import bike.community.model.entity.board.Free;
import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.free.FreeBoardRequest;
import bike.community.model.network.response.post.board.free.FreeBoardPageResponse;
import bike.community.model.network.response.post.board.free.FreeBoardResponse;
import bike.community.model.network.response.post.board.free.search_condition.FreeBoardSearchCond;
import bike.community.model.network.response.user.UserWriterResponse;
import bike.community.repository.board.free_board.FreeBoardRepository;
import bike.community.repository.user.UserRepository;
import bike.community.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;

    @Transactional
    public Header<FreeBoardResponse> create(FreeBoardRequest freeBoardRequest, HttpServletRequest request) throws IOException {
        User user = userRepository.findUserByEmail(tokenUtils.getEmailFromJwt(request));
        Free freeBoard = Free.create(freeBoardRequest.getTitle(), freeBoardRequest.getContent(), user);
        freeBoardRepository.save(freeBoard);

        FreeBoardResponse freeBoardResponse
                = new FreeBoardResponse(
                            freeBoard.getId(),
                            freeBoard.getTitle(),
                            freeBoard.getContent(),
                            new UserWriterResponse(user.getNickname(), user.getEmail())
                        );
        return Header.OK(freeBoardResponse);
    }

    public Header<Page<FreeBoardPageResponse>> searchPaging(Pageable pageable) {
        return Header.OK(freeBoardRepository.searchPaging(pageable));
    }

    public Header<FreeBoardResponse> findOne(Long id) {
        if(freeBoardRepository.findById(id).isPresent()){
            Free freeBoard = freeBoardRepository.findById(id).get();
            FreeBoardResponse freeBoardResponse
                    = new FreeBoardResponse(
                    freeBoard.getId(),
                    freeBoard.getTitle(),
                    freeBoard.getContent(),
                    new UserWriterResponse(freeBoard.getUser().getNickname(), freeBoard.getUser().getEmail())
            );

            return Header.OK(freeBoardResponse);
        }
        return Header.ERROR("???????????? ???????????? ????????????.");
    }

    public Header<Page<FreeBoardPageResponse>> searchByCond(FreeBoardSearchCond cond, Pageable pageable) {
        return Header.OK(freeBoardRepository.searchByCond(cond, pageable));
    }
}
