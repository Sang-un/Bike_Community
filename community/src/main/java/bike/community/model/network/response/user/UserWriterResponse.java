package bike.community.model.network.response.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserWriterResponse {
    private String nickname;
    private String email;
}
