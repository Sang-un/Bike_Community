package bike.community.model.entity.club;

import bike.community.model.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)/*프록시 객체를 위해 protect로 기본 생성자*/
@Getter
@Entity
public class ClubUser {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade=ALL)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade=ALL)
    @JoinColumn(name="club_id")
    private Club club;

    public static ClubUser create(Club club, User user) {
        ClubUser clubUser = new ClubUser();
        clubUser.user = user;
        clubUser.club = club;
        return clubUser;
    }
}
