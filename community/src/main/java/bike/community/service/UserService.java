package bike.community.service;

import bike.community.model.RespDto;
import bike.community.model.user.AfterJoinUserDto;
import bike.community.model.user.JoinUser;
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
    public RespDto<AfterJoinUserDto> join(JoinUser joinUser) {
        // TODO 유효성 검사
        String ecp = passwordEncoder.encode(joinUser.getPassword());
        User user = User.makeUser(joinUser.getEmail(), ecp, joinUser.getNickname(), joinUser.getUsername(),joinUser.getSex(), joinUser.getPhone(), joinUser.getBirthday());
        User saveUser = userRepository.save(user);
        AfterJoinUserDto afterJoinUserDto = new AfterJoinUserDto(saveUser.getEmail(), saveUser.getUsername(), saveUser.getNickname());
        return new RespDto<>(200, "join is successful", afterJoinUserDto);
    }
}