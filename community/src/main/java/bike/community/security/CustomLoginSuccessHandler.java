package bike.community.security;

import bike.community.model.user.User;
import bike.community.security.jwt.TokenUtils;
import bike.community.security.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static bike.community.security.jwt.JwtProperties.*;


@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println("CustomLoginSuccessHandler.onAuthenticationSuccess");
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        String accessToken = redisService.setAccessJwtToken(user.getEmail(), user.getNickname(), user.getRole().toString());

        if(!redisService.isValidRefreshJwtToken(user.getEmail())) redisService.setRefreshJwtToken(user.getEmail(), user.getNickname(), user.getRole().toString());

        response.addHeader(AUTH_HEADER, TOKEN_TYPE + SPACE + accessToken);
    }
}
