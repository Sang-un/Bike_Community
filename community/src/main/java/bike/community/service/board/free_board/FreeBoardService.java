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


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;

//    @Transactional
//    public Header<FreeBoardResponse> create(FreeBoardRequest freeBoardRequest) {
//        User user = userRepository.findUserByNickname(freeBoardRequest.getNickname());
//        Free freeBoard = Free.create(freeBoardRequest.getTitle(), freeBoardRequest.getContent(), user);
//        freeBoardRepository.save(freeBoard);
//        FreeBoardResponse freeBoardResponse = FreeBoardResponse.builder()
//                .id(freeBoard.getId())
//                .title(freeBoard.getTitle())
//                .content(freeBoard.getContent())
//                .user(
//                        UserWriterResponse.builder().email(user.getEmail()).nickname(user.getNickname()).build()
//                )
//                .build();
//        return Header.OK(freeBoardResponse);
//    }

    @Transactional
    public Header<FreeBoardResponse> create(FreeBoardRequest freeBoardRequest, HttpServletRequest request) {
        String nickname = tokenUtils.getNicknameFromJwt(request);
        String email = tokenUtils.getEmailFromJwt(request);
        User user = userRepository.findUserByNickname(nickname);
        Free freeBoard = Free.create(freeBoardRequest.getTitle(), freeBoardRequest.getContent(), user);
        freeBoardRepository.save(freeBoard);
        FreeBoardResponse freeBoardResponse = FreeBoardResponse.builder()
                .id(freeBoard.getId())
                .title(freeBoard.getTitle())
                .content(freeBoard.getContent())
                .user(
                        UserWriterResponse.builder().email(email).nickname(nickname).build()
                )
                .build();
        return Header.OK(freeBoardResponse);
    }

    public Header<Page<FreeBoardPageResponse>> searchPaging(Pageable pageable) {
        return Header.OK(freeBoardRepository.searchPaging(pageable));
    }

    public Header<FreeBoardResponse> findOne(Long id) {
        if(freeBoardRepository.findById(id).isPresent()){
            Free freeBoard = freeBoardRepository.findById(id).get();
            FreeBoardResponse result = FreeBoardResponse.builder()
                    .id(freeBoard.getId())
                    .title(freeBoard.getTitle())
                    .content(freeBoard.getContent())
                    .user(UserWriterResponse.builder()
                            .email(freeBoard.getUser().getEmail())
                            .nickname(freeBoard.getUser().getNickname())
                            .build())
                    .build();
            return Header.OK(result);
        }
        return Header.ERROR("게시물이 존재하지 않습니다.");
    }

    public Header<Page<FreeBoardPageResponse>> searchByCond(FreeBoardSearchCond cond, Pageable pageable) {
        return Header.OK(freeBoardRepository.searchByCond(cond, pageable));
    }
}
