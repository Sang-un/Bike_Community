package bike.community.controller.club;

import bike.community.model.network.Header;
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
    public Header<ClubResponse> clubInfo(String requestUserEmail) {
        return clubService.findByEmail(requestUserEmail);//TODO 그냥 회원과 팀장의 화면이 달라야함
    }

    @PostMapping("/api/club")
    public Header<ClubResponse> create(@RequestBody @Valid NewClubRequest club, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return Header.ERROR("유효성 검사 탈락");
        return clubService.create(club);//TODO 그냥 회원과 팀장의 화면이 달라야함
    }


}
