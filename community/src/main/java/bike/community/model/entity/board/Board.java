package bike.community.model.entity.board;

import bike.community.component.FileStore;
import bike.community.model.common.DateBaseEntity;
import bike.community.model.enumclass.BoardType;
import bike.community.model.network.request.post.board.free.FreeBoardRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import bike.community.model.entity.user.User;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity @Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@DiscriminatorColumn
@Inheritance(strategy=InheritanceType.JOINED)//단일 테이블 전략
public class Board extends DateBaseEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;

    @Lob
    protected String content;

    @ColumnDefault("0")
    protected Long views;

    @ManyToOne(fetch = FetchType.LAZY, cascade=ALL)
    protected User user;

    @Override
    public void prePersist() {
        views = views == null ? 0L : views;
    }

    public static Board create(FreeBoardRequest request, User user) throws IOException {
        Board board = new Board();
        board.title = request.getTitle();
        board.content = request.getContent();
        board.user = user;
        return board;
    }
}
