package bike.community.model.entity.club;

import bike.community.model.DateBaseEntity;
import bike.community.model.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private Gender gender;

    private int numOfUser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User captain;

    @OneToMany(mappedBy="club")
    private List<ClubUser> users = new ArrayList<>();
}
