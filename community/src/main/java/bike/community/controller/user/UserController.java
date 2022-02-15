package bike.community.controller.user;

import bike.community.model.network.Header;
import bike.community.model.network.request.user.JoinUserRequest;
import bike.community.model.network.response.user.AfterJoinUserResponse;
import bike.community.model.network.response.user.UserInfoResponse;
import bike.community.security.UserDetailsImpl;
import bike.community.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController{

    private final UserService userService;

    @PostMapping("/api/join")
    public Header<AfterJoinUserResponse> join(@RequestBody @Valid JoinUserRequest user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return responseError();
        return userService.join(user);
    }

    @GetMapping("/api/userinfo")
    public Header<UserInfoResponse> userInfo(Authentication authentication) {
        System.out.println("/api/userinfo:~~~#######");
        return userService.userInfo(authentication);
    }

    @GetMapping("/api/logout")
    public Header<String> logout(HttpServletRequest request){
        return userService.logout(request);
    }

    private Header<AfterJoinUserResponse> responseError(){
        return Header.ERROR("유효성 검사 탈락입니다~");
    }

    // -- for Test --
    @GetMapping("/api/guest/hello")
    public String hello() {
        return "hello guest";
    }

    @GetMapping("/api/user/user-only")
    public String afterSuccessLoginUser() {
        return "helloUser your login is successful, you have authorization";
    }

    @GetMapping("/admin/admin-only")
    public String afterSuccessLoginAdmin() {
        return "helloAdmin your login is successful, you have authorization";
    }
    // -----------------
}