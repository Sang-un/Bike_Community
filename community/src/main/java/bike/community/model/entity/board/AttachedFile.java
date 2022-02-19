package bike.community.model.entity.board;

import javax.persistence.*;

@Entity
public class AttachedFile {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="attached_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id")
    private Board board;

    private String storeFilename;
    private String uploadFilename;

    private void setId(Long id) {
        this.id = id;
    }
    private void setWriting(Board board) {
        this.board = board;
    }
    private void setStoreFilename(String storeFilename) {
        this.storeFilename = storeFilename;
    }
    private void setUploadFilename(String uploadFilename) {
        this.uploadFilename = uploadFilename;
    }

    public Long getId() {
        return id;
    }
    public Board getBoard() {
        return board;
    }
    public String getStoreFilename() {
        return storeFilename;
    }
    public String getUploadFilename() {
        return uploadFilename;
    }

    public static AttachedFile createAttachedFile(String originalFilename, String storeFilename){
        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setStoreFilename(storeFilename);
        attachedFile.setUploadFilename(originalFilename);

        return attachedFile;
    }
    //연관관계 편의 메소드
    public void addBoard(Board board){
        this.setWriting(board);
    }
}