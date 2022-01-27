package bike.community.model.user;


import bike.community.model.DateBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@NoArgsConstructor(access= AccessLevel.PROTECTED)/*프록시 객체를 위해 protect로 기본 생성자*/
@Getter
@Entity
public class User extends DateBaseEntity implements Serializable {
    @Id @GeneratedValue
    private Long id;

    private String email; //이메일
    private String password; //패스워드

    private String username; //본명 (진짜 본인 이름)
    private String sex; //성별
    private String phone; //전화번호 ('-'없이 ex:01075528034)
    private String birthday; //생년월일 7자리 (ex: 970223)
    private String nickname; //사용할 닉네임

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String provider;
    private String providerId;

//    private String email; //이메일
//    private String password; //패스워드
//    private String username; //본명 (진짜 본인 이름)
//    private String sex; //성별
//    private String phone; //전화번호 ('-'없이 ex:01075528034)
//    private String birthday; //생년월일 7자리 (ex: 970223)
//    private String nickname; //사용할 닉네임

    public static User makeUser(String email, String password, String username, String sex, String phone, String birthday, String nickname) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = username;
        user.sex = sex;
        user.phone = phone;
        user.birthday = birthday;
        user.nickname = nickname;
        user.role = UserRole.ROLE_USER;
        return user;
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
