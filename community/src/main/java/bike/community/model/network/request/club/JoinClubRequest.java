package bike.community.model.network.request.club;


import lombok.Data;

@Data
public class JoinClubRequest {
    private String captainEmail;
    private String userEamil;
    private String clubName;
}
