package bike.community.model.network.response.post.board;

import java.time.LocalDateTime;

public class BoardApiResponse {
    private Long id;

    // 재목
    private String title;

    // 작성자
    private String createdBy;

    // 내용
    private String content;

    // 조회수
    private Long views;

    // 좋아요 수
    private Long likes;

    // private Category category;

    // 작성일
    private LocalDateTime createAt;

    // 수정일
    private LocalDateTime updatedAt;


}
