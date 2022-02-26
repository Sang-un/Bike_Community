package bike.community.model.network.request.post.board.comment;

import bike.community.model.entity.comment.Comment;
import lombok.Data;

@Data
public class CommentReq {
    private String comment;
    private Long boardId;


}
