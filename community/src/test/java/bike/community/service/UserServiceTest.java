package bike.community.service;

import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.post.board.free.FreeBoardRequest;
import bike.community.model.network.response.post.board.free.FreeBoardResponse;
import bike.community.repository.user.UserRepository;
import bike.community.service.board.free_board.FreeBoardService;
import bike.community.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private FreeBoardService freeBoardService;

    @Test
    void hasEmailAndNicknameOf() {
        User user1 = new User("k@naver.com", "킹차니");
        User user2 = new User("p@naver.com", "바부바");

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> result = userRepository.hasEmailAndNicknameOf("k@naver.com", "바부바");
        Assertions.assertThat(result.size()).isEqualTo(2);
        for (User user : result) System.out.println("user = " + user);

        System.out.println("---------------------------------");

//        boolean result2 = userService.hasEmailAndNicknameOf("k@naver.com", "바부바");
//        Assertions.assertThat(result2).isEqualTo(true);
    }

    @Rollback(false)
    @Test
    void canSaveBoard() throws IOException {
//        User user = new User("k@naver.com", "킹차니");
//        FreeBoardRequest board = new FreeBoardRequest("테스트", "테스트입니다.");
//        Header<FreeBoardResponse> freeBoardResponseHeader = freeBoardService.create(board, user);
//        System.out.println(freeBoardResponseHeader);
    }
}