package bike.community.model.network.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class LoginUserRequest {
    private String email;
    private String password;
}
