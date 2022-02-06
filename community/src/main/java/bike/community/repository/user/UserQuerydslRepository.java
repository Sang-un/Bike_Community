package bike.community.repository.user;

<<<<<<< Updated upstream
import bike.community.model.network.response.post.user.UserResponse;
import bike.community.model.user.User;
=======
import bike.community.model.network.response.user.QUserResponse;
import bike.community.model.network.response.user.UserResponse;
import bike.community.model.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
>>>>>>> Stashed changes

import java.util.List;

<<<<<<< Updated upstream
=======
import static bike.community.model.entity.user.QUser.user;
>>>>>>> Stashed changes

public interface UserQuerydslRepository {
    List<UserResponse> findByUsername(String username);
    List<User> hasEmailAndNicknameOf(String email, String nickname);
    UserResponse findByNickname(String nickname);
}
