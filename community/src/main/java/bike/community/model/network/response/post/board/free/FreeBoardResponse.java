package bike.community.model.network.response.post.board.free;

import bike.community.model.network.response.user.UserWriterResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FreeBoardResponse {
    private Long id;
    private String title;
    private String content;
    private UserWriterResponse user;

    @QueryProjection
    public FreeBoardResponse(Long id, String title, String content, UserWriterResponse user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
