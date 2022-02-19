package bike.community.model.network.response.post.board.free;

import bike.community.model.network.response.user.UserWriterResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FreeBoardResponse {
    private Long id;
    private String title;
    private String content;
    private UserWriterResponse user;
//    private List<String> filenames = new ArrayList<>();

    @QueryProjection
    public FreeBoardResponse(Long id,
                             String title,
                             String content,
//                             List<String> filenames,
                             UserWriterResponse user) {
        this.id = id;
        this.title = title;
        this.content = content;
//        for (String filename : filenames) this.filenames.add("http://localhost:8080/api/"+filename);
        this.user = user;
    }
}
