package bike.community.model.network.response.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoResponse {
    private String nickname;
    private String email;
    private String username;
    private String phone;
    private String address;
    private String detail_address;
    private String zipcode;
    private String birthday;
    private String sex;
    private String photoURL;
}
