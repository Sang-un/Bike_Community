package bike.community.security.jwt;

import io.jsonwebtoken.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static bike.community.security.jwt.JwtProperties.*;

@Slf4j
@Component
@NoArgsConstructor
public class TokenUtils {
    public static final String EMAIL = "email";
    public static final String NICKNAME = "nickname";
    public static final String ROLE = "role";

    private String secretKey = SECRET;

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

//    public boolean isValidToken(HttpServletRequest request) {
//        String justToken = parseJwt(request);
//        System.out.println("isValidToken(HttpServletRequest request).justToken = " + justToken);
//        try {
//            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(justToken).getBody();
//            return true;
//        } catch (ExpiredJwtException exception) {//?????? ???????????? ??????
//            log.error("Token Expired");
//            return false;
//        } catch (JwtException exception) {//?????? ??????
//            log.error("Token Tampered");
//            return false;
//        } catch (NullPointerException exception) {//?????? null
//            log.error("Token is null");
//            return false;
//        }
//    }

    public boolean isValidToken(String justToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(justToken).getBody();
            return true;
        } catch (ExpiredJwtException exception) {//?????? ???????????? ??????
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {//?????? ??????
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {//?????? null
            log.error("Token is null");
            return false;
        } catch (Exception exception){
            log.error("Undefined ERROR");
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

    //TODO ??? ????????? ????????? Bearer ??? ?????? ?????? ??????????
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTH_HEADER);
        if(headerAuth.split(" ").length==3){
            System.out.println("!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(headerAuth);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!");
            return headerAuth.split(" ")[2];
        }
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_TYPE)) return headerAuth.substring(7); // e?????? ???????????? jwt ??????
        return null;
    }

    public String parseJwt(String token) {
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_TYPE)) return token.substring(7); // e?????? ???????????? jwt ??????
        return null;
    }
}
