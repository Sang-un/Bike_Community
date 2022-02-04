package bike.community.security.redis;

import bike.community.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

import static bike.community.security.jwt.JwtProperties.*;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate redisTemplate;
    private final TokenUtils tokenUtils;

    public String setAccessJwtToken(String email, String nickname, String role){
        String accessToken = tokenUtils.createAccessJwtToken(email, nickname, role);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email+REDIS_AT, accessToken, Duration.ofMinutes(60));
        return accessToken;
    }

    public String setRefreshJwtToken(String email, String nickname, String role){
        String refreshToken = tokenUtils.createRefreshJwtToken(email, nickname, role);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email+REDIS_AT, refreshToken, Duration.ofDays(14));
        return refreshToken;
    }

    public boolean isValidAccessJwtToken(String email){
        String jwtToken = getJwtToken(email + REDIS_AT);
        return tokenUtils.isValidToken(jwtToken);
    }

    public boolean isValidRefreshJwtToken(String email){
        String jwtToken = getJwtToken(email + REDIS_RT);
        if(jwtToken==null) return false;
        return tokenUtils.isValidToken(jwtToken);
    }

    private String getJwtToken(String email){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(email);
    }

//    public String getValues(String email){
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        return values.get(email);
//    }
//
//    public void delValues(String email) {
//        redisTemplate.delete(email.substring(7));
//    }
//
//    private void deleteAccessToken(String email) {
//        redisTemplate.delete(email + REDIS_AT);
//    }
//
//    private void deleteRefreshToken(String email) {
//        redisTemplate.delete(email + REDIS_RT);
//    }

//    public void logout(HttpServletRequest request) {
//        String token = request.getHeader(AUTH_HEADER);
//        String email = tokenUtils.get(token, "email");
//        deleteAccessToken(email);
//    }
}
