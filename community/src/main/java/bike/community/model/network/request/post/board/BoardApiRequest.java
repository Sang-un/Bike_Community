package bike.community.model.network.request.post.board;

public class BoardApiRequest {
    private Long id;

    // 재목
    private String title;

    // 내용
    private String content;

    // 작성자
    private String createdBy;

    // 익명 유무
    // 익명 = true
    // 실명 = false
    private Boolean isAnonymous;
}
