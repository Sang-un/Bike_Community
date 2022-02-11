package bike.community.repository.board.free_board;

import bike.community.model.entity.board.QFree;
import bike.community.model.network.response.post.board.free.FreeBoardPageResponse;
import bike.community.model.network.response.post.board.free.QFreeBoardPageResponse;
import bike.community.model.network.response.user.QUserWriterResponse;
import bike.community.model.network.response.user.UserWriterResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static bike.community.model.entity.board.QFree.free;

@Repository
public class FreeBoardQuerydslRepositoryImpl implements FreeBoardQuerydslRepository{

    private final JPAQueryFactory queryFactory;

    public FreeBoardQuerydslRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<FreeBoardPageResponse> searchPaging(Pageable pageable) {
        QueryResults<FreeBoardPageResponse> results = queryFactory.select(new QFreeBoardPageResponse(
                        free.id,
                        free.title,
                        new QUserWriterResponse(free.user.nickname, free.user.email)
                )).from(free)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
