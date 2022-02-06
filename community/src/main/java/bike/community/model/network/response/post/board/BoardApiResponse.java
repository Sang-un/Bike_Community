package bike.community.model.network.response.post.board;

import bike.community.model.enumclass.BoardType;

import java.time.LocalDateTime;

public class BoardApiResponse {
    private Long id;

    // 게시판 종류
    private BoardType boardType;

    // 재목
    private String title;

    // 작성자
    private String writer;

    // 조회수
    //private Long views;
    // 좋아요 수
    //private Long likes;

    // 작성일
    private LocalDateTime createAt;

    // 수정일
    private LocalDateTime updatedAt;

    // 파일 유무
    private Boolean hasFile;


}
