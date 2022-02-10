package bike.community.model.entity.board;

import bike.community.model.common.DateBaseEntity;
import bike.community.model.enumclass.BoardType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import bike.community.model.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@DiscriminatorColumn
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)//단일 테이블 전략
public class Board extends DateBaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id; //TODO 필드 접근자 protected로 수정

    // 제목
    protected String title;

    // 작성자
    protected String writer;

    // 내용
    protected String content;

    // TODO 상운이 한테 말하기. 이거 쓸 필요 없음
    // 게시판 종류
//    @Enumerated(EnumType.STRING)
//    private BoardType type;

    // 조회수
    @ColumnDefault("0")
    protected Long views;


    @ManyToOne(fetch = FetchType.LAZY)
    protected User user;

    @Override
    public void prePersist() {
        views = views == null ? 0L : views;
    }

    public Board(String title, String writer, String content, User user) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.user = user;
    }

    public static Board create(String title,  String content, User user) {
        Board board = new Board();
        board.title = title;
        board.content = content;
        board.user = user;
        board.writer = user.getNickname();
        return board;
    }
}
