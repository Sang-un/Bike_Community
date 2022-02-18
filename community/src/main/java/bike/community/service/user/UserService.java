package bike.community.service.user;

import bike.community.component.redis.RedisService;
import bike.community.model.common.Address;
import bike.community.model.network.Header;
import bike.community.model.network.request.user.JoinUserRequest;
import bike.community.model.network.response.user.AfterJoinUserResponse;
import bike.community.model.network.response.user.UserInfoResponse;
import bike.community.model.network.response.user.UserResponse;
import bike.community.model.entity.user.User;
import bike.community.repository.user.UserRepository;
import bike.community.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static bike.community.security.jwt.JwtProperties.AUTH_HEADER;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisService redisService;

    @Transactional
    public Header<AfterJoinUserResponse> join(JoinUserRequest joinUser) {

        List<User> sameUsers = hasEmailAndNicknameOf(joinUser.getEmail(), joinUser.getNickname());
        if(!sameUsers.isEmpty()) return responseAlreadyExistIdError(sameUsers, joinUser);

        Address address = joinUser.getAddress();
        System.out.println("address = " + address);

        User user = User.create(
                joinUser.getEmail(),
                passwordEncoder.encode(joinUser.getPassword()),
                joinUser.getUsername(),
                joinUser.getSex(),
                joinUser.getPhone(),
                joinUser.getBirthday(),
                joinUser.getNickname(),
                joinUser.getAddress());

        User saveUser = userRepository.save(user);
        AfterJoinUserResponse afterJoinUser = AfterJoinUserResponse
                .builder()
                .email(saveUser.getEmail())
                .username(saveUser.getUsername())
                .nickname(saveUser.getNickname()).build();

        return Header.OK(afterJoinUser);
    }

    private Header<AfterJoinUserResponse> responseAlreadyExistIdError(List<User> sameUsers, JoinUserRequest joinUser) {
        for (User sameUser : sameUsers) {
            if(sameUser.getEmail().equals(joinUser.getEmail())) return Header.ERROR("이미 존재하는 email입니다.");
            if(sameUser.getNickname().equals(joinUser.getNickname())) return Header.ERROR("이미 존재하는 닉네임입니다.");
        }
        return Header.ERROR();
    }

    public List<User> hasEmailAndNicknameOf(String email, String nickname) {
        return userRepository.hasEmailAndNicknameOf(email, nickname);
    }

    public Header<String> logout(HttpServletRequest request) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        byte[] bytes = jwtToken.getBytes(StandardCharsets.UTF_8);
        String s = StringUtils.newStringUtf8(bytes);
        redisService.logout(s);
        return Header.OK("로그아웃되었습니다.");
    }

    public Header<UserInfoResponse> userInfo(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String email = principal.getUsername();
        String nickname = principal.getNickname();
        System.out.println(nickname);
        System.out.println(email);
        UserInfoResponse userInfoResponse =
                UserInfoResponse.builder()
                        .email(principal.getUsername())
                        .nickname(principal.getNickname())
                        .username(principal.getRealUsername())
                        .address(principal.getAddress())
                        .detail_address(principal.getDetailAddress())
                        .zipcode(principal.getZipcode())
                        .birthday(principal.getBirthday())
                        .phone(principal.getPhone())
                        .sex(principal.getSex()).build();
        return Header.OK(userInfoResponse);
    }

    public boolean hasUserEmailOf(String email) {
        return userRepository.findOptionalByEmail(email).isPresent();
    }

    public boolean hasUserNicknameOf(String nickname) {
        return userRepository.findOptionalByNickname(nickname).isPresent();
    }

    public List<UserResponse> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserResponse findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

//    public boolean hasEmailAndNicknameOf(String email, String nickname) {
//        List<User> result = userRepository.hasEmailAndNicknameOf(email, nickname);
//        return !result.isEmpty();
//    }



}