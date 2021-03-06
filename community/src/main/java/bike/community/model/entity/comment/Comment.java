package bike.community.model.entity.comment;

import bike.community.model.entity.board.insta.InstaBoard;
import bike.community.model.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
public class Comment{

    @Id @GeneratedValue
    private Long id;

    private String comment;

    private String commentWriter;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="instaBoard_id")
    private InstaBoard instaBoard;

    public static Comment create(String comment, User commentWriter) {
        Comment Comment = new Comment();
        Comment.user = commentWriter;
        Comment.commentWriter = commentWriter.getNickname();
        Comment.comment = comment;
        return Comment;
    }

    public void addBoard(InstaBoard instaBoard) {
        this.instaBoard = instaBoard;
    }
}
