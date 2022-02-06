package bike.community.model.network.request.post.board;

import bike.community.model.enumclass.BoardType;

public class BoardApiRequest {
    private Long id;

    // 재목
    private String title;

    // 내용
    private String content;

    // 게시판 종류
    private BoardType boardType;


    // 익명 유무
    // 익명 = true
    // 실명 = false
    private Boolean isAnonymous;
}
