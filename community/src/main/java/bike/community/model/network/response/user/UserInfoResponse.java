package bike.community.model.network.response.user;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfoResponse {
    private String email;
    private String nickname;

}
