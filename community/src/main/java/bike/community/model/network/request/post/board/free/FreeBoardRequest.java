package bike.community.model.network.request.post.board.free;

import lombok.Data;

@Data
public class FreeBoardRequest {

    private String title;

    private String content;

    private String nickname;
}
