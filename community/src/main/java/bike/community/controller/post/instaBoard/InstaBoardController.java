package bike.community.controller.post.instaBoard;

import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.comment.CommentReq;
import bike.community.model.network.request.post.board.insta.InstaBoardReq;
import bike.community.model.network.response.post.board.insta.InstaBoardResp;
import bike.community.service.board.comment.CommentService;
import bike.community.service.board.instaBoardService.InstaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class InstaBoardController {
    private final InstaBoardService boardService;
    private final CommentService commentService;

    @PostMapping("/api/user/insta")
    private Header<InstaBoardResp> create(@RequestBody InstaBoardReq instaBoardReq, HttpServletRequest request) {
        return boardService.create(instaBoardReq, request);
    }

    //comment 추가
    @PostMapping
    private Header<InstaBoardResp> create(@RequestBody CommentReq commentReq, HttpServletRequest request) {
        return commentService.create(commentReq, request);
    }

}
