package bike.community.model.entity.board;

import bike.community.model.common.DateBaseEntity;
import bike.community.model.enumclass.BoardType;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import bike.community.model.entity.user.User;

import javax.persistence.*;

@Entity
@Getter
public class Board extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    private String title;

    // 작성자
    private String writer;

    // 내용
    private String content;

    // 게시판 종류
    @Enumerated(EnumType.STRING)
    private BoardType type;

    // 조회수
    @ColumnDefault("0")
    private Long views;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public void prePersist() {
        views = views == null ? 0L : views;
    }
}
