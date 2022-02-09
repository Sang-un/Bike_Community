package bike.community.model.entity.club;

import bike.community.model.DateBaseEntity;
import bike.community.model.entity.user.User;
import bike.community.model.network.request.club.NewClubRequest;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)/*프록시 객체를 위해 protect로 기본 생성자*/
@Getter
@Entity
public class Club extends DateBaseEntity implements Serializable {
    @Id @GeneratedValue
    @Column(name="club_id")
    private Long id;

    private String name;

    private String description;

    private boolean isPublic;

    private int age;

    private String city; //활동 지역  // TODO 나중에 확실한 주소 Address엔티티만들기
    private String bikeModel; // TODO 일단은 String

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int numOfUser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User captain;

    @OneToMany(mappedBy="club", cascade=ALL)
    private List<ClubUser> users = new ArrayList<>();

    public static Club create(NewClubRequest newClub, User captain) {
        Club club = new Club();
        club.name = newClub.getName();
        club.description = newClub.getDescription();
        club.isPublic = newClub.getIsPublic();
        club.age = newClub.getAge();
        club.city = newClub.getCity();
        club.bikeModel = newClub.getBikeModel();
        club.gender = newClub.getGender();
        club.numOfUser = 1;// 맨 처음에는 1
        club.captain = captain;
        captain.addClubAsCaptain(club);
        club.users.add(ClubUser.create(club, captain));
        return club;
    }
}
