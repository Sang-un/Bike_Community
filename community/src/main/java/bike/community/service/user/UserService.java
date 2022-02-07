package bike.community.service.user;

import bike.community.model.network.Header;
import bike.community.model.network.request.user.JoinUserRequest;
import bike.community.model.network.response.user.AfterJoinUserResponse;
import bike.community.model.network.response.user.UserResponse;
import bike.community.model.entity.user.User;
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
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Header<AfterJoinUserResponse> join(JoinUserRequest joinUser) {
        // -- 수정된 부분 --
        List<User> sameUsers = hasEmailAndNicknameOf(joinUser.getEmail(), joinUser.getNickname());
        if(!sameUsers.isEmpty()) return responseAlreadyExistIdError(sameUsers, joinUser);
        // ---------------

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

//    public boolean hasEmailAndNicknameOf(String email, String nickname) {
//        List<User> result = userRepository.hasEmailAndNicknameOf(email, nickname);
//        return !result.isEmpty();
//    }

}