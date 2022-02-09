package bike.community.model.network.request.club;

import bike.community.model.entity.club.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Builder
@Setter @Getter
public class NewClubRequest {

    private String name; //클럽 명
    private String description;//클럽 설명
    private boolean isPublic;//공개 크루인지
    private int age;
    private String city;//주요 활동 지역
    private String bikeModel;
    private Gender gender;
    private int numOfUser;
    private String captainEmail;

    public boolean getIsPublic() {
        return this.isPublic;
    }
}
