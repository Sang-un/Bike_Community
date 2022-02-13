package bike.community.controller.user;

import bike.community.model.network.response.user.AfterJoinUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void join() {
//        JoinUserRequest joinUserRequest = JoinUserRequest.builder()
//                .email("kokiyo9@naver.com")
//                .birthday("970223")
//                .nickname("킹찬영짱짱맨")
//                .password("1111")
//                .phone("01075528034")
//                .username("이찬영")
//                .sex("남")
//                .build();
//
//        Header<AfterJoinUserResponse> join = userController.join(joinUserRequest);
//        System.out.println(join);
    }
}