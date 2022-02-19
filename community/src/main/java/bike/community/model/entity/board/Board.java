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
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)//단일 테이블 전략
public class Board extends DateBaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id; //TODO 필드 접근자 protected로 수정

    // 제목
    protected String title;

    // 내용
    @Lob
    protected String content;

    // TODO 상운이 한테 말하기. 이거 쓸 필요 없음
    // 게시판 종류
//    @Enumerated(EnumType.STRING)
//    private BoardType type;

    @ColumnDefault("0")
    protected Long views;

    @ManyToOne(fetch = FetchType.LAZY, cascade=ALL)
    protected User user;

//    @OneToMany(mappedBy="board", cascade=CascadeType.ALL)
//    private List<AttachedFile> attachedFiles = new ArrayList<>();

    @Override
    public void prePersist() {
        views = views == null ? 0L : views;
    }

    public static Board create(FreeBoardRequest request, User user) throws IOException {
        Board board = new Board();
        board.title = request.getTitle();
        board.content = request.getContent();
        board.user = user;
//        FileStore fileStore = new FileStore();
//        List<AttachedFile> attachedFiles = fileStore.storeFiles(request.getImageFiles());
//        for (AttachedFile attachedFile : attachedFiles) {
//            board.getAttachedFiles().add(attachedFile);
//            attachedFile.addBoard(board);
//        }
        return board;
    }
}
