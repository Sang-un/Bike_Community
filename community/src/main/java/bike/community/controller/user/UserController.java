package bike.community.controller.user;

import bike.community.model.network.Header;
import bike.community.model.network.request.user.JoinUserRequest;
import bike.community.model.network.response.post.user.AfterJoinUserResponse;
import bike.community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController{

    private final UserService userService;

    @GetMapping("/guest/hello")
    public String hello() {
        return "hello guest";
    }

    @PostMapping("/join")
    public Header<AfterJoinUserResponse> join(@RequestBody @Valid JoinUserRequest user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return responseError();
        if(!userService.hasUserEmailOf(user.getEmail())) return userService.join(user);
        return Header.ERROR("이미 존재하는 email입니다."); // 이미 존재하는 email 이므로 재요청
    }

    private Header<AfterJoinUserResponse> responseError() {
        return Header.ERROR("유효성 검사 탈락입니다~");
    }

    @GetMapping("/user/user-only")
    public String afterSuccessLoginUser() {
        return "helloUser your login is successful, you have authorization";
    }

    @GetMapping("/admin/admin-only")
    public String afterSuccessLoginAdmin() {
        return "helloAdmin your login is successful, you have authorization";
    }

    @GetMapping("/error/unauthorized")
    public String noToken() {
        return "No Token~";
    }
}