package bike.community.controller.post.instaBoard;

import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.comment.CommentReq;
import bike.community.model.network.request.post.board.insta.InstaBoardReq;
import bike.community.model.network.response.post.board.insta.InstaBoardResp;
import bike.community.service.board.comment.CommentService;
import bike.community.service.board.instaBoardService.InstaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class InstaBoardController {
    private final InstaBoardService boardService;
    private final CommentService commentService;

    /*   instaBoard 추가
     *   POST: /api/user/insta
     *   {
     *       "comment" : "와우 정말 멋진 바이크네요~",
     *       "images" : [ 이미지1, 이미지2 ]
     *   }
     *
     * */
    @PostMapping("/api/user/insta")
    private Header<InstaBoardResp> create(@RequestBody InstaBoardReq instaBoardReq, HttpServletRequest request) {
        return boardService.create(instaBoardReq, request);
    }

    /*   instaBoard 조회
     *   GET: /api/user/insta?boardId=3
     *   3번 아이디
     * */
    @GetMapping("/api/user/insta")
    private Header<InstaBoardResp> create(@RequestParam Long boardId) {
        return boardService.findById(boardId);
    }

    /*  comment 추가
    *   POST: /api/user/insta?boardId=3
    *   {
    *       "comment" : "와우 정말 멋진 바이크네요~"
    *   }
    * */
    //comment 추가
    @PostMapping("/api/user/insta/comment")
    private Header<InstaBoardResp> addComment(@RequestParam Long boardId, @RequestBody CommentReq commentReq, HttpServletRequest request) {
        return commentService.create(commentReq, request);
    }






}
