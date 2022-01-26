package bike.community.service;

import bike.community.model.RespDto;
import bike.community.model.network.Header;
import bike.community.model.network.request.user.JoinUserRequest;
import bike.community.model.network.response.post.user.AfterJoinUserResponse;
import bike.community.model.user.User;
import bike.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Header<AfterJoinUserResponse> join(JoinUserRequest joinUser) {
        // TODO 유효성 검사
        User user = User.makeUser(
                joinUser.getEmail(),
                passwordEncoder.encode(joinUser.getPassword()),
                joinUser.getUsername(),
                joinUser.getSex(),
                joinUser.getPhone(),
                joinUser.getBirthday(),
                joinUser.getNickname());

        User saveUser = userRepository.save(user);
        AfterJoinUserResponse afterJoinUser = AfterJoinUserResponse
                .builder()
                .email(saveUser.getEmail())
                .username(saveUser.getUsername())
                .nickname(saveUser.getNickname()).build();
        return Header.OK(afterJoinUser);
    }

    public boolean findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}