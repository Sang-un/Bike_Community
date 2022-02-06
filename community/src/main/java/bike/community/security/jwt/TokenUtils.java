package bike.community.security.jwt;

<<<<<<< Updated upstream
=======
import bike.community.model.entity.user.User;
>>>>>>> Stashed changes
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

import static bike.community.security.jwt.JwtProperties.*;

@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtils {
    public static final String EMAIL = "email";
    public static final String NICKNAME = "nickname";
    public static final String ROLE = "role";

    private String secretKey = SECRET;

    @PostConstruct
    private void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessJwtToken(String email, String nickname, String role){
        Claims claims = Jwts.claims()
                .setSubject(ACCESS_NAME)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + ACCESS_EXPIRED_TIME));

        claims.put(EMAIL, email);
        claims.put(NICKNAME, nickname);
        claims.put(ROLE, role);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,  secretKey)
                .compact();
    }

    public String createRefreshJwtToken(String email, String nickname, String role){
        Claims claims = Jwts.claims()
                .setSubject(REFRESH_NAME)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + REFRESH_EXPIRED_TIME));

        claims.put(EMAIL, email);
        claims.put(NICKNAME, nickname);
        claims.put(ROLE, role);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,  secretKey)
                .compact();
    }

    public boolean isValidToken(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
            return true;
        } catch (ExpiredJwtException exception) {//토큰 유효기간 지남
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {//토큰 변조
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {//토큰 null
            log.error("Token is null");
            return false;
        }
    }

    private Claims getJwtBody(String jwt) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt.split(SPACE)[1]).getBody();
    }

    public String getEmailFromJwt(String jwt){
        return (String) getJwtBody(jwt).get(EMAIL);
    }

    public String getNicknameFromJwt(String jwt){
        return (String) getJwtBody(jwt).get(NICKNAME);
    }

    public String getRoleFromJwt(String jwt){
        return (String) getJwtBody(jwt).get(ROLE);
    }
}
