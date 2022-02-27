package bike.community.model.network.request.post.board.insta;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class InstaBoardRequest {
    private String content;
    private MultipartFile thumbnailImageUrl;
    private List<MultipartFile> images;
}
