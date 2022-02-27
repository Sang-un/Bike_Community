package bike.community.model.network.response.post.board.insta;

import bike.community.model.entity.board.image.ImageURL;
import bike.community.model.entity.comment.Comment;
import bike.community.model.network.response.post.board.comment.CommentResp;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class InstaBoardDetailResponse {
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private String thumbnailImageUrl;
    private List<String> imageURLs = new ArrayList<>();
    private List<CommentResp> comments = new ArrayList<>();
    private String boardWriter;

    public static InstaBoardDetailResponse create(Long id, String content, LocalDateTime createdDate, String thumbnailImageUrl, String boardWriter, List<ImageURL> images, List<Comment> comments) {
        InstaBoardDetailResponse instaBoardResp = new InstaBoardDetailResponse();
        instaBoardResp.id = id;
        instaBoardResp.content = content;
        instaBoardResp.createdDate = createdDate;
        instaBoardResp.boardWriter = boardWriter;

        instaBoardResp.thumbnailImageUrl = thumbnailImageUrl;
        instaBoardResp.imageURLs.add(instaBoardResp.thumbnailImageUrl);
        for (ImageURL image : images) instaBoardResp.imageURLs.add(image.getUrl());
        if(comments != null && !comments.isEmpty()) for (Comment comment : comments) instaBoardResp.comments.add(CommentResp.create(comment));
        return instaBoardResp;
    }

    @QueryProjection
    public InstaBoardDetailResponse(Long id, String content, LocalDateTime createdDate, String thumbnailImageUrl, String boardWriter, List<ImageURL> images, List<Comment> comments) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.thumbnailImageUrl = thumbnailImageUrl;
        for (ImageURL image : images) this.imageURLs.add(image.getUrl());
        if(comments != null && !comments.isEmpty()) for (Comment comment : comments) this.comments.add(CommentResp.create(comment));
        this.boardWriter = boardWriter;
    }
}
