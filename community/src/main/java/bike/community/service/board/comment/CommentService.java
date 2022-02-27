package bike.community.service.board.comment;

import bike.community.component.exception.BoardNotFoundException;
import bike.community.model.entity.board.insta.InstaBoard;
import bike.community.model.entity.comment.Comment;
import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.comment.CommentReq;
import bike.community.model.network.response.post.board.insta.InstaBoardDetailResponse;
import bike.community.model.network.response.post.board.insta.InstaBoardPageResponse;
import bike.community.repository.board.insta_board.InstaBoardRepository;
import bike.community.repository.user.UserRepository;
import bike.community.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final InstaBoardRepository InstaBoardRepository;
    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;

    @Transactional
    public Header<InstaBoardDetailResponse> createComment(Long boardId, CommentReq commentReq, HttpServletRequest request) {
        User commentWriter = userRepository.findUserByEmail(tokenUtils.getEmailFromJwt(request));
        InstaBoard instaBoard = InstaBoardRepository.findInstaBoardOGById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));
        instaBoard.addComment(Comment.create(commentReq.getComment(), commentWriter));
        InstaBoardDetailResponse instaBoardDetailResp = InstaBoardDetailResponse.create(
                instaBoard.getId(),
                instaBoard.getContent(),
                instaBoard.getCreatedDate(),
                instaBoard.getThumbnailImageUrl(),
                instaBoard.getBoardWriter(),
                instaBoard.getImages(),
                instaBoard.getComments()
        );
        return Header.OK(instaBoardDetailResp);
    }
}
