package bike.community.model.network.request.post.board.free;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Data
public class FreeBoardRequest {

    private String title;

    private String content;

//    private String nickname;// TODO 이거 없애기. 토큰을 까서 확인하자

//    private List<MultipartFile> imageFiles;
}
