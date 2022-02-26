package bike.community.model.entity.board.insta;

import bike.community.model.common.DateBaseEntity;
import bike.community.model.entity.board.image.ImageURL;
import bike.community.model.entity.comment.Comment;
import bike.community.model.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
public class InstaBoard extends DateBaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="instaBoard_id")
    private Long id;

    private String content;

    @ColumnDefault("0")
    private int heart;

    @OneToMany(mappedBy="instaBoard", cascade=CascadeType.ALL)
    private List<ImageURL> images = new ArrayList<>();

    @OneToMany(mappedBy="instaBoard", cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public static InstaBoard create(String content, List<String> imageURLs, User user) {
        InstaBoard instaBoard = new InstaBoard();
        instaBoard.content = content;
        instaBoard.user = user;
        for (String imageURL : imageURLs) instaBoard.images.add(ImageURL.create(imageURL, instaBoard));
        return instaBoard;
    }

    public void addComment(Comment Comment){
        this.comments.add(Comment);
        Comment.addBoard(this);
    }
}