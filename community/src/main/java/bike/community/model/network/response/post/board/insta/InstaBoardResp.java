package bike.community.model.network.response.post.board.insta;

import bike.community.model.entity.board.image.ImageURL;
import bike.community.model.entity.comment.Comment;
import bike.community.model.entity.user.User;
import bike.community.model.network.response.post.board.comment.CommentResp;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InstaBoardResp {
    private Long id;
    private String content;
    private List<ImageURL> images;
    private List<CommentResp> comments = new ArrayList<>();
    private String writer;

    public static InstaBoardResp create(Long id, String content, List<ImageURL> images, List<Comment> comments, User boardWriter) {
        InstaBoardResp instaBoardResp = new InstaBoardResp();
        instaBoardResp.id = id;
        instaBoardResp.content = content;
        instaBoardResp.images = images;
        instaBoardResp.writer = boardWriter.getNickname();
        for (Comment comment : comments) instaBoardResp.comments.add(CommentResp.create(comment));
        return instaBoardResp;
    }
}
