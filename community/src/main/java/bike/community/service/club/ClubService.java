package bike.community.service.club;


import bike.community.model.network.Header;
import bike.community.model.network.request.club.NewClubRequest;
import bike.community.model.network.response.club.ClubResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
@Service
public class ClubService {


    public Header<ClubResponse> create(NewClubRequest club) {

        return null;
    }

    public Header<ClubResponse> findByEmail(String requestUserEmail) {

        return null;
    }
}
