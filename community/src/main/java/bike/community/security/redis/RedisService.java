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

    public void setAccessToken(String token, String email){
        System.out.println("make access token of "+email);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email, token, Duration.ofMinutes(3));
    }

    public void setRefreshToken(String token, String email){
        System.out.println("make refresh token of "+email);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email, token, Duration.ofDays(3));
    }

    public String getValues(String email){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        System.out.println("getValues : "+ values.get(email));
        return values.get(email);
    }

    public void delValues(String email) {
        redisTemplate.delete(email.substring(7));
    }

    private void deleteAccessToken(String email) {
        redisTemplate.delete(email + REDIS_AT);
    }

    private void deleteRefreshToken(String email) {
        redisTemplate.delete(email + REDIS_RT);
    }

    public void logout(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER);
        String email = tokenUtils.get(token, "email");
        deleteAccessToken(email);
    }
}
