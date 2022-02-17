package bike.community.security.jwt;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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
        System.out.println("justToken = " + justToken);
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
        System.out.println("justToken = " + justToken);
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

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTH_HEADER);
        String[] headerAuths = headerAuth.split(" ");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_TYPE)){
//            System.out.println("headerAuth.substring(7):"+headerAuth.substring(7));
            return headerAuths[1];
        }
        return null;
    }
}
