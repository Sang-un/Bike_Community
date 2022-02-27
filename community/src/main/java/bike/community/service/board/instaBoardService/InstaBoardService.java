package bike.community.service.board.instaBoardService;

import bike.community.component.exception.BoardNotFoundException;
import bike.community.model.entity.board.insta.InstaBoard;
import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.insta.InstaBoardRequest;
import bike.community.model.network.response.post.board.insta.InstaBoardDetailResponse;
import bike.community.model.network.response.post.board.insta.InstaBoardPageResponse;
import bike.community.repository.board.insta_board.InstaBoardRepository;
import bike.community.repository.user.UserRepository;
import bike.community.security.jwt.TokenUtils;
import bike.community.service.image.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class InstaBoardService {

    private final UserRepository userRepository;
    private final InstaBoardRepository instaBoardRepository;
    private final TokenUtils tokenUtils;
    private final AwsS3Service awsS3Service;

    @Transactional
    public Header<InstaBoardPageResponse> create(InstaBoardRequest instaBoardReq, HttpServletRequest request) {
        User boardWriter = userRepository.findUserByNickname(tokenUtils.getNicknameFromJwt(request));
        InstaBoard saveInstaBoard = instaBoardRepository.save(
                                        InstaBoard.create(
                                                instaBoardReq.getContent(),
                                                awsS3Service.uploadImage(instaBoardReq.getThumbnailImageUrl()),
                                                awsS3Service.uploadImages(instaBoardReq.getImages()),
                                                boardWriter
                                        )
                                    );
        return Header.OK(
                InstaBoardPageResponse.create(
                        saveInstaBoard.getId(),
                        saveInstaBoard.getContent(),
                        saveInstaBoard.getCreatedDate(),
                        saveInstaBoard.getThumbnailImageUrl(),
                        boardWriter.getNickname()
                ));
    }

    public Header<InstaBoardDetailResponse> findById(Long boardId) {
        return Header.OK(
                instaBoardRepository.findInstaBoardById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId))
        );
    }

    public Header<Page<InstaBoardPageResponse>> findAll(Pageable pageable) {
        return Header.OK(instaBoardRepository.searchPaging(pageable));
    }
}