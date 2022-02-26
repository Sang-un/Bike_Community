package bike.community.service.board.instaBoardService;

import bike.community.model.entity.board.insta.InstaBoard;
import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.insta.InstaBoardReq;
import bike.community.model.network.response.post.board.insta.InstaBoardResp;
import bike.community.repository.board.insta_board.InstaBoardRepository;
import bike.community.repository.user.UserRepository;
import bike.community.security.jwt.TokenUtils;
import bike.community.service.image.AwsS3Service;
import lombok.RequiredArgsConstructor;
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

    public Header<InstaBoardResp> create(InstaBoardReq instaBoardReq, HttpServletRequest request) {
        User boardWriter = userRepository.findUserByNickname(tokenUtils.getNicknameFromJwt(request));
        InstaBoard instaBoard = InstaBoard.create(instaBoardReq.getContent(), awsS3Service.uploadImages(instaBoardReq.getImages()), boardWriter);
        instaBoardRepository.save(instaBoard);
        InstaBoardResp instaBoardResp = InstaBoardResp.create(instaBoard.getId(), instaBoard.getContent(), instaBoard.getImages(), instaBoard.getComments(), boardWriter);
        return Header.OK(instaBoardResp);
    }

    public Header<InstaBoardResp> findById(Long boardId) {

        return null;
    }
}