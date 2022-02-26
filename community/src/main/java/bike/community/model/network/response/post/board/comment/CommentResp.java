package bike.community.model.network.response.post.board.comment;

import bike.community.model.entity.comment.Comment;
import bike.community.model.network.request.post.board.comment.CommentReq;
import lombok.Data;

@Data
public class CommentResp{
    private String comment;
    private String writerNickname;

    public static CommentResp create(Comment comment) {
        CommentResp commentResp = new CommentResp();
        commentResp.comment = comment.getComment();
        commentResp.writerNickname = comment.getUser().getNickname();
        return commentResp;
    }
}
