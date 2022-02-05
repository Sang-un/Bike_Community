package bike.community.service;

import bike.community.model.network.Header;
import bike.community.model.network.request.user.JoinUserRequest;
import bike.community.model.network.response.post.user.AfterJoinUserResponse;
import bike.community.model.network.response.post.user.UserResponse;
import bike.community.model.user.User;
import bike.community.repository.user.UserQuerydslRepository;
import bike.community.repository.user.UserQuerydslRepositoryImpl;
import bike.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserQuerydslRepositoryImpl userDslRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Header<AfterJoinUserResponse> join(JoinUserRequest joinUser) {
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

    public boolean hasUserEmailOf(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean hasUserNicknameOf(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    public List<UserResponse> findByUsername(String username) {
        return userDslRepository.findByUsername(username);
    }

    public UserResponse findByNickname(String nickname) {
        return userDslRepository.findByNickname(nickname);
    }

//    public boolean hasEmailAndNicknameOf(String email, String nickname) {
//        List<UserResponse> reuslt = userDslRepository.hasEmailAndNicknameOf(email, nickname);
//    }
}