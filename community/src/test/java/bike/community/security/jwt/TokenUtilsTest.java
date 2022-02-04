package bike.community.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import static bike.community.security.jwt.JwtProperties.SPACE;
import static bike.community.security.jwt.JwtProperties.TOKEN_TYPE;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class TokenUtilsTest {

    TokenUtils tokenUtils;

    @Test
    void createAccessJwtToken() {

//        this.tokenUtils = new TokenUtils();

        String email = "kokiyo97@naver.com";
        String nickname = "킹차니";
        String role = "ROLE_USER";

        String accessJwtToken = tokenUtils.createAccessJwtToken(email, nickname, role);
        System.out.println("accessJwtToken = " + accessJwtToken);
        String accToken = TOKEN_TYPE + accessJwtToken;
        String[] s = accToken.split(" ");
        System.out.println("s = " + s[1]);
//        System.out.println("substring = " + substring);
//
//        String emailFromJwt = tokenUtils.getEmailFromJwt(accessJwtToken);
//        System.out.println("emailFromJwt = " + emailFromJwt);
    }
}