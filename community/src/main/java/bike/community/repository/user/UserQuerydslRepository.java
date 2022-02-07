package bike.community.repository.user;

import bike.community.model.entity.user.User;
import bike.community.model.network.response.user.UserResponse;
import java.util.List;


public interface UserQuerydslRepository {
    List<UserResponse> findByUsername(String username);
    List<User> hasEmailAndNicknameOf(String email, String nickname);
    UserResponse findByNickname(String nickname);
}
