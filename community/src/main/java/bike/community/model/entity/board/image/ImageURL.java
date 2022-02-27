package bike.community.model.entity.board.image;

import bike.community.model.entity.board.insta.InstaBoard;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
public class ImageURL {
    @Id @GeneratedValue
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="instaBoard_id")
    private InstaBoard instaBoard;

    public void addInstaBoard(InstaBoard instaBoard){
        this.instaBoard = instaBoard;
    }

    public static ImageURL create(String url, InstaBoard instaBoard) {
        ImageURL imageURL = new ImageURL();
        imageURL.url = url;
        imageURL.addInstaBoard(instaBoard);
        return imageURL;
    }
}
