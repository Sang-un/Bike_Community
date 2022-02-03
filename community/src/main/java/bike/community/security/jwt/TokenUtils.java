package bike.community.security.jwt;

import bike.community.model.user.User;
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static bike.community.security.jwt.JwtProperties.*;

@Slf4j
@Component/**/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtils {

    public String createAccessJwtToken(String email, String nickname, String role){
        Claims claims = Jwts.claims()
                .setSubject("access_token")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + ACCESS_EXPIRED_TIME));

        claims.put("email", email);
        claims.put("nickname", nickname);
        claims.put("role", role);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,  SECRET.getBytes())
                .compact();
    }

    public String createRefreshJwtToken(String email, String nickname, String role){
        Claims claims = Jwts.claims()
                .setSubject("refresh_token")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + REFRESH_EXPIRED_TIME));

        claims.put("email", email);
        claims.put("nickname", nickname);
        claims.put("role", role);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,  SECRET.getBytes())
                .compact();
    }

    public boolean isValidToken(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwt).getBody();
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {//토큰 변조
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    private Claims getJwtBody(String jwt) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(jwt).getBody();
    }

    public String getEmailFromJwt(String jwt){
        return (String) getJwtBody(jwt).get("email");
    }

    public String getNicknameFromJwt(String jwt){
        return (String) getJwtBody(jwt).get("nickname");
    }

    public String getRoleFromJwt(String jwt){
        return (String) getJwtBody(jwt).get("role");
    }

//    public static String getTokenFromHeader(String header) {
//        return header.split(" ")[1];
//    }
//
//    public String get(String token, String key){
//        if(key.equals("email"))
//            return getUserEmailFromToken(token);
//        else if(key.equals("role"))
//            return getRoleFromToken(token);
//        else return null;
//    }
//
//    private static Date createExpireDateForOneYear() {
//        // 토큰 만료시간은 30일으로 설정
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, 30);
//        return c.getTime();
//    }
//
//    private static Map<String, Object> createHeader() {
//        Map<String, Object> header = new HashMap<>();
//        header.put("typ", "JWT");
//        header.put("alg", "HS256");
//        header.put("regDate", System.currentTimeMillis());
//        return header;
//    }
//
//    private static Map<String, Object> createClaims(User user) {
//        // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
//        Map<String, Object> claims = new HashMap<>();
//
//        claims.put("email", user.getEmail());
//        claims.put("role", user.getRole());
//
//        return claims;
//    }
//
//    private static Key createSigningKey() {
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
//        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
//    }
//
//    private static Claims getClaimsFormToken(String token) {
//        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
//                .parseClaimsJws(token).getBody();
//    }
//
//    private static String getUserEmailFromToken(String token) {
//        Claims claims = getClaimsFormToken(token);
//        System.out.println(claims);
//        String sub = (String) claims.get("email");
//        return sub.split("/")[0];
//    }
//
//    private static String getRoleFromToken(String token) {
//        Claims claims = getClaimsFormToken(token);
//        return claims.get("role").toString();
//    }
}
