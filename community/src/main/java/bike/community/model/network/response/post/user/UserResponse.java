package bike.community.model.network.response.post.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class UserResponse {

    private String email; //이메일
    private String username; //본명 (진짜 본인 이름)
    private String sex; //성별
    private String phone; //전화번호 ('-'없이 ex:01075528034)
    private String birthday; //생년월일 7자리 (ex: 970223)
    private String nickname; //사용할 닉네임

    @QueryProjection
    public UserResponse(String email, String username, String sex, String phone, String birthday, String nickname) {
        this.email = email;
        this.username = username;
        this.sex = sex;
        this.phone = phone;
        this.birthday = birthday;
        this.nickname = nickname;
    }
}
