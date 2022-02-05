package bike.community.repository.user;

import bike.community.model.network.response.post.user.QUserResponse;
import bike.community.model.network.response.post.user.UserResponse;

import java.util.List;

import static bike.community.model.user.QUser.user;

public interface UserQuerydslRepository {
    List<UserResponse> findByUsername(String username);

    List<UserResponse> hasEmailAndNicknameOf(String email, String nickname);
}
