package bike.community.model.network.response.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class UserInfoResponse {
    private String email;
    private String nickname;
    private String address;
    private String detail_address;
    private String zipcode;
    private String phone;
    private String username;
    private String sex;
    private String birthday;
}
