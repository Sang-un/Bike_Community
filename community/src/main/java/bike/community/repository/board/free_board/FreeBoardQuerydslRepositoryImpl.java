package bike.community.repository.board.free_board;

import bike.community.model.network.response.post.board.free.FreeBoardPageResponse;
import bike.community.model.network.response.post.board.free.QFreeBoardPageResponse;
import bike.community.model.network.response.post.board.free.search_condition.FreeBoardSearchCond;
import bike.community.model.network.response.user.QUserWriterResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


import static bike.community.model.entity.board.QFree.free;
import static bike.community.model.entity.user.QUser.user;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class FreeBoardQuerydslRepositoryImpl implements FreeBoardQuerydslRepository{

    private final JPAQueryFactory queryFactory;

    public FreeBoardQuerydslRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<FreeBoardPageResponse> searchPaging(Pageable pageable) {
        QueryResults<FreeBoardPageResponse> results = queryFactory.select(
                new QFreeBoardPageResponse(
                        free.id,
                        free.title,
                        new QUserWriterResponse(free.user.nickname, free.user.email)
                )).from(free)
                .leftJoin(free.user, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(free.id.desc())//최신등록순으로
                .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<FreeBoardPageResponse> searchByCond(FreeBoardSearchCond cond, Pageable pageable) {
        QueryResults<FreeBoardPageResponse> results = queryFactory.select(
                        new QFreeBoardPageResponse(
                                free.id,
                                free.title,
                                new QUserWriterResponse(free.user.nickname, free.user.email)
                        )).from(free)
                .leftJoin(free.user, user)
                .where(
                        nicknameEq(cond.getNickname()),
                        titleLike(cond.getTitle()),
                        contentLike(cond.getContent())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }


    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? free.user.username.eq(username) : null;
    }

    private BooleanExpression nicknameEq(String nickname) {
        return hasText(nickname) ? free.user.nickname.eq(nickname) : null;
    }

    private BooleanExpression titleLike(String title) {
        return hasText(title) ? free.title.like(title) : null;
    }

    private BooleanExpression contentLike(String content) {
        return hasText(content) ? free.content.like(content) : null;
    }

//    private BooleanExpression ageGoe(Integer ageGoe) {
//        return ageGoe!=null ? member.age.goe(ageGoe) : null;
//    }
//
//    private BooleanExpression ageLoe(Integer ageLoe) {
//        return ageLoe!=null ? member.age.loe(ageLoe) : null;
//    }
}
