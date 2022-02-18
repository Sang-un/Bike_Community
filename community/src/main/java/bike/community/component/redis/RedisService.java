package bike.community.component.redis;

import bike.community.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static bike.community.security.jwt.JwtProperties.*;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate redisTemplate;
    private final TokenUtils tokenUtils;

    public String setAccessJwtToken(String email, String nickname, String role){
        String accessToken = tokenUtils.createAccessJwtToken(email, nickname, role);
        System.out.println("RedisService.setAccessJwtToken.accessToken = "+accessToken);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email+REDIS_AT, accessToken, Duration.ofMinutes(20));
        return accessToken;
    }

    public String setRefreshJwtToken(String email, String nickname, String role){
        String refreshToken = tokenUtils.createRefreshJwtToken(email, nickname, role);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email+REDIS_RT, refreshToken, Duration.ofDays(14));
        return refreshToken;
    }

    public boolean isValidAccessJwtToken(String email){
        String jwtToken = getJwtToken(email + REDIS_AT);
        if(jwtToken==null) return false;
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

    public void logout(String jwtToken) {
        String email = tokenUtils.getEmailFromJwt(jwtToken);
        System.out.println(email);
        deleteAccessToken(email);
    }

    private void deleteAccessToken(String email) {
        redisTemplate.delete(email + REDIS_AT);
    }

    private void deleteRefreshToken(String email) {
        redisTemplate.delete(email + REDIS_RT);
    }

}
