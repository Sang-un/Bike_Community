package bike.community.controller.user;

import bike.community.model.RespDto;
import bike.community.model.user.AfterJoinUserDto;
import bike.community.model.user.JoinUser;
import bike.community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    //권한이 없는 사용자라면 해당 api로 redirect 된다.
    @GetMapping("/loginForm")
    public String loginForm() {
        return "로그인 폼입니다.";
    }

    @PostMapping("/join")
    public RespDto<AfterJoinUserDto> join(@RequestBody JoinUser user) {
        return userService.join(user);
    }

    @GetMapping("/user/helloUser")
    public String afterSuccessLogin() {
        return "helloUser your login is successful, you have authorization";
    }

    @GetMapping("/admin/helloAdmin")
    public String afterSuccessLogin2() {
        return "helloAdmin your login is successful, you have authorization";
    }

    @GetMapping("/error/unauthorized")
    public String noToken() {
        return "No Token~";
    }
}
