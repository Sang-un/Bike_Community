package bike.community.model.network.response.post.board.free;

import bike.community.model.network.response.user.UserWriterResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FreeBoardPageResponse {
    private Long id;
    private String title;
    private UserWriterResponse user;

    @QueryProjection
    public FreeBoardPageResponse(Long id, String title, UserWriterResponse user) {
        this.id = id;
        this.title = title;
        this.user = user;
    }
}
