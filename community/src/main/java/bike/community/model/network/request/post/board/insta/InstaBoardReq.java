package bike.community.model.network.request.post.board.insta;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class InstaBoardReq {
    private String content;
    private List<MultipartFile> images;
}
