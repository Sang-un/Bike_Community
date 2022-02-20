package bike.community.model.security.jwt;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static bike.community.model.security.jwt.JwtProperties.*;

@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtils {
    public static final String EMAIL = "email";
    public static final String NICKNAME = "nickname";
    public static final String ROLE = "role";

    private String secretKey = SECRET;

//    @PostConstruct
//    private void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }

    public String createAccessJwtToken(String email, String nickname, String role){

        Claims claims = Jwts.claims()
                .setSubject(ACCESS_NAME)
                .setIssuedAt(new Date());

        claims.put(EMAIL, email);
        claims.put(NICKNAME, nickname);
        claims.put(ROLE, role);

        Date ext = new Date();
        ext.setTime(ext.getTime() + ACCESS_EXPIRED_TIME);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256,  secretKey)
                .compact();
    }

    public String createRefreshJwtToken(String email, String nickname, String role){

        Claims claims = Jwts.claims()
                .setSubject(REFRESH_NAME)
                .setIssuedAt(new Date());

        claims.put(EMAIL, email);
        claims.put(NICKNAME, nickname);
        claims.put(ROLE, role);

        Date ext = new Date();
        ext.setTime(ext.getTime() + REFRESH_EXPIRED_TIME);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256,  secretKey)
                .compact();
    }

    public boolean isValidToken(HttpServletRequest request) {
        String justToken = parseJwt(request);
        System.out.println("isValidToken(HttpServletRequest request).justToken = " + justToken);
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(justToken).getBody();
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

    public boolean isValidToken(String justToken) {
        System.out.println("isValidToken(String justToken).justToken = " + justToken);
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(justToken).getBody();
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
                .parseClaimsJws(jwt).getBody();
    }

    public String getEmailFromJwt(String jwt){
        return (String) getJwtBody(jwt).get(EMAIL);
    }

    public String getNicknameFromJwt(String jwt){
        return (String) getJwtBody(jwt).get(NICKNAME);
    }

    public String getEmailFromJwt(HttpServletRequest request){
        String jwt = parseJwt(request);
        return (String) getJwtBody(jwt).get(EMAIL);
    }

    public String getNicknameFromJwt(HttpServletRequest request){
        String jwt = parseJwt(request);
        return (String) getJwtBody(jwt).get(NICKNAME);
    }

    public String getRoleFromJwt(String jwt){
        return (String) getJwtBody(jwt).get(ROLE);
    }

    //TODO 왜 글쓰기 요청만 Bearer 가 두개 붙어 오는가?
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTH_HEADER);
        if(headerAuth.split(" ").length==3) return headerAuth.split(" ")[2];
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_TYPE)) return headerAuth.substring(7); // e부터 시작하는 jwt 토큰
        return null;
    }

    public String parseJwt(String token) {
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_TYPE)) return token.substring(7); // e부터 시작하는 jwt 토큰
        return null;
    }
}
