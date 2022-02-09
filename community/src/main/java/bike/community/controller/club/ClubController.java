package bike.community.controller.club;

import bike.community.model.network.Header;
import bike.community.model.network.request.club.JoinClubRequest;
import bike.community.model.network.request.club.NewClubRequest;
import bike.community.model.network.response.club.ClubResponse;
import bike.community.service.club.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/api/club")
    public Header<ClubResponse> clubInfo(@RequestParam String clubName, @RequestParam String nickname) {
        return clubService.findByEmail(clubName);//TODO 그냥 회원과 팀장의 화면이 달라야함
    }

    @PostMapping("/api/club")
    public Header<ClubResponse> create(@RequestBody @Valid NewClubRequest club, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return Header.ERROR("유효성 검사 탈락");
//        return clubService.create(club);//TODO 그냥 회원과 팀장의 화면이 달라야함
            return null;
    }

    @PostMapping("/api/club/add")// Captain이 수락버튼 누르면 동작
    public Header<ClubResponse> addUserToClub(@RequestBody JoinClubRequest JoinClubRequest) {
        return clubService.addUserToClub(JoinClubRequest);//TODO 그냥 회원과 팀장의 화면이 달라야함
    }
}
