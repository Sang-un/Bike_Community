package bike.community.repository.user;

import bike.community.model.network.response.post.user.QUserResponse;
import bike.community.model.network.response.post.user.UserResponse;
import bike.community.model.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static bike.community.model.user.QUser.user;

@Repository
public class UserQuerydslRepositoryImpl implements UserQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UserQuerydslRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserResponse> findByUsername(String username) {
        return queryFactory.select(new QUserResponse(
                        user.email,
                        user.username,
                        user.sex,
                        user.phone,
                        user.birthday,
                        user.nickname
                ))
                .from(user)
                .where(user.username.eq(username))
                .fetch();
    }

    @Override
    public UserResponse findByNickname(String nickname) {
        return queryFactory.select(new QUserResponse(
                        user.email,
                        user.username,
                        user.sex,
                        user.phone,
                        user.birthday,
                        user.nickname
                ))
                .from(user)
                .where(user.nickname.eq(nickname))
                .fetchOne();
    }

    @Override
    public List<User> hasEmailAndNicknameOf(String email, String nickname) {
        return queryFactory.selectFrom(user)
                .where(user.email.eq(email).or(user.nickname.eq(nickname)))
                .fetch();
    }
}
