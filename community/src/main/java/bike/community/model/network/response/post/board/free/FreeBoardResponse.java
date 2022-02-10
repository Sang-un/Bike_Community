package bike.community.model.network.response.post.board.free;

import bike.community.model.network.response.user.UserResponse;
import bike.community.model.network.response.user.UserWriterResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FreeBoardResponse {
    private Long id;
    private String title;
    private String content;
    private UserWriterResponse user;

}
