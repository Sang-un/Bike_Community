package bike.community.service.club;

import bike.community.model.common.Address;
import bike.community.model.entity.club.Club;
import bike.community.model.entity.club.Gender;
import bike.community.model.entity.user.User;
import bike.community.model.network.Header;
import bike.community.model.network.request.club.NewClubRequest;
import bike.community.model.network.request.user.JoinUserRequest;
import bike.community.model.network.response.club.ClubResponse;
import bike.community.model.network.response.user.AfterJoinUserResponse;
import bike.community.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Rollback
@SpringBootTest
class ClubServiceTest {

    @Autowired
    private ClubService clubService;

    @Autowired
    private UserService userService;

    @Test
    void create() {

        userService.join(JoinUserRequest.builder()
                .birthday("980223")
                .password("ckscksdl12~")
                .nickname("킹차니")
                .phone("01099992222")
                .sex("남")
                .username("이찬영")
                .email("kokiyo97@naver.com")
                .address(new Address("경기도 군포시 산본2동", "개나리아파트1329동 401호", "15802"))
                .build());

        NewClubRequest newClub = NewClubRequest.builder()
                .age(20)
                .bikeModel("honda")
                .captainEmail("kokiyo97@naver.com")
                .city("군포시")
                .description("문란한밤입니다.")
                .gender(Gender.NON)
                .isPublic(true)
                .name("문밤")
                .build();
        Header<Club> clubHeader = clubService.create(newClub);
        System.out.println(clubHeader.getData());
    }

    @Test
    void findByEmail() {
    }

    @Test
    void addUserToClub() {
    }
}