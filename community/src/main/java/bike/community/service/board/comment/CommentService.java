package bike.community.service.board.comment;

import bike.community.component.exception.UserNotFoundException;
import bike.community.model.entity.board.insta.InstaBoard;
import bike.community.model.entity.comment.Comment;
import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.comment.CommentReq;
import bike.community.model.network.response.post.board.insta.InstaBoardResp;
import bike.community.repository.board.insta_board.InstaBoardRepository;
import bike.community.repository.user.UserRepository;
import bike.community.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final InstaBoardRepository InstaBoardRepository;
    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;

    @Transactional
    public Header<InstaBoardResp> create(CommentReq commentReq, HttpServletRequest request) {
        User boardWriter = userRepository.findUserByEmail(tokenUtils.getEmailFromJwt(request));
        InstaBoard instaBoard = InstaBoardRepository.findById(commentReq.getBoardId()).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        instaBoard.addComment(Comment.create(commentReq.getComment(), boardWriter));
        InstaBoardResp instaBoardResp = InstaBoardResp.create(instaBoard.getId(), instaBoard.getContent(), instaBoard.getImages(), instaBoard.getComments(), boardWriter);
        return Header.OK(instaBoardResp);
    }

}
