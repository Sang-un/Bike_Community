package bike.community.model.network.response.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserWriterResponse {
    private String nickname;
    private String email;

    @QueryProjection
    public UserWriterResponse(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
