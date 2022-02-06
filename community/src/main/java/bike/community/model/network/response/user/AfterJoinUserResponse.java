package bike.community.model.network.response.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AfterJoinUserResponse {
    private String email;
    private String username;
    private String nickname;
}
