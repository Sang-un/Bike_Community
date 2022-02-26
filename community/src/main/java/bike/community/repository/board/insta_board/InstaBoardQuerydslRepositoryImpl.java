package bike.community.repository.board.insta_board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class InstaBoardQuerydslRepositoryImpl implements InstaBoardQuerydslRepository{

    private final JPAQueryFactory queryFactory;

    public InstaBoardQuerydslRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
}
