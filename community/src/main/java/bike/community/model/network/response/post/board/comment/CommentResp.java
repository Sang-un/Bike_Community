package bike.community.model.network.response.post.board.comment;

import bike.community.model.entity.comment.Comment;
import lombok.Data;

@Data
public class CommentResp{
    private String comment;
    private String commentWriter;

    public static CommentResp create(Comment comment) {
        CommentResp commentResp = new CommentResp();
        commentResp.comment = comment.getComment();
        commentResp.commentWriter = comment.getCommentWriter();
        return commentResp;
    }
}
