package bike.community.model.network.response.post.board.insta;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class InstaBoardPageResponse {
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private String thumbnailImageUrl;
    private String boardWriter;

    public static InstaBoardPageResponse create(Long id, String content, LocalDateTime createdDate, String thumbnailImageUrl, String boardWriter) {
        InstaBoardPageResponse instaBoardResp = new InstaBoardPageResponse();
        instaBoardResp.id = id;
        instaBoardResp.content = content;
        instaBoardResp.createdDate = createdDate;
        instaBoardResp.boardWriter = boardWriter;
        instaBoardResp.thumbnailImageUrl = thumbnailImageUrl;
        return instaBoardResp;
    }

    @QueryProjection
    public InstaBoardPageResponse(Long id, String content, LocalDateTime createdDate, String thumbnailImageUrl, String boardWriter) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.boardWriter = boardWriter;
    }
}
