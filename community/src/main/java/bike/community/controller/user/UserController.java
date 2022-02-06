package bike.community.controller.user;

import bike.community.model.network.Header;
import bike.community.model.network.request.user.JoinUserRequest;
import bike.community.model.network.response.user.AfterJoinUserResponse;
import bike.community.security.redis.RedisService;
import bike.community.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
<<<<<<< Updated upstream
import java.nio.charset.StandardCharsets;

import static bike.community.security.jwt.JwtProperties.AUTH_HEADER;
=======
>>>>>>> Stashed changes

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController{

    private final UserService userService;
    private final RedisService redisService;

    @GetMapping("/api/guest/hello")
    public String hello() {
        return "hello guest";
    }

    // TODO 한 줄 넣기
    @PostMapping("/api/join")
    public Header<AfterJoinUserResponse> join(@RequestBody @Valid JoinUserRequest user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return responseError();
        if(userService.hasEmailAndNicknameOf(user.getEmail(), user.getNickname())) return Header.ERROR("이미 존재하는 email 혹은 닉네임입니다."); // 이미 존재하는 email 이므로 재요청
        return userService.join(user);
    }

    // TODO 반환 HEADER
    @GetMapping("/api/logout")
    public String logout(HttpServletRequest request){
        String jwtToken = request.getHeader(AUTH_HEADER);
        byte[] bytes = jwtToken.getBytes(StandardCharsets.UTF_8);
        String s = StringUtils.newStringUtf8(bytes);
        redisService.logout(s);
        return "logout OK";
    }

    private Header<AfterJoinUserResponse> responseError(){
        return Header.ERROR("유효성 검사 탈락입니다~");
    }

    @GetMapping("/api/user/user-only")
    public String afterSuccessLoginUser() {
        return "helloUser your login is successful, you have authorization";
    }

    @GetMapping("/admin/admin-only")
    public String afterSuccessLoginAdmin() {
        return "helloAdmin your login is successful, you have authorization";
    }
}