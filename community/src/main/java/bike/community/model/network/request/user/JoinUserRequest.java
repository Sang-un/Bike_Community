package bike.community.model.network.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class JoinUserRequest {
    private String email; //이메일
    private String password; //패스워드
    private String username; //본명 (진짜 본인 이름)
    private String sex; //성별
    private String phone; //전화번호 ('-'없이 ex:01075528034)
    private String birthday; //생년월일 7자리 (ex: 970223)
    private String nickname; //사용할 닉네임
}
