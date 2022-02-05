package bike.community.repository.user;

import bike.community.model.network.response.post.user.UserResponse;
import bike.community.model.user.User;

import java.util.List;


public interface UserQuerydslRepository {
    List<UserResponse> findByUsername(String username);
    List<User> hasEmailAndNicknameOf(String email, String nickname);
    UserResponse findByNickname(String nickname);
}
