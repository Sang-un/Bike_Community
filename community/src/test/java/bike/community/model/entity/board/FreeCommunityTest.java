package bike.community.model.entity.board;


import bike.community.model.entity.user.User;
import bike.community.repository.board.BikeBoardRepository;
import bike.community.repository.board.BoardRepository;
import bike.community.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@Rollback(false)
@Transactional(readOnly = true)
@SpringBootTest
class FreeCommunityTest {

    @Autowired
    private BikeBoardRepository bikeBoardRepository;

    @Autowired
    private BoardRepository BoardRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void createBike() {

        User user = new User("kk", "하하");
        userRepository.save(user);

        Bike bike = Bike.create("bmw", "bmww질렀습니다.", "bmww질렀습니다라구요 하하", user);
        bikeBoardRepository.save(bike);
    }

    @Test
    @Transactional
    void createBoard() {

        User user = new User("kk", "하하");
        userRepository.save(user);

        Board bike = Board.create( "bmww질렀습니다.", "bmww질렀습니다라구요 하하", user);
        BoardRepository.save(bike);
    }
}