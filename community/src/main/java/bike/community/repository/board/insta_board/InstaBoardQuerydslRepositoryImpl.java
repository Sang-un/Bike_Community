package bike.community.repository.board.insta_board;

import bike.community.model.entity.board.insta.InstaBoard;
import bike.community.model.entity.board.insta.QInstaBoard;
import bike.community.model.network.response.post.board.insta.InstaBoardDetailResponse;
import bike.community.model.network.response.post.board.insta.InstaBoardPageResponse;
import bike.community.model.network.response.post.board.insta.QInstaBoardDetailResponse;
import bike.community.model.network.response.post.board.insta.QInstaBoardPageResponse;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static bike.community.model.entity.board.image.QImageURL.imageURL;
import static bike.community.model.entity.board.insta.QInstaBoard.instaBoard;
import static bike.community.model.entity.comment.QComment.comment1;

@Repository
public class InstaBoardQuerydslRepositoryImpl implements InstaBoardQuerydslRepository{

    private final JPAQueryFactory queryFactory;

    public InstaBoardQuerydslRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<InstaBoardPageResponse> searchPaging(Pageable pageable) {

        List<InstaBoardPageResponse> results = queryFactory.select(
                        new QInstaBoardPageResponse(
                                instaBoard.id,
                                instaBoard.content,
                                instaBoard.createdDate,
                                instaBoard.thumbnailImageUrl,
                                instaBoard.boardWriter
                        )).from(instaBoard)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(instaBoard.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(instaBoard.count()).from(instaBoard);

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<InstaBoardDetailResponse> findInstaBoardById(Long boardId) {
        InstaBoardDetailResponse instaBoardDetailResponse = queryFactory.select(
                        new QInstaBoardDetailResponse(
                                instaBoard.id,
                                instaBoard.content,
                                instaBoard.createdDate,
                                instaBoard.thumbnailImageUrl,
                                instaBoard.boardWriter,
                                instaBoard.images,
                                instaBoard.comments
                        )).from(instaBoard)
                .join(instaBoard.images, imageURL).fetchJoin()
                .join(instaBoard.comments, comment1).fetchJoin()
                .where(instaBoard.id.eq(boardId)).fetchOne();

        return Optional.ofNullable(instaBoardDetailResponse);
    }

    @Override
    public Optional<InstaBoard> findInstaBoardOGById(Long boardId) {
        InstaBoard instaBoard = queryFactory.selectFrom(QInstaBoard.instaBoard)
                .join(QInstaBoard.instaBoard.images, imageURL).fetchJoin()
                .join(QInstaBoard.instaBoard.comments, comment1).fetchJoin()
                .where(QInstaBoard.instaBoard.id.eq(boardId)).fetchOne();

        return Optional.ofNullable(instaBoard);
    }
}
