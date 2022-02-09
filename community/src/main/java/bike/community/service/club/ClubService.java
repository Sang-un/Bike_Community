package bike.community.service.club;


import bike.community.model.entity.club.Club;
import bike.community.model.network.Header;
import bike.community.model.network.request.club.JoinClubRequest;
import bike.community.model.network.request.club.NewClubRequest;
import bike.community.model.network.response.club.ClubResponse;
import bike.community.repository.club.ClubRepository;
import bike.community.repository.user.UserRepository;
import bike.community.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly=true)
@Service
public class ClubService {

    private final UserService userService;
    private final ClubRepository clubRepository;

    @Transactional //TODO 반환타입 Header<ClubResponse>로 수정하기
    public Header<Club> create(NewClubRequest newClub) {
        Club club = Club.create(newClub, userService.findByEmail(newClub.getCaptainEmail()));
        Club saveClub = clubRepository.save(club);
        return Header.OK(saveClub);
    }

    public Header<ClubResponse> findByEmail(String requestUserEmail) {

        return null;
    }

    public Header<ClubResponse> addUserToClub(JoinClubRequest joinClubRequest) {

        return null;
    }
}
