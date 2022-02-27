package bike.community.controller.post.instaBoard;

import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.comment.CommentReq;
import bike.community.model.network.request.post.board.insta.InstaBoardRequest;
import bike.community.model.network.response.post.board.insta.InstaBoardDetailResponse;
import bike.community.model.network.response.post.board.insta.InstaBoardPageResponse;
import bike.community.service.board.comment.CommentService;
import bike.community.service.board.instaBoardService.InstaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * */
    @PostMapping("/api/user/insta")
    private Header<InstaBoardPageResponse> create(@RequestBody InstaBoardRequest instaBoardReq, HttpServletRequest request) {
        return boardService.create(instaBoardReq, request);
    }

    /*   instaBoard 상세 조회
     *   GET: /api/user/insta?board_id=3
     *   3번 아이디 게시글 조회
     * */
    @GetMapping("/api/user/insta")
    private Header<InstaBoardDetailResponse> findOne(@RequestParam Long board_id) {
        return boardService.findById(board_id);
    }

    /*   instaBoard 전체 조회
     *   GET: /api/user/insta-all
     *   pageable 인자에 맞게 게시글들 조회
     * */
    @GetMapping("/api/user/insta-all")
    private Header<Page<InstaBoardPageResponse>> findAll(Pageable pageable) {
        return boardService.findAll(pageable);
    }

    /*  comment 추가
    *   POST: /api/user/insta/comment?board_id=3
    *   {
    *       "comment" : "와우 정말 멋진 바이크네요~"
    *   }
    * */
    @PostMapping("/api/user/insta/comment")
    private Header<InstaBoardDetailResponse> createComment(@RequestParam Long boardId, @RequestBody CommentReq commentReq, HttpServletRequest request) {
        return commentService.createComment(boardId, commentReq, request);
    }
}
