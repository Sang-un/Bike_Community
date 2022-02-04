package bike.community.security;

import bike.community.model.network.response.post.user.AfterJoinUserResponse;
import bike.community.model.user.User;
import bike.community.security.redis.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static bike.community.security.jwt.JwtProperties.*;


@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final RedisService redisService;
    private final ObjectMapper om;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println("CustomLoginSuccessHandler.onAuthenticationSuccess");
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        String accessToken = redisService.setAccessJwtToken(user.getEmail(), user.getNickname(), user.getRole().toString());

        if(!redisService.isValidRefreshJwtToken(user.getEmail())) redisService.setRefreshJwtToken(user.getEmail(), user.getNickname(), user.getRole().toString());

        responseUserDate(response, accessToken, user);
    }

    public void responseUserDate(HttpServletResponse response, String accessToken,User user){
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + SPACE + accessToken);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        String loginUserJson = "";
        try {
            AfterJoinUserResponse afterJoinUserResponse = AfterJoinUserResponse.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .username(user.getUsername()).build();

            loginUserJson = om.writeValueAsString(afterJoinUserResponse);
            out = response.getWriter();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        out.print(loginUserJson);
        out.flush();
    }
}