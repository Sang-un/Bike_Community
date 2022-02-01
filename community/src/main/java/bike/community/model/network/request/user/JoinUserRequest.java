package bike.community.model.network.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@ToString
@Builder
@Setter @Getter
public class JoinUserRequest {

    @Email
    @NotBlank @NotNull
    private String email; //이메일

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}")//숫자 적어도하나, 영문 적어도 하나, 특수문자 적어도 하나, 공백 제거, 8~20자리
    @NotBlank @NotNull
    private String password; //패스워드

    @NotBlank @NotNull
    private String username; //본명 (진짜 본인 이름)

    @NotBlank @NotNull
    private String sex; //성별 -> 남/여

    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    @NotBlank @NotNull
    private String phone; //전화번호 ('-'없이 ex:01075528034)

    @Pattern(regexp = "[0-9]{6}", message = "7자리의 숫자만 입력가능합니다")
    @NotBlank @NotNull
    private String birthday; //생년월일 7자리 (ex: 970223)

    @Size(min=1,max=7, message="3자이상 7자미만으로 작성해야 합니다.")
    @NotBlank @NotNull
    private String nickname; //사용할 닉네임
}